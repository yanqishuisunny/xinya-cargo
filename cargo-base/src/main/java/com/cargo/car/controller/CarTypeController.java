package com.cargo.car.controller;


import com.cargo.car.service.CarTypeService;
import com.cargo.order.service.GoodsCategoryService;
import com.commom.supper.SuperController;
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
 * @since 2020-10-26
 */
@Api(tags = "车辆类型")
@RestController
@RequestMapping("/api/base/carType")
public class CarTypeController  extends SuperController {
    @Autowired
    private CarTypeService carTypeService;

    @ApiOperation(value = "获取车辆类型列表")
    @GetMapping("/list")
    public ResponseInfo list()  {
        return ResponseUtil.success(carTypeService.list());
    }
}
