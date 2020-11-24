package com.cargo.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.user.dto.EditMenuRoleMainDto;
import com.cargo.user.dto.MenuRoleMainDto;
import com.cargo.user.entity.MenuRoleDetailEntity;
import com.cargo.user.entity.MenuRoleMainEntity;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.MenuRoleDetailService;
import com.cargo.user.service.MenuRoleMainService;
import com.cargo.user.vo.MenuRoleMainVo;
import com.commom.cache.Constant;
import com.commom.core.BeanConverter;
import com.commom.shiro.ShiroUtil;
import com.commom.snowflake.SnowflakeIdWorker;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import com.commom.vo.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 菜单角色主表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Slf4j
@Api(tags = "菜单角色主表")
@RestController
@RequestMapping("/api/base/menuMain")
public class MenuRoleMainController extends SuperController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private MenuRoleMainService menuRoleMainService;

    @Autowired
    private MenuRoleDetailService menuRoleDetailService;


    @ApiOperation(value = "创建角色")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody MenuRoleMainDto dto) {
        CurrentUser currentUser = userInfoMapper.findCurrentUser(ShiroUtil.getUserId(),ShiroUtil.getOrgId());
        QueryWrapper<MenuRoleMainEntity> qw = new QueryWrapper<>();
        qw.eq("org_id",currentUser.getOrgId());
        qw.eq("role_code",dto.getRoleCode());
        qw.eq("role_name", dto.getRoleName());
        int count = menuRoleMainService.count(qw);
        if (count >= 1) {
            return ResponseUtil.error("角色已存在");
        }

        MenuRoleMainEntity entity = BeanConverter.convert(MenuRoleMainEntity.class, dto);
        entity.setRoleMainId(String.valueOf(SnowflakeIdWorker.generateId()));
        entity.setOrgId(currentUser.getOrgId());
        entity.setCreateUser(currentUser.getUserCode());
        entity.setGmtCreate(LocalDateTime.now());
        return ResponseUtil.result(menuRoleMainService.save(entity));
    }

    @ApiOperation(value = "编辑角色")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody EditMenuRoleMainDto dto) {
        CurrentUser currentUser = userInfoMapper.findCurrentUser(ShiroUtil.getUserId(),ShiroUtil.getOrgId());
        QueryWrapper<MenuRoleMainEntity> qw = new QueryWrapper<>();
//        qw.eq("group_id",user.getGroupId());
        qw.eq("org_id",currentUser.getOrgId());
        qw.eq("role_code",dto.getRoleCode());
        qw.eq("role_name", dto.getRoleName());
        qw.ne("role_main_id",dto.getRoleMainId());
        int count = menuRoleMainService.count(qw);
        if (count >= 1) {
            return ResponseUtil.error("角色已存在");
        }
        MenuRoleMainEntity entity = BeanConverter.convert(MenuRoleMainEntity.class, dto);
        entity.setUpdateUser(currentUser.getUserName());
        return ResponseUtil.result(menuRoleMainService.updateById(entity));
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/del")
    public ResponseInfo del(@RequestBody EditMenuRoleMainDto dto) {
        CurrentUser currentUser = userInfoMapper.findCurrentUser(ShiroUtil.getUserId(),ShiroUtil.getOrgId());
        QueryWrapper<MenuRoleDetailEntity> qw = new QueryWrapper<>();
        qw.in("role_main_id", dto.getMenuRoleMainIds());

        int count = menuRoleDetailService.count(qw);

        if (count >= 1) {
            return ResponseUtil.error("角色存在级联数据删除失败");
        }
        UpdateWrapper<MenuRoleMainEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("role_main_id",dto.getMenuRoleMainIds());
        updateWrapper.set("is_able", Constant.AbleEnum.NO.getValue());
        updateWrapper.set("update_user",currentUser.getUserName());
        return ResponseUtil.result(menuRoleMainService.update(updateWrapper));
    }

    @ApiOperation(value = "查询角色列表")
    @PostMapping(value = "/list")
    public ResponseInfo<Page<MenuRoleMainVo>> findByPageRole(@RequestBody MenuRoleMainDto menuRoleMainDto) {
        Page<MenuRoleMainVo> page = this.getPage(false);
        CurrentUser currentUser = userInfoMapper.findCurrentUser(ShiroUtil.getUserId(),ShiroUtil.getOrgId());
        return ResponseUtil.success(menuRoleMainService.findByPageMenuRoleMain(menuRoleMainDto, currentUser, page));
    }

    @ApiOperation(value = "根据ID获得角色")
    @GetMapping("/get/{roleMainId}")
    public ResponseInfo<MenuRoleMainEntity> findById(@ApiParam("角色ID") @PathVariable("roleMainId") String roleMainId){
        return ResponseUtil.success(menuRoleMainService.getById(roleMainId));
    }

}

