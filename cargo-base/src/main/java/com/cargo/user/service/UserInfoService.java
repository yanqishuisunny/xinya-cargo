package com.cargo.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.user.dto.IdCardVaildDto;
import com.cargo.user.dto.UserInfoDto;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.vo.LoginOrgUserVo;
import com.cargo.user.vo.UserInfoVo;
import com.commom.cache.HeaderDto;
import com.commom.vo.CurrentUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
public interface UserInfoService extends IService<UserInfoEntity> {


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
    LoginOrgUserVo findUserByCode(String userCode);



    LoginOrgUserVo findUserById(String userId,String versionType);


    Page<UserInfoVo> findByPageUserInfo(UserInfoDto dto, CurrentUser currentUser, Page<UserInfoVo> page, HeaderDto headerDto);


    int editPassword(String userId , String password);


    UserInfoVo convert(UserInfoEntity userInfoEntity);

    /**
     * 校验手机号唯一性
     *
     * @param userCode
     * @return
     */
    boolean checkUserCode(String userCode);


    /**
     * 校验手机号唯一性
     *
     * @param phoneNo
     * @return
     */
    boolean checkPhone(String phoneNo);



    CurrentUser findCurrentUser(String userId,String orgId);


    /**
     * 实名认证
     *
     * @param dto
     */
    void updataUserIdCard(IdCardVaildDto dto);


    /**
     * 身份证过期之后换证件
     * @param idCardVaildDto
     * @return
     */
    Boolean replaceIdCard(IdCardVaildDto idCardVaildDto,String userId);

}
