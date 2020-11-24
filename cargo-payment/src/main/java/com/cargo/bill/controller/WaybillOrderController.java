package com.cargo.bill.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.bill.dto.WaybillOrderDto;
import com.cargo.bill.dto.WaybillOrderListDto;
import com.cargo.bill.entity.WaybillOrderEntity;
import com.cargo.bill.service.WaybillOrderService;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 帐单扩展_运单(司机对账)表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-18
 */
@Api(tags = "帐单扩展_运单(司机对账)表")
@RestController
@RequestMapping("/api/payment/waybillorder")
public class WaybillOrderController extends SuperController {

    @Autowired
    WaybillOrderService waybillOrderService;

    @ApiOperation(value = "司机对账_列表")
    @PostMapping("/list")
    public ResponseInfo waybillList(@RequestBody WaybillOrderListDto dto){

        Page<WaybillOrderEntity> page = this.getPage(false);
        waybillOrderService.waybillList(dto,page);

        return ResponseUtil.success();
    }

    @ApiOperation(value = "司机对账_核销")
    @PostMapping("/confirm")
    public  ResponseInfo confirmWayBill(WaybillOrderDto dto){
        waybillOrderService.confirmWayBill(dto);
        return ResponseUtil.success();
    }

    @ApiOperation(value = "司机对账_付款")
    @PostMapping("/pay")
    public  ResponseInfo payWayBill(WaybillOrderDto dto){
        waybillOrderService.payWayBill(dto);
        return ResponseUtil.success();
    }
}

