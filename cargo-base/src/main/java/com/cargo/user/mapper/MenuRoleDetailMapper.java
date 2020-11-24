package com.cargo.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.user.entity.MenuRoleDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 菜单角色明细表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Repository
public interface MenuRoleDetailMapper extends BaseMapper<MenuRoleDetailEntity> {
    int updateMenuRoleDetail(@Param("roleMainId") String roleMainId);
}
