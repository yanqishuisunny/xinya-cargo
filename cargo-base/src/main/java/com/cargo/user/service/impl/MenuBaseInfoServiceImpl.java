package com.cargo.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.user.entity.MenuBaseInfoEntity;
import com.cargo.user.mapper.MenuBaseInfoMapper;
import com.cargo.user.service.MenuBaseInfoService;
import com.cargo.user.vo.MenuBaseInfoVo;
import com.commom.cache.HeaderDto;
import com.commom.utils.TreeUtils;
import com.commom.vo.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Service
public class MenuBaseInfoServiceImpl extends ServiceImpl<MenuBaseInfoMapper, MenuBaseInfoEntity> implements MenuBaseInfoService {

    @Autowired
    private MenuBaseInfoMapper menuBaseInfoMapper;


    @Override
    public List<MenuBaseInfoVo> findByMenuBaseInfoList(String roleId,HeaderDto headerDto) {
        List<MenuBaseInfoVo> list = menuBaseInfoMapper.findMenuBaseInfoList(roleId,headerDto.getVersionType());
        return list.stream()
                .filter(n -> Objects.equals("0", n.getParentId()))
                .map(e -> TreeUtils.findChildren(e, list))
                .collect(Collectors.toList());
    }


    @Override
    public List<MenuBaseInfoVo> findByMenuBaseInfoListByLogin(CurrentUser user, String versionType) {
        List<MenuBaseInfoVo> list = menuBaseInfoMapper.findByMenuBaseInfoListByLogin(user,versionType);
        return list.stream()
                .filter(n -> Objects.equals("0", n.getParentId()))
                .map(e -> TreeUtils.findChildren(e, list))
                .collect(Collectors.toList());
    }

}
