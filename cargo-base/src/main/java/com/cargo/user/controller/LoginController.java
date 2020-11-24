package com.cargo.user.controller;

import com.cargo.common.RegChannelEm;
import com.cargo.common.UserEnum;
import com.cargo.user.dto.LoginDto;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.service.UserInfoService;
import com.cargo.user.service.UserLoginService;
import com.cargo.user.vo.LoginOrgUserVo;
import com.cargo.user.vo.LoginVO;
import com.cargo.utils.UserUtil;
import com.commom.cache.HeaderDto;
import com.commom.core.BusCode;
import com.commom.core.IBusCode;
import com.commom.supper.SuperController;
import com.commom.utils.MD5Util;
import com.commom.utils.RedisUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author GF
 */

@Slf4j
@RestController
@Api(tags = "04-登录相关")
@RequestMapping(value = "/api/base/login")
@Validated
public class LoginController extends SuperController {



    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserInfoService userInfoService;



    /**
     * 密码输入错误 锁定时长 5分钟
     */
    private static final long REDIS_LOGIN_ACCOUT_LOCK_TIME = 5 * 60L;
    /**
     * 允许密码错误次数
     */
    private static final int LOGIN_LIMIT = 5;



    @ApiOperation(value = "0910-任务单系统登录")
    @PostMapping("/signIn")
    public ResponseInfo<LoginVO> userSignIn(@RequestBody @Validated LoginDto dto) {
        //错误次数
        LoginVO loginVO = new LoginVO();
        // 判断是否被锁定
        String loginLock = UserUtil.REDIS_LOGIN_ACCOUT_LOCK + dto.getUserCode();
        if (RedisUtil.hasKey(loginLock)) {
            return ResponseUtil.result(UserEnum.Code.LOGIN_USER_PWD_FIVE);
        }
        UserInfoEntity user = userLoginService.findUser(dto.getUserCode());
        if (null == user) {
            return ResponseUtil.result(UserEnum.Code.NOT_USER);
        }
        if (null != user && 0 == user.getStatus()) {
            return ResponseUtil.result(UserEnum.Code.USER_SHUTDOWN);
        }
        String md5Pwd = MD5Util.MD5Encode(dto.getPassword() + user.getSalt());
        if (!Objects.equals(user.getPassword(), md5Pwd)) {
            return ResponseUtil.result(UserEnum.Code.LOGIN_ACCOUNT_ERROR);
        }
//        //判断用户登录平台是否正确
//        IBusCode clientCode = clientCheck(user);
//        if (!Objects.equals(BusCode.SUCCESS,clientCode)) {
//            return ResponseUtil.result(clientCode);
//        }
        LoginOrgUserVo loginOrgUserVo = userInfoService.findUserById(user.getUserId(),this.getHeaders().getVersionType());
        String token = this.userLoginService.login(user,loginOrgUserVo);
        loginVO.setToken(token);
        if(null != loginOrgUserVo){
            loginVO.setUserCode(loginOrgUserVo.getUserCode());
            loginVO.setUserId(user.getUserId());
            loginVO.setPhoneNo(user.getPhoneNo());
            loginVO.setOrgId(loginOrgUserVo.getOrgId());
            loginVO.setOrgName(loginOrgUserVo.getOrgName());
        }
        //删除redis登录错误次数缓存
        RedisUtil.del(UserUtil.REDIS_LOGIN_ACCOUT_NUMBER + dto.getUserCode());
        return ResponseUtil.success(loginVO);
    }




    /**
     * 判断用户权限 是否与登录客户端匹配
     * @param user
     * @return
     */
    private IBusCode clientCheck(UserInfoEntity user) {
        HeaderDto headerDto = this.getHeaders();
        if (Objects.equals(RegChannelEm.PCWEB.toString(), headerDto.getAppid())) {
            if("3".equals(user.getUserRole())){
                return UserEnum.Code.APP_CONSIGNOR_LOGIN_ERROR;
            }
        }
        return BusCode.SUCCESS;
    }






    @ApiOperation(value = "0402-短信登录")
    @PostMapping("/login-sms")
    public ResponseInfo<LoginVO> userLoginSMS(
            @Validated
            @ApiParam("账号/手机号")
            @NotBlank(message = "手机号不能为空!")
            @Pattern(regexp = "^1[\\d]{10}", message = "手机号格式错误")
            @RequestParam String phone,
            @Validated
            @ApiParam("短信")
            @NotBlank(message = "验证码不能为空!")
            @RequestParam String sms) {
        LoginVO loginVO = new LoginVO();
        // 记录用户登录次数 并判断是否被锁定
        String loginLock = UserUtil.REDIS_LOGIN_ACCOUT_LOCK + phone;
        if (RedisUtil.hasKey(loginLock)) {
            return ResponseUtil.result(UserEnum.Code.LOGIN_USER_PWD_LIMIT);
        }
        UserInfoEntity user = userInfoService.findUser(phone);

        if (null == user ) {
            return ResponseUtil.result(UserEnum.Code.NOT_REGISTER);
        }

        if (Objects.isNull(user)) {
            loginNumber(phone);
            return ResponseUtil.result(UserEnum.Code.LOGIN_PHONE_NULL_ERROR);
        }
        String redisSms = (String) RedisUtil.get(UserEnum.SMSType.LOGIN.getRediskey() + phone);
        if (!sms.equals(redisSms)) {
            loginNumber(phone);
            return ResponseUtil.result(UserEnum.Code.LOGIN_MESSAGE_CAPTHA_ERROR);
        }
        LoginOrgUserVo loginOrgUserVo = userInfoService.findUserById(user.getUserId(),this.getHeaders().getVersionType());
//        IBusCode clientCode = clientCheck(user);
//        if (!Objects.equals(BaseBusCode.SUCCESS,clientCode)) {
//            return ResponseUtil.result(clientCode);
//        }
        String token = this.userLoginService.login(user,loginOrgUserVo);
        loginVO.setToken(token);
        return ResponseUtil.success(loginVO);
    }



    /**
     * 记录用户密码输入错误次数
     * @param userCode
     * @return
     */
    private IBusCode loginNumber(String userCode) {
        String key = UserUtil.REDIS_LOGIN_ACCOUT_NUMBER + userCode;
        Object o = RedisUtil.get(key);
        int num = o == null ? 0 : (Integer) o;
        // 获取当天23点59分59秒Date
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        long redisTagTime = (calendar2.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        RedisUtil.set(key, ++num, redisTagTime);
        String loginKey = UserUtil.REDIS_LOGIN_ACCOUT_LOCK + userCode;
        if (num >= LOGIN_LIMIT) {
            RedisUtil.set(loginKey, 0, REDIS_LOGIN_ACCOUT_LOCK_TIME);
            RedisUtil.del(key);
        }
        if(3==num){
            return UserEnum.Code.LOGIN_USER_PWD_THREE;
        }else if(5==num){
            return UserEnum.Code.LOGIN_USER_PWD_FIVE;
        }
        return UserEnum.Code.LOGIN_ACCOUNT_ERROR;
    }






}
