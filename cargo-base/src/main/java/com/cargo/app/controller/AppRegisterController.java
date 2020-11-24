package com.cargo.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.app.dto.AppEditDto;
import com.cargo.app.dto.AppRegisterDto;
import com.cargo.app.dto.PwdDto;
import com.cargo.common.UserEnum;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.service.RegisterService;
import com.cargo.user.service.UserInfoService;
import com.cargo.user.service.UserLoginService;
import com.cargo.user.vo.LoginVO;
import com.cargo.utils.UserUtil;
import com.commom.cache.HeaderDto;
import com.commom.core.BusCode;
import com.commom.core.IBusCode;
import com.commom.supper.SuperController;
import com.commom.utils.RedisUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author GF
 * @Date 2019-8-15 13:51:50
 * @Description
 **/
@Api(tags = "01-APP注册")
@RestController
@RequestMapping("/api/base/app")
public class AppRegisterController extends SuperController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 注册
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "0101-APP注册，并自动登录")
    @PostMapping("/register/user")
    public ResponseInfo<LoginVO> appRegister(@RequestBody @Validated AppRegisterDto dto) {
        LoginVO loginVO = new LoginVO();
        HeaderDto headerDto = this.getHeaders();
        dto.setUserCode(dto.getPhoneNo());
        IBusCode code = registerService.appRegister(dto,headerDto);
        if (!Objects.equals(BusCode.SUCCESS, code)) {
            return ResponseUtil.result(code);
        }
        UserInfoEntity user = userInfoService.findUser(dto.getPhoneNo());
        loginVO.setToken(userLoginService.login(user,null));
        loginVO.setUserCode(user.getUserCode());
        loginVO.setPhoneNo(user.getPhoneNo());
        return ResponseUtil.success(loginVO);
    }




    /**
     * 验证用户名是否存在
     *
     * @param userCode
     */
    @ApiOperation(value = "验证用户名是否存在")
    @GetMapping("/existUserName")
    public ResponseInfo existUserName(@ApiParam("用户名/手机号") @RequestParam String userCode) {
        boolean flag = userLoginService.checkUser(userCode);
        return ResponseUtil.success(flag);
    }



    @ApiOperation(value = "编辑用户信息")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody AppEditDto dto) {
        QueryWrapper<UserInfoEntity> qw = new QueryWrapper<>();
        qw.eq("user_code", dto.getUserCode());
        qw.eq("password", dto.getPassword());
        UserInfoEntity userInfoEntity = userInfoService.getOne(qw);
        if (null == userInfoEntity) {
            return ResponseUtil.error("用户不存在");
        }
        userInfoEntity.setPassword(dto.getPassword());
        return ResponseUtil.result(userInfoService.updateById(userInfoEntity));
    }



    /**
     * APP重置密码，忘记密码
     *
     * @param pwdDto
     * @return
     */
    @ApiOperation(value = "0503-重置密码，忘记密码")
    @PostMapping(value = "/resetPassword")
    public ResponseInfo updatePassword(@RequestBody PwdDto pwdDto) {
        UserInfoEntity user = userInfoService.findUser(pwdDto.getPhone());
        UserEnum.SMSType emType = UserEnum.SMSType.valueOf(pwdDto.getType());
        String key = emType.getRediskey() + pwdDto.getPhone();
        String redisSms = (String) RedisUtil.get(key);
        if (!pwdDto.getSmscode().equals(redisSms)) {
            return ResponseUtil.result(UserEnum.Code.LOGIN_MESSAGE_CAPTHA_ERROR);
        }
        RedisUtil.del(UserUtil.REDIS_LOGIN_ACCOUT_NUMBER + user.getUserCode());
        return ResponseUtil.success(userInfoService.editPassword(user.getUserId(), pwdDto.getPassword()));
    }



}
