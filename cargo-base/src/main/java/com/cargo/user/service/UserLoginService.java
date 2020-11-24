package com.cargo.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.common.UserEnum;
import com.cargo.user.dto.LoginDto;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.vo.LoginOrgUserVo;
import com.commom.cache.HeaderDto;
import com.commom.core.IBusCode;
import com.commom.vo.CurrentUser;

/**
 * <p>
 * 用户管理(此表不是备份，用于平台用户管理) 服务类
 * </p>
 *
 * @author Carlos
 * @since 2019-07-17
 */
public interface UserLoginService extends IService<UserInfoEntity> {



    /**
     * 用户登录 返回token
     * @param user
     * @return
     */
    String login(UserInfoEntity user, LoginOrgUserVo loginOrgUserVo);




    /**
     * 查询用户 条件  账号名 或者 手机号
     *
     * @return
     */
    UserInfoEntity findUser(String userCode);


    /**
     * 查询用户 条件  账号名 或者 手机号
     *
     * @return
     */
    Boolean checkUser(String userCode);




    /**
     * 发送短信验证码
     * @param phone
     * @param type 1：登录，2：注册，3：找回密码
     * @return
     */
    IBusCode sendSMS(String phone, UserEnum.SMSType type);


}
