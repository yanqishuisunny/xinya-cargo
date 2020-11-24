package com.cargo.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.user.dto.MenuRoleMainDto;
import com.cargo.user.entity.MenuRoleMainEntity;
import com.cargo.user.vo.MenuRoleMainVo;
import com.commom.vo.CurrentUser;

/**
 * <p>
 * 菜单角色主表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
public interface MenuRoleMainService extends IService<MenuRoleMainEntity> {

    Page<MenuRoleMainVo> findByPageMenuRoleMain(MenuRoleMainDto menuRoleMainDto, CurrentUser currentUser, Page<MenuRoleMainVo> page);
}
