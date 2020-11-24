package com.cargo.user.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.core.BaseBusCode;
import com.cargo.user.dto.UserInfoDto;
import com.cargo.user.dto.UserInfoRetDto;
import com.cargo.user.entity.OrgUserAssociationEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.OrgUserAssociationService;
import com.cargo.user.service.UserInfoService;
import com.cargo.user.vo.UserInfoVo;
import com.commom.cache.Constant;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.gpsUtils.StringUtil;
import com.commom.shiro.ShiroUtil;
import com.commom.snowflake.SnowflakeIdWorker;
import com.commom.supper.SuperController;
import com.commom.utils.MD5Util;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import com.commom.utils.Tool;
import com.commom.vo.CurrentUser;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Api(tags = "用户表")
@RestController
@RequestMapping("/api/base/userInfo")
public class UserInfoController extends SuperController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private OrgUserAssociationService orgUserAssociationService;


    @ApiOperation(value = "创建用户")
    @PostMapping("/add")
    @Transactional
    public ResponseInfo add(@RequestBody UserInfoDto dto) {
        String orgId = ShiroUtil.getOrgId();
        if(StringUtil.isEmpty(orgId)){
            throw new BussException("登录用户没有企业信息");
        }
        CurrentUser currentUser = userInfoMapper.findCurrentUser(ShiroUtil.getUserId(),ShiroUtil.getOrgId());
        QueryWrapper<UserInfoEntity> qw = new QueryWrapper<>();
        qw.eq("user_code", dto.getUserCode());
        int count = userInfoService.count(qw);
        if (count >= 1) {
            return ResponseUtil.error("用户已存在");
        }
        UserInfoEntity entity = BeanConverter.convert(UserInfoEntity.class, dto);
        entity.setUserId(String.valueOf(SnowflakeIdWorker.generateId()));
        entity.setCreateUser(currentUser.getUserName());
        entity.setGmtCreate(LocalDateTime.now());
        entity.setUserType(CurrentUser.USERTYPE_1);
        entity.setSalt(Tool.uuId());
        if (!Strings.isNullOrEmpty(dto.getPassword())) {
            entity.setPassword(MD5Util.MD5Encode(dto.getPassword() + entity.getSalt()));
        }
        userInfoService.save(entity);
        //保存用户和企业关系
        OrgUserAssociationEntity orgUserAssociationEntity = new OrgUserAssociationEntity();
        orgUserAssociationEntity.setAssociationId(String.valueOf(SnowflakeIdWorker.generateId()));
        orgUserAssociationEntity.setUserId(entity.getUserId());
        orgUserAssociationEntity.setRoleCode(dto.getMenuRole());
        orgUserAssociationEntity.setRoleId(dto.getMenuRole());
        orgUserAssociationEntity.setOrgId(currentUser.getOrgId());
        orgUserAssociationEntity.setLastLoginTime(LocalDateTime.now());
        orgUserAssociationEntity.setOrgRole(this.getHeaders().getVersionType());
        return ResponseUtil.result(orgUserAssociationService.save(orgUserAssociationEntity));
    }


    @ApiOperation(value = "查询用户列表")
    @PostMapping(value = "/list")
    public ResponseInfo<Page<UserInfoVo>> findByPageUserInfo(@RequestBody UserInfoDto dto) {
        Page<UserInfoVo> page = this.getPage(false);
        CurrentUser currentUser = new CurrentUser();
        currentUser.setOrgId(ShiroUtil.getOrgId());
        return ResponseUtil.success(userInfoService.findByPageUserInfo(dto, currentUser, page,this.getHeaders()));
    }


    @ApiOperation(value = "根据ID获得用户")
    @GetMapping("/get/{userId}")
    public ResponseInfo<UserInfoVo> findById(@ApiParam("用户ID") @PathVariable("userId") String userId){
        return ResponseUtil.success(userInfoService.convert(userInfoService.getById(userId)));
    }


    @ApiOperation(value = "删除用户")
    @PostMapping("/del")
    @Transactional
    public ResponseInfo del(@RequestBody UserInfoDto dto) {
        UserInfoEntity userInfoEntity = userInfoService.getById(ShiroUtil.getUserId());
        UpdateWrapper<UserInfoEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("user_id",dto.getUserIds());
        updateWrapper.set("is_able", Constant.AbleEnum.NO.getValue());
        updateWrapper.set("update_user",userInfoEntity.getUserName());
        userInfoService.update(updateWrapper);
        //保存用户和企业关系
        UpdateWrapper<OrgUserAssociationEntity> associationUpdateWrapper = new UpdateWrapper<>();
        associationUpdateWrapper.in("user_id",dto.getUserIds());
        associationUpdateWrapper.eq("org_id",ShiroUtil.getOrgId());
        associationUpdateWrapper.set("is_able", Constant.AbleEnum.NO.getValue());
//        associationUpdateWrapper.set("update_user",userInfoEntity.getUserName());
        orgUserAssociationService.update(associationUpdateWrapper);
        return ResponseUtil.result(BaseBusCode.SUCCESS);
    }


    @ApiOperation(value = "编辑用户")
    @PostMapping("/edit")
    @Transactional
    public ResponseInfo edit(@RequestBody UserInfoDto dto) {
        UserInfoEntity userInfoEntity = userInfoService.getById(ShiroUtil.getUserId());
        QueryWrapper<UserInfoEntity> qw = new QueryWrapper<>();
        qw.eq("user_code", dto.getUserCode());
        qw.eq("user_name", dto.getUserName());
        qw.ne("user_id",dto.getUserId());
        int count = userInfoService.count(qw);
        if (count >= 1) {
            return ResponseUtil.error("用户已存在");
        }
        UserInfoEntity entity = BeanConverter.convert(UserInfoEntity.class, dto);
        entity.setUpdateUser(userInfoEntity.getUserName());
        userInfoService.updateById(entity);
        QueryWrapper<OrgUserAssociationEntity> associationWrapper = new QueryWrapper<>();
        associationWrapper.eq("org_id",ShiroUtil.getOrgId());
        associationWrapper.eq("user_id", dto.getUserId());
        associationWrapper.eq("org_role",this.getHeaders().getVersionType());
        OrgUserAssociationEntity orgUserAssociationEntity = orgUserAssociationService.getOne(associationWrapper);
        if(null != orgUserAssociationEntity){
            orgUserAssociationEntity.setRoleId(dto.getMenuRole());
            orgUserAssociationEntity.setRoleCode(dto.getMenuRole());
        }
        return ResponseUtil.result(orgUserAssociationService.updateById(orgUserAssociationEntity));
    }


    /**
     * 重置密码，忘记密码
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "0503-重置密码，忘记密码")
    @PostMapping(value = "/resetPassword")
    public ResponseInfo updatePassword(@RequestBody @Validated UserInfoRetDto dto) {
        String userId = ShiroUtil.currentUserId();
        return ResponseUtil.success(userInfoService.editPassword(userId , dto.getPassword()));
    }



}

