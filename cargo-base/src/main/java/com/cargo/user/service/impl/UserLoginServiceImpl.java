package com.cargo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.common.UserEnum;
import com.cargo.core.BaseBusCode;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.UserInfoService;
import com.cargo.user.service.UserLoginService;
import com.cargo.user.vo.LoginOrgUserVo;
import com.commom.cache.CachePre;
import com.commom.cache.Constant;
import com.commom.cache.HeaderDto;
import com.commom.config.JwtProperties;
import com.commom.core.AssertBuss;
import com.commom.core.IBusCode;
import com.commom.exception.BussException;
import com.commom.gpsUtils.StringUtil;
import com.commom.shiro.JWTUtil;
import com.commom.utils.HttpServletUtil;
import com.commom.utils.NumUtil;
import com.commom.utils.RedisUtil;
import com.commom.utils.Tool;
import com.google.common.base.Strings;
import com.xsungroup.tms.external.api.MessageApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;

/**
 * <p>
 * 用户管理(此表不是备份，用于平台用户管理) 服务实现类
 * </p>
 *
 * @author Carlos
 * @since 2020-07-17
 */
@Service
@Slf4j
public class UserLoginServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements UserLoginService {


    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 验证码每小时最多发送次数
     */
    private static final int HOUR_SMSMAXCOUNT = 5;

    /**
     * 验证码每天最多发送次数
     */
    private static final int DAY_SMSMAXCOUNT = 10;

    /**
     * 验证码一小时 防刷
     */
    private long SMSHOUREXPIRATION = 60 * 60L;


    /**
     * 验证码 有效期
     */
    private static final long SMSEXPIRATION = 5 * 60L;


    @Autowired
    private MessageApi messageApi;


    @Autowired
    private UserInfoService userInfoService;




    @Override
    public UserInfoEntity findUser(String userCode) {
        if(Strings.isNullOrEmpty(userCode)){
            throw new BussException("缺少入参");
        }
        QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(userCode)) {
            wrapper.eq("user_code", userCode);
        }
        return getOne(wrapper);
    }


    @Override
    public Boolean checkUser(String userCode) {
        if(Strings.isNullOrEmpty(userCode)){
            throw new BussException("缺少入参");
        }
        QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(userCode)) {
            wrapper.eq("user_code", userCode);
        }
        return retBool(this.baseMapper.selectCount(wrapper));
    }


    @Override
    public String login(UserInfoEntity user, LoginOrgUserVo loginOrgUserVo) {
        AssertBuss.notEmpty(user, UserEnum.Code.NOT_USER);
        if (Objects.equals(Constant.Status.DISABLED.getValue(), user.getStatus())) {
            throw new BussException(UserEnum.Code.LOGIN_USER_DISABLED);
        }
        String hostName = HttpServletUtil.getRequest().getRemoteHost();
        String ip = HttpServletUtil.getIpAddress();

        String uuid = Tool.uuId();
        String token = "";
        if(null != loginOrgUserVo && !StringUtil.isEmpty(loginOrgUserVo.getOrgId())){
            token = jwtUtil.generateToken(ip, hostName, user.getUserId()+","+loginOrgUserVo.getOrgId(), uuid, jwtProperties.getExpiration());
            RedisUtil.set(CachePre.LOING_SHIRO_JWT_ID + token, user.getUserId()+","+loginOrgUserVo.getOrgId(), jwtProperties.getExpiration());
        }else{
            token = jwtUtil.generateToken(ip, hostName, user.getUserId(), uuid, jwtProperties.getExpiration());
            RedisUtil.set(CachePre.LOING_SHIRO_JWT_ID + token, user.getUserId(), jwtProperties.getExpiration());
        }
        System.out.println("login:"+token);
        //更新登录时间
        user.setLastLoginTime(LocalDateTime.now());
        this.baseMapper.updateById(user);
        return token;
    }





    @Override
    public IBusCode sendSMS(String phone, UserEnum.SMSType type) {
        if (!Strings.isNullOrEmpty(phone)) {
            switch (type) {
                case REGISTER:
                    if (userInfoService.checkUserCode(phone)) {
                        return UserEnum.Code.ACCOUNT_DOUBLE_ERROR;
                    }
                    break;
                case FORGET:
                case LOGIN:
                    if (!userInfoService.checkUserCode(phone)) {
                        return UserEnum.Code.LOGIN_PHONE_NULL_ERROR;
                    }
                    break;
                default:
                    return BaseBusCode.PARAMETER_VALID_ERROR;
            }
            //获取当前时间到第二天零点的秒数
            long SMSDAYEXPIRATION = getSecondsNextEarlyMorning();
            if( SMSDAYEXPIRATION < SMSHOUREXPIRATION){
                SMSHOUREXPIRATION = SMSDAYEXPIRATION;
            }
            //每小时发送的次数防刷
            String hour_key = CachePre.PHONE_SMS_HOUR_SEND_COUNT + type + phone;
            int hour_count = 0;
            if (RedisUtil.hasKey(hour_key)) {
                hour_count = (int) RedisUtil.get(hour_key);
            }
            RedisUtil.set(hour_key, ++hour_count, SMSHOUREXPIRATION);
            if (hour_count > HOUR_SMSMAXCOUNT) {
                return UserEnum.Code.SMS_LIMIT;
            }

            //每天发送的最大次数
            String day_key = CachePre.PHONE_SMS_DAY_SEND_COUNT + type + phone;
            int day_count = 0;
            if (RedisUtil.hasKey(day_key)) {
                day_count = (int) RedisUtil.get(day_key);
            }
            RedisUtil.set(day_key, ++day_count, SMSDAYEXPIRATION);
            if (day_count > DAY_SMSMAXCOUNT) {
                return UserEnum.Code.SMS_DAY_OUT;
            }

            String smsCode = NumUtil.getCode(6);
            log.info(type.getMessage() + " = " + smsCode);
            int code = messageApi.send(phone, "您的验证码为" + smsCode + "有效期5分钟，感谢您的关注和支持").getCode();
            if (code == BaseBusCode.SUCCESS.getCode()) {
                RedisUtil.set(type.getRediskey() + phone, smsCode, SMSEXPIRATION);
                return BaseBusCode.SUCCESS;
            }
        }
        return BaseBusCode.FAIL;
    }



    public static Long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        // 改成这样就好了
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }



}
