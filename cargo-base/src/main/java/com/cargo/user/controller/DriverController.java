package com.cargo.user.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cargo.user.dto.DriverDto;
import com.cargo.user.entity.DriverEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.service.DriverService;
import com.cargo.user.vo.DriverOrgVo;
import com.cargo.user.vo.DriverVo;
import com.cargo.user.vo.UserInfoVo;
import com.commom.shiro.ShiroUtil;
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

import java.util.List;

/**
 * <p>
 * 司机合作关系表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-13
 */
@Api(tags = "司机和司机合作关系表")
@RestController
@RequestMapping("/api/base/tDriver")
public class DriverController extends SuperController {

    @Autowired
    private DriverService driverService;



    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody DriverDto dto) {
        return ResponseUtil.result(driverService.add(dto));
    }


    @ApiOperation(value = "编辑")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody DriverDto dto) {
        return ResponseUtil.result(driverService.edit(dto));
    }



    @ApiOperation(value = "查询司机和合作的司机详情")
    @PostMapping("/detail")
    public ResponseInfo<DriverOrgVo> detail(@RequestBody DriverDto dto) {
        return ResponseUtil.result(driverService.detail(dto, ShiroUtil.getUserId()));
    }



    @ApiOperation(value = "查询司机和合作的司机列表")
    @PostMapping("/list")
    public ResponseInfo<IPage<DriverVo>> pcList(@RequestBody DriverDto dto) {
        IPage<DriverEntity> iPage = this.getPage(false);
        return ResponseUtil.result(driverService.list(iPage,dto));
    }


    @ApiOperation(value = "查询平台其他司机列表")
    @PostMapping("/driverlist")
    public ResponseInfo<IPage<UserInfoVo>> driverlist(@RequestBody DriverDto dto) {
        IPage<UserInfoEntity> iPage = this.getPage(false);
        return ResponseUtil.result(driverService.driverlist(iPage,dto));
    }



    @ApiOperation(value = "查询司机和合作的司机列表(包括自己)")
    @PostMapping("/driList")
    public ResponseInfo<List<DriverVo>> driList(@RequestBody DriverDto dto) {
        return ResponseUtil.result(driverService.driList(dto));
    }

}

