package com.cargo.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.user.dto.EditMenuBaseInfoDto;
import com.cargo.user.dto.MenuBaseInfoDto;
import com.cargo.user.entity.MenuBaseInfoEntity;
import com.cargo.user.entity.MenuRoleMainEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.MenuBaseInfoService;
import com.cargo.user.service.MenuRoleMainService;
import com.cargo.user.vo.MenuBaseInfoVo;
import com.commom.cache.Constant;
import com.commom.core.BeanConverter;
import com.commom.gpsUtils.StringUtil;
import com.commom.shiro.ShiroUtil;
import com.commom.snowflake.SnowflakeIdWorker;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import com.commom.utils.TreeUtils;
import com.commom.vo.CurrentUser;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Slf4j
@Api(tags = "菜单表")
@RestController
@RequestMapping("/api/base/baseInfo")
public class MenuBaseInfoController extends SuperController {

    @Autowired
    private MenuBaseInfoService menuBaseInfoService;

    @Autowired
    private MenuRoleMainService menuRoleMainService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @ApiOperation(value = "创建权限")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody MenuBaseInfoDto dto) {
        String userId = ShiroUtil.getUserId();
        UserInfoEntity userInfoEntity = userInfoMapper.selectById(userId);
        QueryWrapper<MenuBaseInfoEntity> qw = new QueryWrapper<>();
        qw.eq("menu_code", dto.getMenuCode());
        qw.eq("menu_type", dto.getMenuType());
        dto.setBaseInfoId(String.valueOf(SnowflakeIdWorker.generateId()));

        int count = menuBaseInfoService.count(qw);
        if (count >= 1) {
            return ResponseUtil.error("权限已存在");
        }
        if (Strings.isNullOrEmpty(dto.getParentId())) {
            dto.setParentId("0");
        }
        MenuBaseInfoEntity entity = BeanConverter.convert(MenuBaseInfoEntity.class, dto);
        if(null != userInfoEntity){
            entity.setCreateUser(userInfoEntity.getUserCode());
        }
        return ResponseUtil.result(menuBaseInfoService.saveOrUpdate(entity));
    }


    @ApiOperation(value = "编辑权限")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody EditMenuBaseInfoDto dto) {
        String userId = ShiroUtil.getUserId();
        UserInfoEntity userInfoEntity = userInfoMapper.selectById(userId);
        QueryWrapper<MenuBaseInfoEntity> qw = new QueryWrapper<>();
        qw.eq("menu_code", dto.getMenuCode());
        qw.eq("menu_type", dto.getMenuType());
        qw.ne("base_info_id",dto.getBaseInfoId());
        int count = menuBaseInfoService.count(qw);
        if (count >= 1) {
            return ResponseUtil.error("权限已存在");
        }
        if (Strings.isNullOrEmpty(dto.getParentId())) {
            dto.setParentId("0");
        }
        MenuBaseInfoEntity entity = BeanConverter.convert(MenuBaseInfoEntity.class, dto);
        if(null != userInfoEntity){
            entity.setUpdateUser(userInfoEntity.getUserCode());
        }
        return ResponseUtil.result(menuBaseInfoService.saveOrUpdate(entity));
    }


    @ApiOperation(value = "获取权限树")
    @GetMapping("/tree")
    public ResponseInfo getMenuBaseInfoList() {
        String userId = ShiroUtil.getUserId();
        QueryWrapper<MenuBaseInfoEntity> qw = new QueryWrapper<>();
        qw.eq("is_able", Constant.AbleEnum.YES.getValue());
        qw.eq("version_type", this.getHeaders().getVersionType());
        qw.eq("status", Constant.Status.ENABLE.getValue());
        qw.eq("flg_hidden", Constant.FlgHidden.DISABLED.getValue());
        List<MenuBaseInfoEntity> list = menuBaseInfoService.list(qw);
        List<MenuBaseInfoVo> listVo = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MenuBaseInfoEntity p = list.get(i);
            MenuBaseInfoVo vo = BeanConverter.convert(MenuBaseInfoVo.class, p);
            vo.setId(p.getBaseInfoId());
            listVo.add(vo);
        }
        return ResponseUtil.success(listVo.stream()
                .filter(n -> Objects.equals("0", n.getParentId()))
                .map(e -> TreeUtils.findChildren(e, listVo))
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "根据角色ID获取权限树")
    @GetMapping("/tree/{roleId}")
    public ResponseInfo getMenuBaseInfoList(@ApiParam("角色ID") @PathVariable("roleId") String roleId) {
        return ResponseUtil.success(menuBaseInfoService.findByMenuBaseInfoList(roleId,this.getHeaders()));
    }

    @ApiOperation(value = "根据当前登陆人获取权限树")
    @GetMapping("/loginbytree")
    public ResponseInfo loginbytree() {
        CurrentUser currentUser = userInfoMapper.findCurrentUser(ShiroUtil.getUserId(),ShiroUtil.getOrgId());
        JSONObject jsonObject = new JSONObject();
        if(null == currentUser){
            jsonObject.put("listResource", new ArrayList<>());
            return ResponseUtil.success(jsonObject);
        }
        if(!StringUtil.isEmpty(currentUser.getAdminId()) && currentUser.getAdminId().equals(ShiroUtil.getUserId())){
            QueryWrapper<MenuBaseInfoEntity> qw = new QueryWrapper<>();
            qw.eq("status", Constant.Status.ENABLE.getValue());
            qw.eq("version_type", this.getHeaders().getVersionType());
            List<MenuBaseInfoEntity> list = menuBaseInfoService.list(qw);
            List<MenuBaseInfoVo> listVo = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                MenuBaseInfoEntity p = list.get(i);
                MenuBaseInfoVo vo = BeanConverter.convert(MenuBaseInfoVo.class, p);
                vo.setId(p.getBaseInfoId());
                listVo.add(vo);
            }
            List<MenuBaseInfoVo> collect = listVo.stream()
                    .filter(n -> Objects.equals("0", n.getParentId()))
                    .map(e -> TreeUtils.findChildren(e, listVo))
                    .collect(Collectors.toList());
            jsonObject.put("listResource", collect);
        }else {
            QueryWrapper<MenuRoleMainEntity> qw = new QueryWrapper<>();
            qw.eq("role_main_id", currentUser.getRoleId());
            qw.eq("status",Constant.AbleEnum.NO.getValue());
            int count = menuRoleMainService.count(qw);
            if(count >= 1){
                jsonObject.put("listResource", new ArrayList<>());
            }else {
                jsonObject.put("listResource", menuBaseInfoService.findByMenuBaseInfoListByLogin(currentUser,this.getHeaders().getVersionType()));
            }
        }
        return ResponseUtil.success(jsonObject);
    }


}

