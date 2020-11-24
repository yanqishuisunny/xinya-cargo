package com.cargo.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.user.entity.UserDetailEntity;
import com.cargo.user.entity.UserInfoEntity;

/**
 * <p>
 * 用户详细情况表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-05
 */
public interface UserDetailService extends IService<UserDetailEntity> {


    /**
     * 修改用户详情信息  并同步用户身份证到用户表中
     * @param userDetail
     */
    void updateFullUserDetail(UserInfoEntity userEntity, UserDetailEntity userDetail);



    /**
     * 检查身份是否存在
     * @param card
     * @return
     */
    boolean exitsByIdCard(String card,String userId);



    /**
     * 根据用户ID获取唯一用户详情
     * @param userId
     * @return
     */
    UserDetailEntity selectOneByUserId(String userId);

}
