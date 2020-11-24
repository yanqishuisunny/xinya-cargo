package com.cargo.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.user.dto.EditMenuRoleDetailDto;
import com.cargo.user.entity.MenuRoleDetailEntity;

/**
 * <p>
 * 菜单角色明细表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
public interface MenuRoleDetailService extends IService<MenuRoleDetailEntity> {

   boolean edit(EditMenuRoleDetailDto dto);

}
