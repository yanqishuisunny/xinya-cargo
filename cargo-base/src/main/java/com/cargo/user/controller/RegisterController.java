package com.cargo.user.controller;

import com.cargo.app.dto.AppRegisterDto;
import com.cargo.app.dto.PwdDto;
import com.cargo.common.UserEnum;
import com.cargo.user.dto.UserRegisterDto;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.service.RegisterService;
import com.cargo.user.service.UserInfoService;
import com.cargo.user.service.UserLoginService;
import com.cargo.user.vo.LoginVO;
import com.cargo.utils.UserUtil;
import com.commom.cache.HeaderDto;
import com.commom.core.BusCode;
import com.commom.core.IBusCode;
import com.commom.shiro.JWTUtil;
import com.commom.supper.SuperController;
import com.commom.utils.RedisUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @Author Carlos
 **/
@Api(tags = "03-用户注册")
@RestController
@RequestMapping("/api/base/register")
public class RegisterController extends SuperController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JWTUtil jwtUtil;



    /**
     * 验证用户名是否存在
     *
     * @param userCode
     */
    @ApiOperation(value = "验证用户名是否存在")
    @GetMapping("/existUserName")
    public ResponseInfo existUserName(@ApiParam("用户名") @RequestParam String userCode) {
        boolean flag = userLoginService.checkUser(userCode);
        return ResponseUtil.success(flag);
    }


    /**
     * 注册用户
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "新注册用户")
    @PostMapping("/registerUser")
    public ResponseInfo<LoginVO> registerUser(@RequestBody AppRegisterDto dto) {
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
     * PC重置密码，忘记密码
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
