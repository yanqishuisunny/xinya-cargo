package com.cargo.car.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.car.dto.CarDto;
import com.cargo.car.entity.CarEntity;
import com.cargo.car.service.*;
import com.cargo.car.vo.CarVo;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.commons.beanutils.ConvertUtils.convert;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
@Api(tags = "车辆注册")
@RestController
@RequestMapping("/api/base/car")
public class CarController extends SuperController {
    @Autowired
    private CarService carService;


    @ApiOperation(value = "获取车辆列表")
    @PostMapping("/list")
    public ResponseInfo<Page<CarVo>> list(@RequestBody CarDto dto)  {
        Page<CarVo> page = this.getPage(false);
        if(dto.getType().equals("2")){
            dto.setCreateUser(ShiroUtil.getUserId());
        }
        return ResponseUtil.success(carService.queryForList(page,dto));
    }
    @ApiOperation(value = "获取车辆")
    @GetMapping("/get/{carId}")
    public ResponseInfo get(@PathVariable("carId") String carId)  {
        CarVo vo = carService.queryForOne(carId);
        return ResponseUtil.success(vo);
    }


    @ApiOperation(value = "新增车辆")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody CarDto dto)  {
        CarEntity entity = new CarEntity();
        BeanUtils.copyProperties(dto,entity);
        entity.setAuditStatus("1");
        entity.setCreateUser(ShiroUtil.getUserId());
        return ResponseUtil.result(carService.save(entity));
    }

    @ApiOperation(value = "编辑车辆")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody List<CarDto> listDto)  {
        return ResponseUtil.result(carService.updateByIds(listDto));
    }
    @ApiOperation(value = "编辑车辆状态")
    @PostMapping("/editStatus")
    public ResponseInfo editStatus(@RequestBody CarDto dto)  {
        return ResponseUtil.result(carService.updateStatus(dto));
    }

    @ApiOperation(value = "删除车辆")
    @PostMapping("/delete")
    public ResponseInfo delete(@RequestBody List<String> carIds)  {
        UpdateWrapper<CarEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("car_id",carIds);
        updateWrapper.set("is_able",0);
        return ResponseUtil.result(carService.update(updateWrapper));
    }
}
