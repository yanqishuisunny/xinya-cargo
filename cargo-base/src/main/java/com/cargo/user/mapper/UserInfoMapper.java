package com.cargo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.user.dto.UserInfoDto;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.vo.UserInfoVo;
import com.commom.vo.CurrentUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfoEntity> {


    List<UserInfoEntity> findByPageUserInfo(@Param("userInfoDto") UserInfoDto userInfoDto, Page<UserInfoVo> page, @Param("currentUser") CurrentUser currentUser, @Param("versionType") String versionType);




    CurrentUser findCurrentUser(@Param("userId") String userId , @Param("orgId") String orgId);

}
