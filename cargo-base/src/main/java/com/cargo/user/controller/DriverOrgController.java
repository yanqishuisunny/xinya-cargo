package com.cargo.user.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cargo.user.dto.DriverOrgDto;
import com.cargo.user.dto.OrgDto;
import com.cargo.user.dto.UserInfoDto;
import com.cargo.user.entity.DriverOrgEntity;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.service.DriverOrgService;
import com.cargo.user.vo.DriverOrgVo;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 组织邀请司机记录表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10
 */
@Api(tags = "组织邀请司机记录表")
@RestController
@RequestMapping("/api/base/driver")
public class DriverOrgController extends SuperController {


    @Autowired
    private DriverOrgService driverOrgService;


    @ApiOperation(value = "app-查询为合作企业列表")
    @PostMapping("/appDriverOrgList")
    @Transactional
    public ResponseInfo<List<OrgEntity>> appDriverOrgList(@RequestBody OrgDto dto) {
        return ResponseUtil.result(driverOrgService.appDriverOrgList(dto,ShiroUtil.getUserId()));
    }



    @ApiOperation(value = "查询平台所有司机")
    @PostMapping("/driverlist")
    public ResponseInfo<IPage<UserInfoEntity>> driverlist(@RequestBody UserInfoDto dto) {
        IPage<UserInfoEntity> iPage = this.getPage(false);
        return ResponseUtil.result(driverOrgService.driverlist(iPage,dto,ShiroUtil.getOrgId()));
    }


    @ApiOperation(value = "司机加盟企业")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody DriverOrgDto dto) {
        return ResponseUtil.result(driverOrgService.add(dto));
    }



    @ApiOperation(value = "编辑企业司机合作关系")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody DriverOrgDto dto) {
        return ResponseUtil.result(driverOrgService.edit(dto));
    }



    @ApiOperation(value = "查询司机详情")
    @PostMapping("/detail")
    public ResponseInfo<DriverOrgVo> detail(@RequestBody DriverOrgDto dto) {
        return ResponseUtil.result(driverOrgService.detail(dto, ShiroUtil.getUserId()));
    }



    @ApiOperation(value = "PC-根据企业id查询合作的司机企业关系详情列表")
    @PostMapping("/pc-list")
    public ResponseInfo<IPage<DriverOrgEntity>> pcList(@RequestBody DriverOrgDto dto) {
        IPage<DriverOrgEntity> iPage = this.getPage(false);
        return ResponseUtil.result(driverOrgService.pcList(iPage,dto, ShiroUtil.getOrgId()));
    }



    @ApiOperation(value = "app-根据司机id查询关联的企业合作关系详情列表")
    @PostMapping("/app-list")
    public ResponseInfo<IPage<DriverOrgVo>> appList(@RequestBody DriverOrgDto dto) {
        IPage<DriverOrgEntity> iPage = this.getPage(false);
        return ResponseUtil.result(driverOrgService.appList(iPage,dto, ShiroUtil.getUserId()));
    }






}

