package com.cargo.location.controller;

import com.cargo.location.entity.DeviceInfoEntity;
import com.cargo.location.service.DeviceInfoService;
import com.commom.shiro.ShiroUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "设备信息controller")
@RestController
@RequestMapping("/api/gps/deviceInfo")
public class DeviceInfoController {

    @Autowired
    private DeviceInfoService deviceInfoService;



    @GetMapping("/queryDevice")
    @ApiOperation(value = "获取某一个设备信息，根据设备主键查询")
    public ResponseInfo<DeviceInfoEntity> queryDevice(@ApiParam(value = "设备主键", required = true) String deviceId) {
        String userId = ShiroUtil.currentUserId();
        return ResponseUtil.success(deviceInfoService.queryDeviceInfo(deviceId,userId));
    }





}
