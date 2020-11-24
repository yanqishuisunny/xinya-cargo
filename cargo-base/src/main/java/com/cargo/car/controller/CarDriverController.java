package com.cargo.car.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.car.dto.CarDriverDto;
import com.cargo.car.entity.CarDriverEntity;
import com.cargo.car.service.CarDriverService;
import com.cargo.car.service.CarService;
import com.cargo.car.vo.CarDriverVo;
import com.cargo.car.vo.CarVo;
import com.cargo.user.entity.DriverInformationEntity;
import com.cargo.user.service.DriverInformationService;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 车辆与司机关系表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-11-11
 */
@Api(tags = "车辆与司机关联操作")
@RestController
@RequestMapping("/api/base/carDriver")
public class CarDriverController {
    @Autowired
    private CarDriverService carDriverService;
    @Autowired
    private DriverInformationService driverInformationService;

    @ApiOperation(value = "添加司机与车辆绑定关系")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody CarDriverDto dto)  {
        return ResponseUtil.result(carDriverService.add(dto));
    }

    @ApiOperation(value = "根据车辆ID查找司机")
    @PostMapping("/list")
    public ResponseInfo list(@RequestBody CarDriverDto dto)  {
        QueryWrapper<CarDriverEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("car_id",dto.getCarIds());
        List<CarDriverEntity> list = carDriverService.list(queryWrapper);
        List<CarDriverVo> listVo = new ArrayList<>();
        list.forEach(item ->{
            CarDriverVo carDriverVo = new CarDriverVo();
            BeanUtils.copyProperties(item,carDriverVo);
            DriverInformationEntity byId = driverInformationService.getById(carDriverVo.getInformationId());
            if(byId != null){
                carDriverVo.setPhoneNo(byId.getPhoneNo());
                carDriverVo.setDriverName(byId.getIdCardName());
            }
            listVo.add(carDriverVo);
        });
        return ResponseUtil.result(listVo);
    }

    @ApiOperation(value = "编辑司机与车辆绑定关系")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody CarDriverDto dto)  {
        return ResponseUtil.result(carDriverService.edit(dto));
    }
}
