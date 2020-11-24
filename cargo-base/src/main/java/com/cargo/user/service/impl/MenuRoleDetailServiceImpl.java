package com.cargo.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.user.dto.EditMenuRoleDetailDto;
import com.cargo.user.entity.MenuBaseInfoEntity;
import com.cargo.user.entity.MenuRoleDetailEntity;
import com.cargo.user.entity.MenuRoleMainEntity;
import com.cargo.user.mapper.MenuBaseInfoMapper;
import com.cargo.user.mapper.MenuRoleDetailMapper;
import com.cargo.user.mapper.MenuRoleMainMapper;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.MenuRoleDetailService;
import com.commom.core.BeanConverter;
import com.commom.shiro.ShiroUtil;
import com.commom.snowflake.SnowflakeIdWorker;
import com.commom.vo.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单角色明细表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Service
public class MenuRoleDetailServiceImpl extends ServiceImpl<MenuRoleDetailMapper, MenuRoleDetailEntity> implements MenuRoleDetailService {

    @Autowired
    private MenuRoleDetailMapper menuRoleDetailMapper;

    @Autowired
    private MenuBaseInfoMapper menuBaseInfoMapper;

    @Autowired
    private MenuRoleMainMapper menuRoleMainMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(EditMenuRoleDetailDto dto) {
        CurrentUser currentUser = userInfoMapper.findCurrentUser(ShiroUtil.getUserId(),ShiroUtil.getOrgId());
        MenuRoleMainEntity menuRoleMainEntity = menuRoleMainMapper.selectById(dto.getRoleMainId());
        if(menuRoleMainEntity == null){
            try {
                throw new Exception("找不到角色");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        //作废此角色已有权限
        menuRoleDetailMapper.updateMenuRoleDetail(dto.getRoleMainId());
        if(dto.getBaseInfoIds() != null && dto.getBaseInfoIds().size() != 0) {
            List<MenuBaseInfoEntity> menuBaseInfoEntities = menuBaseInfoMapper.selectBatchIds(dto.getBaseInfoIds());
            List<MenuRoleDetailEntity> convert = BeanConverter.convert(MenuRoleDetailEntity.class, menuBaseInfoEntities);
            convert.stream().forEach(detail -> {
                detail.setRoleDetailId(String.valueOf(SnowflakeIdWorker.generateId()));
                detail.setRoleMainId(menuRoleMainEntity.getRoleMainId());
                detail.setRemark(menuRoleMainEntity.getRemark());
//                detail.setGroupId(user.getGroupId());
                detail.setOrgId(currentUser.getOrgId());
                detail.setCreateUser(currentUser.getUserName());
                menuRoleDetailMapper.insert(detail);
            });
        }
        return true;
    }
}
