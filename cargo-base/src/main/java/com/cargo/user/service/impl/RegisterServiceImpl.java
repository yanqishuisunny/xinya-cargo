package com.cargo.user.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.cargo.app.dto.AppRegisterDto;
import com.cargo.common.RoleEm;
import com.cargo.common.UserEnum;
import com.cargo.user.dto.UserRegisterDto;
import com.cargo.user.entity.UserDetailEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.UserDetailMapper;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.RegisterService;
import com.cargo.user.service.UserInfoService;
import com.commom.cache.HeaderDto;
import com.commom.core.BusCode;
import com.commom.core.IBusCode;
import com.commom.snowflake.SnowflakeIdWorker;
import com.commom.utils.MD5Util;
import com.commom.utils.RedisUtil;
import com.commom.utils.Tool;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @Author GF
 * @Date 2019-7-29 09:54:50
 * @Description
 **/
@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {


    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    private UserDetailMapper userDetailMapper;





    /**
     * app端注册
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IBusCode appRegister(AppRegisterDto dto,HeaderDto headerDto) {
        String code = (String) RedisUtil.get(UserEnum.SMSType.REGISTER.getRediskey() + dto.getPhoneNo());
        if (!Objects.equals(code, dto.getSmsCode())) {
            return UserEnum.Code.LOGIN_MESSAGE_CAPTHA_ERROR;
        }
        //校验登录账号是否重复
        if (userInfoService.checkUserCode(dto.getUserCode())) {
            return UserEnum.Code.REG_PHONE_DUPLICATION_ERROR;
        }
        //用户角色 未确认 roleId
        UserInfoEntity userEntity = new UserInfoEntity(dto.getUserCode(), dto.getPassword(), dto.getPhoneNo());
        userEntity.setUserType(RoleEm.ORG_ADMIN);  //注册的用户都是企业管理员
        userEntity.setUserRole(headerDto.getVersionType());
        userEntity.setUserId(SnowflakeIdWorker.generateId().toString());
        userEntity.setSalt(Tool.uuId());
        if (!Strings.isNullOrEmpty(dto.getPassword())) {
            userEntity.setPassword(MD5Util.MD5Encode(dto.getPassword() + userEntity.getSalt()));
        }
        userInfoMapper.insert(userEntity);
        UserDetailEntity userDetailEntity = new UserDetailEntity();
        userDetailEntity.setFlgPerfect(false);
        userDetailEntity.setOverdueStatus(0);
        userDetailEntity.setId(SnowflakeIdWorker.generateId().toString());
        userDetailEntity.setUserId(userEntity.getUserId());
        userDetailMapper.insert(userDetailEntity);
        //通过后清空短信缓存
        RedisUtil.del(UserEnum.SMSType.REGISTER.getRediskey() + dto.getPhoneNo());
        return BusCode.SUCCESS;
    }


    /**
     * 0302-注册用户
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String registerUser(UserRegisterDto userRegisterDTO, HeaderDto headerDto) {
        log.info("注册用户信息：{}", userRegisterDTO);
        return "";
    }


}
