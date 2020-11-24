package com.cargo.car.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.area.entity.AreCodeEntry;
import com.cargo.area.service.AreaService;
import com.cargo.car.dto.LineDto;
import com.cargo.car.entity.LineEntity;
import com.cargo.car.service.LineService;
import com.cargo.car.vo.CarVo;
import com.cargo.car.vo.LineVo;
import com.cargo.feign.service.FeignService;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
@Api(tags = "线路管理")
@RestController
@RequestMapping("/api/base/line")
public class LineController extends SuperController {
    @Autowired
    private LineService lineService;
    @Autowired
    private AreaService areaService;


    @ApiOperation(value = "获取线路管理列表")
    @GetMapping("/list")
    public ResponseInfo<Page<LineVo>> list()  {
        Page<LineVo> page = this.getPage(false);
        return ResponseUtil.success(lineService.queryForList(page));
    }
    @ApiOperation(value = "获取线路管理列表")
    @GetMapping("/get/{id}")
    public ResponseInfo list(@PathVariable("id") String id)  {
        return ResponseUtil.success(lineService.queryForOne(id));
    }

    @ApiOperation(value = "新增线路管理")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody LineDto dto)  {
        String userId = ShiroUtil.getUserId();
        LineEntity entity = new LineEntity();
        entity.setCreateUser(userId);
        entity = AreconverArea(entity,dto.getSenderAdCode(),dto.getDeliveryAdCode());
        return ResponseUtil.success(lineService.save(entity));
    }

    public LineEntity AreconverArea(LineEntity entity ,String senderAdCode,String DeliveryAdCode){
        AreCodeEntry senderByAdCode = areaService.getByAreaId(senderAdCode);
        if (!ObjectUtils.isEmpty(senderByAdCode)) {
            //市
            entity.setSenderAreaCityId(senderByAdCode.getCityCode());
            entity.setSenderAreaCityName(senderByAdCode.getCity());
            //区
            entity.setSenderAreaTownId(senderByAdCode.getAreaCode());
            entity.setSenderAreaTownName(senderByAdCode.getArea());
            //省
            entity.setSenderAreaProvinceId(senderByAdCode.getProvinceCode());
            entity.setSenderAreaProvinceName(senderByAdCode.getProvince());
        }
        //根据区域编码 去获取收货相应的区域名称
        AreCodeEntry deliveryByAdCode = areaService.getByAreaId(DeliveryAdCode);
        if (!ObjectUtils.isEmpty(deliveryByAdCode)) {
            //市
            entity.setDeliveryAreaCityId(deliveryByAdCode.getCityCode());
            entity.setDeliveryAreaCityName(deliveryByAdCode.getCity());
            //区
            entity.setDeliveryAreaTownId(deliveryByAdCode.getAreaCode());
            entity.setDeliveryAreaTownName(deliveryByAdCode.getArea());
            //省
            entity.setDeliveryAreaProvinceId(deliveryByAdCode.getProvinceCode());
            entity.setDeliveryAreaProvinceName(deliveryByAdCode.getProvince());
        }
        return entity;
    };

    @ApiOperation(value = "修改线路管理")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody LineDto dto)  {
        String userId = ShiroUtil.getUserId();
        LineEntity entity = new LineEntity();
        entity.setUpdateUser(userId);
        entity.setLineId(dto.getLineId());
        entity = AreconverArea(entity,dto.getSenderAdCode(),dto.getDeliveryAdCode());

        return ResponseUtil.success(lineService.updateById(entity));
    }

    @ApiOperation(value = "新增线路管理")
    @PostMapping("/delete")
    public ResponseInfo delete(@RequestBody List<String> ids)  {
        String userId = ShiroUtil.getUserId();
        UpdateWrapper<LineEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("line_id",ids);
        updateWrapper.set("is_able",0);
        return ResponseUtil.success(lineService.update(updateWrapper));
    }
}
