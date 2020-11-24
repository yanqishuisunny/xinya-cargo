package com.cargo.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.order.dto.GeneralOrderDto;
import com.cargo.order.entity.GeneralOrderEntity;
import com.cargo.order.entity.IntentionLogEntity;
import com.cargo.order.service.GeneralOrderService;
import com.cargo.order.vo.GeneralOrderVo;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-11-09
 */
@Slf4j
@Api(tags = "订单管理")
@RestController
@RequestMapping("/api/order/generalOrder")
public class GeneralOrderController extends SuperController {
    @Autowired
    private GeneralOrderService generalOrderService;


    @ApiOperation(value = "订单列表")
    @PostMapping("/list")
    public ResponseInfo<Page<GeneralOrderVo>> list(@RequestBody GeneralOrderDto dto) {
        Page<GeneralOrderVo> page = this.getPage(false);
        dto.setCreateUser(ShiroUtil.getUserId());
        log.info("dto:{}", JSONObject.toJSONString(dto));
        return ResponseUtil.success(generalOrderService.queryForList(dto,page));
    }
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody GeneralOrderDto dto) {
        return ResponseUtil.result(generalOrderService.add(dto));
    }

    @GetMapping("/get/{generalOrderId}")
    public ResponseInfo add(@PathVariable("generalOrderId") String generalOrderId) {
        return ResponseUtil.success(generalOrderService.queryForOne(generalOrderId));
    }

    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody GeneralOrderDto dto) {

        return ResponseUtil.result(generalOrderService.updateStatus(dto));
    }

    @PostMapping("/orderCensus")
    public ResponseInfo orderCensus(@RequestBody GeneralOrderDto dto) {
        return ResponseUtil.result(generalOrderService.orderCensus(dto));
    }
/*
    @PostMapping("/getWayBillByGenId")
    public ResponseInfo getWayBillByGenId(@RequestBody GeneralOrderDto dto) {
        dto.setLoginUserId(ShiroUtil.getUserId());
        return ResponseUtil.result(generalOrderService.getWayBillByGenId(dto));
    }*/

}
