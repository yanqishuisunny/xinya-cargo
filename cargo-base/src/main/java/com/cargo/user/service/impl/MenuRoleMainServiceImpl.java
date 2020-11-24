package com.cargo.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.user.dto.MenuRoleMainDto;
import com.cargo.user.entity.MenuRoleMainEntity;
import com.cargo.user.mapper.MenuRoleMainMapper;
import com.cargo.user.service.MenuRoleMainService;
import com.cargo.user.vo.MenuRoleMainVo;
import com.commom.vo.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单角色主表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Service
public class MenuRoleMainServiceImpl extends ServiceImpl<MenuRoleMainMapper, MenuRoleMainEntity> implements MenuRoleMainService {
    @Autowired
    private MenuRoleMainMapper menuRoleMainMapper;
    @Override
    public Page<MenuRoleMainVo> findByPageMenuRoleMain(MenuRoleMainDto menuRoleMainDto, CurrentUser currentUser, Page<MenuRoleMainVo> page) {
        List<MenuRoleMainVo> list = menuRoleMainMapper.findByPageMenuRoleMain(menuRoleMainDto,page,currentUser);
        page.setRecords(list);
        return page;
    }
}
