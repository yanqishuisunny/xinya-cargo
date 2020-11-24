package com.cargo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.user.entity.MenuBaseInfoEntity;
import com.cargo.user.vo.MenuBaseInfoVo;
import com.commom.vo.CurrentUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
public interface MenuBaseInfoMapper extends BaseMapper<MenuBaseInfoEntity> {

    List<MenuBaseInfoVo> findMenuBaseInfoList(@Param("roleId") String roleId, @Param("versionType") String versionType);

    List<MenuBaseInfoVo> findByMenuBaseInfoListByLogin(@Param("user") CurrentUser user, @Param("versionType") String versionType);


}
