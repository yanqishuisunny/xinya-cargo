package com.cargo.carrier.controller;

import com.cargo.carrier.dto.CarrierDto;
import com.cargo.carrier.service.CarrierService;
import com.cargo.carrier.vo.CarrierVo;
import com.cargo.carrier.vo.CarrierdetailVo;
import com.cargo.feign.dto;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 承运商
 * @Auther: xinzs
 * @Date: 2020/11/5 14:35
 */
@Api(tags = "承运商")
@RestController
@RequestMapping("/api/base/carrier")
public class CarrierController {

    @Autowired
    CarrierService carrierService;

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public ResponseInfo addCarrier(@RequestBody CarrierDto dto){
        String orgId = carrierService.addCarrier(dto);
        return ResponseUtil.success(orgId);
    }


    @ApiOperation(value = "更新")
    @PostMapping("/update")
    public ResponseInfo updateCarrier(@RequestBody CarrierDto dto){
        carrierService.updateCarrier(dto);
        return ResponseUtil.success();
    }


    @ApiOperation(value = "详情")
    @PostMapping("/detail")
    public ResponseInfo selectCarrierById(@RequestBody CarrierDto dto){
        String orgId = dto.getOrgId();//企业ID
        CarrierdetailVo carrierVo = carrierService.selectCarrierById(orgId);
        return ResponseUtil.success(carrierVo);
    }

    @ApiOperation(value = "详情")
    @PostMapping("/detailByCarrierId")
    public ResponseInfo<CarrierdetailVo> selectCarrierByRoleId(@RequestBody String id){
        CarrierdetailVo carrierdetailVo = carrierService.selectCarrierByRoleId(id);
        return ResponseUtil.success(carrierdetailVo);
    }
}
