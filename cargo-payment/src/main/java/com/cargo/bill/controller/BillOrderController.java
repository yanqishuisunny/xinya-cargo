package com.cargo.bill.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.bill.dto.BillOrderDto;
import com.cargo.bill.dto.BillOrderListDto;
import com.cargo.bill.dto.BillOrderListOwnerDto;
import com.cargo.bill.dto.BillOrderMergeDto;
import com.cargo.bill.entity.BillOrderEntity;
import com.cargo.bill.service.BillOrderService;
import com.cargo.bill.vo.AliPayBillOrderVo;
import com.cargo.bill.vo.BillOrderAnalyseVo;
import com.cargo.bill.vo.BillOrderVo;
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

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 帐单表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Api(tags = "帐单表")
@RestController
@RequestMapping("/api/payment/billOrder")
public class BillOrderController extends SuperController {

    @Autowired
    BillOrderService billOrderService;


    //订单完成，加入账单表
    @ApiOperation(value = "订单完成，加入账单表")
    @PostMapping("/add")
    public ResponseInfo addBillOrder(@RequestBody BillOrderDto dto){
        billOrderService.addBillOrder(dto);
        return ResponseUtil.success();
    }


    //账单列表查询(待审核，待付款)
    @ApiOperation(value = "账单列表查询(待审核，待付款)")
    @PostMapping("/list")
    public ResponseInfo billOrderList(@RequestBody BillOrderListDto dto){
        Page<BillOrderEntity> page = this.getPage(false);

        IPage<BillOrderVo> list = billOrderService.billOrderList(dto, page);

        return ResponseUtil.success(list);
    }


    //审核通过
    @ApiOperation(value = "审核通过")
    @PostMapping("/examine")
    public ResponseInfo examineBillOrder(@RequestBody BillOrderDto dto){
        billOrderService.examineBillOrder(dto);
        return ResponseUtil.success();
    }

    //付款按钮
    @ApiOperation(value = "付款按钮")
    @PostMapping("/pay")
    public ResponseInfo payBillOrder(@RequestBody BillOrderDto dto) throws Exception {
        String billOrderId = dto.getBillOrderId();
        dto.setBillOrderId(billOrderId);
        AliPayBillOrderVo aliPayBillOrderVo = billOrderService.payBillOrder(dto);
        return ResponseUtil.success(aliPayBillOrderVo);
    }

    //合并对账
    @ApiOperation(value = "合并对账")
    @PostMapping("/merge")
    public ResponseInfo mergeBillOrder(@RequestBody BillOrderMergeDto dto) {
        List<String> billOrderIds = dto.getBillOrderIds();
        billOrderService.mergeBillOrder(billOrderIds);
        return ResponseUtil.success();
    }


    @ApiOperation(value = "收款列表(待收款,已收款)")
    @PostMapping("/ownerList")
    public ResponseInfo billOrderListOwner(@RequestBody BillOrderListOwnerDto dto){
        Page<BillOrderEntity> page = this.getPage(false);
        IPage<BillOrderVo> list = billOrderService.billOrderListOwner(dto, page);
        return ResponseUtil.success(list);
    }

    @ApiOperation(value = "催款")
    @PostMapping("/press")
    public ResponseInfo pressBillOrder(@RequestBody BillOrderDto dto){
        billOrderService.pressBillOrder(dto);
        return ResponseUtil.success();
    }

    @ApiOperation(value = "收益分析")
    @PostMapping("/analysis")
    public ResponseInfo analysisBillOrder(@RequestBody BillOrderListDto dto) throws ParseException {
        BillOrderAnalyseVo billOrderAnalyseVo = billOrderService.analysisBillOrder(dto);
        return ResponseUtil.success(billOrderAnalyseVo);
    }
}

