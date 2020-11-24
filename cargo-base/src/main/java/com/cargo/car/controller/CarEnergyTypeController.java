package com.cargo.car.controller;


import com.cargo.car.service.CarCardTypeService;
import com.cargo.car.service.CarEnergyTypeService;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
@Api(tags = "能源类型")
@RestController
@RequestMapping("/api/base/carEnergyType")
public class CarEnergyTypeController {
    @Autowired
    private CarEnergyTypeService carEnergyTypeService;

    @ApiOperation(value = "获取能源类型列表")
    @GetMapping("/list")
    public ResponseInfo list()  {
        return ResponseUtil.success(carEnergyTypeService.list());
    }
}
