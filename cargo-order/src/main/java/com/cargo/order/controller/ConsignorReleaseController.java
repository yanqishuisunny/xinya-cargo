package com.cargo.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.order.dto.ConsignorReleaseDto;
import com.cargo.order.entity.ConsignorReleaseEntity;
import com.cargo.order.service.ConsignorReleaseService;
import com.cargo.order.vo.ConsignorReleaseVo;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.*;
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
 * @since 2020-10-26
 */
@Slf4j
@Api(tags ="货主发布信息操作")
@RestController
@RequestMapping("/api/order/consignorRelease")
public class ConsignorReleaseController extends SuperController {
    @Autowired
    private ConsignorReleaseService consignorReleaseService;


    @ApiOperation(value = "获取货主发布信息列表")
    @PostMapping("/list")
    public ResponseInfo<Page<ConsignorReleaseVo>> list(@RequestBody ConsignorReleaseDto dto) {
        Page<ConsignorReleaseVo> page = this.getPage(false);
        Page<ConsignorReleaseVo> consignorReleaseVoPage = null;
        if(dto.getType().equals("2")){
            dto.setCreateUser(ShiroUtil.getUserId());
        }
        consignorReleaseVoPage = consignorReleaseService.queryForList(dto,page);
        return ResponseUtil.success(consignorReleaseVoPage);
    }

    @ApiOperation(value = "新增货主发布信息")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody ConsignorReleaseDto dto) {
        log.info("新增货主发布信息 入参:{}",JSONObject.toJSONString(dto));
        return ResponseUtil.result(consignorReleaseService.add(dto));
    }

    @ApiOperation(value = "货主发布信息详情")
    @GetMapping("/get/{consignorReleaseId}")
    public ResponseInfo add(@PathVariable String consignorReleaseId) {
        return ResponseUtil.result(consignorReleaseService.queryForOne(consignorReleaseId));
    }

    @ApiOperation(value = "更新货主发布信息状态")
    @PostMapping("/editStatus")
    public ResponseInfo editStatus(@RequestBody ConsignorReleaseDto dto) {
        log.info("更新货主发布信息状态 入参:{}",JSONObject.toJSONString(dto));

        return ResponseUtil.result(consignorReleaseService.updateStatus(dto));
    }

    @ApiOperation(value = "更新货主发布信息内容")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody ConsignorReleaseDto dto) {
        log.info("更新货主发布信息内容 入参:{}",JSONObject.toJSONString(dto));
        return ResponseUtil.result(consignorReleaseService.updateConById(dto));
    }
    @ApiOperation(value = "根据出发地跟目的地获得预估公里数")
    @GetMapping("/getDistance")
    public ResponseInfo getDistance(@RequestParam("originsAddress") String originsAddress,@RequestParam("destinationAddress") String destinationAddress) {
        return ResponseUtil.success(GaodeApi.getDistance(originsAddress,destinationAddress));
    }

    @ApiOperation(value = "货主删除发布信息")
    @PostMapping("/delete")
    public ResponseInfo delete(@RequestBody List<String> consignorReleaseIds) {
        return ResponseUtil.result(consignorReleaseService.updateByListIds(consignorReleaseIds));
    }

}
