package com.cargo.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.user.entity.MenuBaseInfoEntity;
import com.cargo.user.vo.MenuBaseInfoVo;
import com.commom.cache.HeaderDto;
import com.commom.vo.CurrentUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
public interface MenuBaseInfoService extends IService<MenuBaseInfoEntity> {

    List<MenuBaseInfoVo> findByMenuBaseInfoList(String roleId, HeaderDto headerDto);

    List<MenuBaseInfoVo> findByMenuBaseInfoListByLogin(CurrentUser user, @Param("versionType") String versionType);

}
