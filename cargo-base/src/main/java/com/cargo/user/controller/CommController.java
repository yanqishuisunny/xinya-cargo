package com.cargo.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.common.UserEnum;
import com.cargo.core.BaseBusCode;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.UserLoginService;
import com.commom.core.AssertBuss;
import com.commom.core.IBusCode;
import com.commom.exception.BussException;
import com.commom.shiro.JWTUtil;
import com.commom.supper.SuperController;
import com.commom.utils.RedisUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 通用功能
 * @author 何立辉
 */

@RestController
@RequestMapping(value = "/api/base/comm")
@Api(tags = "02-短信-通用功能")
@Validated
public class CommController extends SuperController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserLoginService userLoginService;

    /**
     * 验证通过令牌时效
     */
    private final static long SMS_OK_TOKEN_EXPIRATION = 20 * 60L;


    @ApiOperation(value = "0201-短信登录-发送验证码")
    @GetMapping("/sendSMS")
    @Validated
    public ResponseInfo sendSMS(
            @ApiParam("账号/手机号")
            @Pattern(regexp = "^1[\\d]{10}", message = "手机号格式错误")
            @NotBlank(message = "手机号不能为空!")
            @RequestParam String phone,
            @ApiParam("类型(LOGIN:登录,FORGET:忘记密码,REGISTER:注册验证码)")
            @NotBlank(message = "短信验证码类型不能为空!")
            @RequestParam String type) {
        UserEnum.SMSType emType = UserEnum.SMSType.valueOf(type);
        AssertBuss.notNull(emType, BaseBusCode.PARAMETER_VALID_ERROR);
        //校验平台
        QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_code", phone);
        List<UserInfoEntity> userList = userInfoMapper.selectList(wrapper);
        if(CollectionUtils.isNotEmpty(userList)  && UserEnum.SMSType.REGISTER.getRediskey().equals(emType.getRediskey())){
            return ResponseUtil.result(UserEnum.Code.SMS_REGISTER);
        }
        UserInfoEntity user = userInfoMapper.selectOne(wrapper);
        //如果用户已经存在，但标识是注册。则不可让用户在注册
        if(null != user && UserEnum.SMSType.REGISTER.getRediskey().equals(emType.getRediskey())){
            return ResponseUtil.result(UserEnum.Code.SMS_REGISTER);
        }
        //判断用户是空，但是忘记密码和登录操作。提示用户去注册
        if(null == user && (UserEnum.SMSType.LOGIN.getRediskey().equals(emType.getRediskey()) || UserEnum.SMSType.FORGET.getRediskey().equals(emType.getRediskey()))){
            return ResponseUtil.result(UserEnum.Code.NOT_REGISTER);
        }
        //判断当前用户是否被禁用
        if(null != user && 0 == user.getStatus()){
            throw new BussException(UserEnum.Code.USER_SHUTDOWN);
        }
        IBusCode busCode = userLoginService.sendSMS(phone, emType);
        return ResponseUtil.result(busCode);
    }


//    @ApiOperation(value = "0202-验证填写的短信码是否正确")
//    @GetMapping("/checkSMS")
//    @Validated
//    public ResponseInfo<String> checkSmsCode(
//            @ApiParam("账号/手机号")
//            @Pattern(regexp = "^1[\\d]{10}", message = "手机号格式错误")
//            @NotBlank(message = "手机号不能为空!")
//            @RequestParam String phone,
//            @ApiParam("类型!(LOGIN:登录,FORGET:忘记密码,REGISTER:注册验证码)")
//            @NotBlank(message = "短信验证码类型不能为空!")
//            @RequestParam String type,
//            @ApiParam("验证码")
//            @NotBlank(message = "验证码不能为空!")
//            @RequestParam String smsCode) {
//        UserEnum.SMSType emType = UserEnum.SMSType.valueOf(type);
//        String key = emType.getRediskey() + phone;
//        String redisSms = (String) RedisUtil.get(key);
//        if (!smsCode.equals(redisSms)) {
//            return ResponseUtil.result(UserEnum.Code.LOGIN_MESSAGE_CAPTHA_ERROR);
//        } else {
//            //验证通过后删除缓存
//            RedisUtil.del(key);
//            String token = jwtUtil.generateSMSToken(phone, SMS_OK_TOKEN_EXPIRATION);
//            System.out.println("验证码token:"+token);
//            return ResponseUtil.success(token);
//        }
//    }



}
