package com.cargo.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.user.dto.EditMenuRoleDetailDto;
import com.cargo.user.entity.MenuRoleDetailEntity;
import com.cargo.user.service.MenuRoleDetailService;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜单角色明细表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Api(tags = "菜单角色明细表")
@RestController
@RequestMapping("/api/base/menuDetail")
public class MenuRoleDetailController extends SuperController {
    @Autowired
    private MenuRoleDetailService menuRoleDetailService;


    @ApiOperation(value = "配置权限")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody EditMenuRoleDetailDto dto) {
        QueryWrapper<MenuRoleDetailEntity> qw = new QueryWrapper<>();
        qw.eq("role_main_id", dto.getRoleMainId());
        return ResponseUtil.result(menuRoleDetailService.edit(dto));
    }
}

