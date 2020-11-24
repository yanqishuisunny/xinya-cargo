package com.cargo.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.order.dto.ConsignorReleaseDto;
import com.cargo.order.dto.IntentionOrderDto;
import com.cargo.order.entity.ConsignorReleaseEntity;
import com.cargo.order.entity.IntentionOrderEntity;
import com.cargo.order.service.IntentionLogService;
import com.cargo.order.service.IntentionOrderService;
import com.cargo.order.vo.IntentionOrderVo;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-10-28
 */
@Slf4j
@Api(tags = "意向单操作")
@RestController
@RequestMapping("/api/order/intentionOrder")
public class IntentionOrderController extends SuperController {
    @Autowired
    private IntentionOrderService intentionOrderService;

    @ApiOperation(value = "获取发出/收到列表")
    @GetMapping("/list")
    public ResponseInfo<Page<IntentionOrderVo>> list(@ModelAttribute IntentionOrderDto dto) {
        Page<IntentionOrderVo> page = this.getPage(false);
        dto.setCurrentUserId(ShiroUtil.currentUserId());
        return ResponseUtil.success(intentionOrderService.queryForList(dto,page));
    }

    @ApiOperation(value = "承运商对货主发布信息发出意向")
    @PostMapping("/shipperIntAdd")
    public ResponseInfo shipperIntAdd(@RequestBody IntentionOrderDto dto) {
        return ResponseUtil.result(intentionOrderService.saveIntentionOrder(dto));
    }

    @ApiOperation(value = "意向单详情")
    @GetMapping("/get/{intentionOrderId}")
    public ResponseInfo list(@PathVariable String intentionOrderId) {
        return ResponseUtil.success(intentionOrderService.queryForOne(intentionOrderId));
    }

    @ApiOperation(value = "更新意向单状态")
    @PostMapping("/editStatus")
    public ResponseInfo editStatus(@RequestBody IntentionOrderDto dto) {
        log.info("更新意向单状态 入参:{}", JSONObject.toJSONString(dto));

        return ResponseUtil.result(intentionOrderService.updateStatus(dto));
    }
    @ApiOperation(value = "更新意向单")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody IntentionOrderDto dto) {
        log.info("更新意向单状态 入参:{}", JSONObject.toJSONString(dto));
        UpdateWrapper<IntentionOrderEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("intention_order_id",dto.getIntentionOrderId());
        updateWrapper.set("price",dto.getPrice());

        return ResponseUtil.result(intentionOrderService.update(updateWrapper));
    }

    @ApiOperation(value = "删除意向单")
    @PostMapping("/delete")
    public ResponseInfo delete(@RequestBody List<String> ids) {
        log.info("更新意向单状态 入参:{}", JSONObject.toJSONString(ids));

        return ResponseUtil.result(intentionOrderService.updateByIds(ids));
    }
}
