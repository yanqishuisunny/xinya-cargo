package com.cargo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.user.dto.MenuRoleMainDto;
import com.cargo.user.entity.MenuRoleMainEntity;
import com.cargo.user.vo.MenuRoleMainVo;
import com.commom.vo.CurrentUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单角色主表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
public interface MenuRoleMainMapper extends BaseMapper<MenuRoleMainEntity> {

    List<MenuRoleMainVo> findByPageMenuRoleMain(@Param("menuRoleMainDto") MenuRoleMainDto menuRoleMainDto, Page<MenuRoleMainVo> page, @Param("currentUser") CurrentUser currentUser);
}
