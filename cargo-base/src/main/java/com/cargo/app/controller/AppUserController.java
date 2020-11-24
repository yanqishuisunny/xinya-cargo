package com.cargo.app.controller;

import com.cargo.app.vo.AppDriverInformationVo;
import com.cargo.car.entity.CarEntity;
import com.cargo.car.service.CarService;
import com.cargo.common.UserEnum;
import com.cargo.user.dto.IdCardVaildDto;
import com.cargo.user.entity.DriverInformationEntity;
import com.cargo.user.entity.UserDetailEntity;
import com.cargo.user.service.DriverInformationService;
import com.cargo.user.service.UserDetailService;
import com.cargo.user.service.UserInfoService;
import com.commom.cache.CachePre;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.DateUtil;
import com.commom.utils.RedisUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import com.xsungroup.tms.external.api.BaiduApi;
import com.xsungroup.tms.external.api.PengYuanApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author
 * @Date
 * @Description
 **/
@Api(tags = "07-APP 司机信息")
@RestController
@Slf4j
@RequestMapping("/api/base/app/user")
public class AppUserController extends SuperController {

    @Autowired
    private UserInfoService userService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private CarService carService;

    @Autowired
    private BaiduApi baiduApi;

    @Autowired
    private PengYuanApi pengYuanApi;

    @Autowired
    private DriverInformationService driverInformationService;


    @ApiOperation(value = "0701-实名认证")
    @PostMapping("/idcard-valid")
    @Transactional(rollbackFor = Exception.class)
    public ResponseInfo idcardValid(@RequestBody @Validated IdCardVaildDto dto) {
        String idCardEndTime = dto.getIdCardEndTime();
        Date newDate = DateUtil.StrToDate(idCardEndTime, "yyyy-MM-dd");
        if (newDate.before(new Date(System.currentTimeMillis()))) {
            //如果身份在的结束日期小于当前的日期，就认为身份证过期了
            throw new BussException("身份证已过期，请上传有效的身份证件！");
        }
        dto.setUserId(ShiroUtil.getUserId());
        //在校验认证核验之前，先调鹏元身份证验证的接口
        String msg = "实名认证失败";
        try {
            com.xsungroup.tms.external.vo.ResponseInfo result = pengYuanApi.identity(dto.getIdcardName(), dto.getIdcardNo());
            if (result.getCode() != 1000) {
                msg = result.getMessage();
                return ResponseUtil.error(msg);
            }
        } catch (Exception e) {
            return ResponseUtil.error(msg);
        }
        //这一行移动到调认证核验接口之前，是为了节省接口费用
        userService.updataUserIdCard(dto);
        //官网API上给的建议值是80 低于80分的都视为认证不通过
        if (idcardCheck(dto, dto.getUserId()) < 80) {
            throw new BussException(UserEnum.Code.ID_CARD_CHECK);
        }
        return ResponseUtil.success();
    }



    public Double idcardCheck(IdCardVaildDto dto, String userId) {
        com.xsungroup.tms.external.vo.ResponseInfo result = null;
        try {
            result = baiduApi.idcardCheck(dto.getLivingImgUrl(), dto.getIdcardNo(), dto.getIdcardName());
        } catch (Exception e) {
            log.info("人证核验失败，继续下面操作");
        }
        log.info("认证核验返回的结果：" + result.toString());
        Map map = (Map) result.getData();
        if (map == null) {
            throw new BussException("认证失败，请确认身份信息是否正确");
        }
        String data = map.get("score") == null ? "0" : map.get("score").toString();
        return Double.parseDouble(data);
    }



    @ApiOperation(value = "用户信息完善程度提示信息")
    @GetMapping("/user-perfect")
    public ResponseInfo<AppDriverInformationVo> userPerfectTips() {
        AppDriverInformationVo appDriverInformationVo = new AppDriverInformationVo();
        UserDetailEntity userDetail = userDetailService.selectOneByUserId(ShiroUtil.getUserId());
        if (Objects.isNull(userDetail) || !userDetail.getFlgPerfect()) {
            appDriverInformationVo.setAuthenName(false);
        }else{
            appDriverInformationVo.setAuthenName(true);
        }
        DriverInformationEntity driverInformationEntity = driverInformationService.selectInfoByUserId(ShiroUtil.getUserId());
        if (driverInformationEntity != null) {
            appDriverInformationVo.setAuditStatus(driverInformationEntity.getAuditStatus());
        }else{
            appDriverInformationVo.setAuditStatus(6);
        }
        List<CarEntity> carList = carService.selectOneByOrgIdAndCompanyId(ShiroUtil.getUserId());
        if (CollectionUtils.isEmpty(carList)) {
            appDriverInformationVo.setCarInfo(false);
        }else{
            appDriverInformationVo.setCarInfo(true);
        }
        return ResponseUtil.success(appDriverInformationVo);
    }



    @PostMapping("/replaceIdCard")
    @ApiOperation(value = "身份证过期之后换证件")
    public ResponseInfo replaceIdCard(@RequestBody IdCardVaildDto idCardVaildDto) {
        return ResponseUtil.resultSuccess(userService.replaceIdCard(idCardVaildDto, ShiroUtil.getUserId()));
    }



    /**
     * @param : token
     * @return : boolean
     * @Description : 退出登录
     * @auther : Alex
     * @date : 2019/7/31
     */
    @ApiOperation(value = "0403-退出登录")
    @PostMapping("/logout")
    public ResponseInfo logout(HttpServletRequest request) {
        RedisUtil.del(CachePre.LOING_SHIRO_JWT_ID + ShiroUtil.currentUserId() + this.getHeaders().getToken());
        //注销cas session
        HttpSession session = request.getSession(false);
        if (!ObjectUtils.isEmpty(session)) {
            session.invalidate();
        }
        return ResponseUtil.success();
    }


}
