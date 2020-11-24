package com.cargo.feign.service;

import com.cargo.feign.entity.AreCodeEntry;
import com.cargo.feign.entity.OrgEntity;
import com.cargo.order.vo.*;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(value = "xinya-base" ,fallback = BaseFallbackService.class)
public interface BaseFeignService {

    @ApiOperation(value = "获取货主发布信息视图")
    @PostMapping("/ms/service/consignorRelease/converlist")
    List<ConsignorReleaseVo> converlist(@RequestBody List<ConsignorReleaseVo> list);

    @ApiOperation(value = "获取承運商发布信息视图")
    @PostMapping("/ms/service/carrierRelease/converCarrierlist")
    List<CarrierReleaseVo> converCarrierlist(@RequestBody List<CarrierReleaseVo> list);

    @ApiOperation(value = "获得省市区根据AdCode")
    @GetMapping("/ms/service/area/getByAdCode/{adCode}")
    AreCodeEntry findByAdCode(@PathVariable("adCode") String adCode);

    @ApiOperation(value = "获取意向单视图")
    @PostMapping("/ms/service/intentionOrder/converList")
    List<IntentionOrderVo> converIntentionOrder(@RequestBody List<IntentionOrderVo> list);

    @ApiOperation(value = "获取订单视图")
    @PostMapping("/ms/service/order/converOrder")
    List<GeneralOrderVo> converOrder(@RequestBody List<GeneralOrderVo> listVo);

    @ApiOperation(value = "获取车辆视图")
    @GetMapping("/ms/service/car/get/{carId}")
    CarVo getCar(@PathVariable("carId") String carId);

    @ApiOperation(value = "获取企业信息")
    @PostMapping("/ms/service/org/get/{orgId}")
    OrgEntity getOrgById(@PathVariable String orgId);
}
