package com.cargo.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.order.dto.CarrierReleaseDto;
import com.cargo.order.entity.CarrierReleaseEntity;
import com.cargo.order.entity.GoodsEntity;
import com.cargo.order.service.CarrierReleaseService;
import com.cargo.order.vo.CarrierReleaseVo;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.MapperUtils;
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
 * @since 2020-11-03
 */
@Slf4j
@Api(tags ="承运商发布信息操作")
@RestController
@RequestMapping("/api/order/carrierRelease")
public class CarrierReleaseController extends SuperController {
    @Autowired
    private CarrierReleaseService carrierReleaseService;
    
    @ApiOperation(value = "获取承运商发布信息列表")
    @PostMapping("/list")
    public ResponseInfo<Page<CarrierReleaseVo>> list(@RequestBody CarrierReleaseDto dto) {
        Page<CarrierReleaseVo> page = this.getPage(false);
        Page<CarrierReleaseVo> carrierReleaseVoPage = null;
        if(dto.getType().equals("2")){
           dto.setCreateUser(ShiroUtil.getUserId());
        }
        carrierReleaseVoPage = carrierReleaseService.queryForList(dto, page);
        return ResponseUtil.success(carrierReleaseVoPage);
    }


    @ApiOperation(value = "新增承运商发布信息")
    @PostMapping("/add")
    public ResponseInfo add(@RequestBody CarrierReleaseDto dto) {
        log.info("新增货主发布信息 入参:{}", JSONObject.toJSONString(dto));
        return ResponseUtil.result(carrierReleaseService.add(dto));
    }

    @ApiOperation(value = "承运商发布信息详情")
    @GetMapping("/get/{carrierReleaseId}")
    public ResponseInfo add(@PathVariable("carrierReleaseId") String carrierReleaseId) {
        return ResponseUtil.result(carrierReleaseService.queryForOne(carrierReleaseId));
    }

    @ApiOperation(value = "更新承运商发布信息状态")
    @PostMapping("/editStatus")
    public ResponseInfo editStatus(@RequestBody CarrierReleaseDto dto) {
        log.info("更新货主发布信息 入参:{}", JSONObject.toJSONString(dto));
        CarrierReleaseEntity entity = new CarrierReleaseEntity();
        BeanUtils.copyProperties(dto,entity);
        UpdateWrapper<CarrierReleaseEntity> upw = new UpdateWrapper<>();
        upw.eq("carrier_release_id",entity.getCarrierReleaseId());
        upw.set("status",entity.getStatus());
        upw.set("check_remark",entity.getCheckRemark());
        return ResponseUtil.result(carrierReleaseService.update(upw));
    }
    @ApiOperation(value = "更新承运商发布信息内容")
    @PostMapping("/edit")
    public ResponseInfo edit(@RequestBody CarrierReleaseDto dto) {
        log.info("更新货主发布信息 入参:{}", JSONObject.toJSONString(dto));
        return ResponseUtil.result(carrierReleaseService.updateCaById(dto));
    }
    /*@ApiOperation(value = "根据出发地跟目的地获得预估公里数")
    @GetMapping("/getDistance")
    public ResponseInfo getDistance(@RequestParam("originsAddress") String originsAddress,@RequestParam("destinationAddress") String destinationAddress) {
        return ResponseUtil.success(GaodeApi.getDistance(originsAddress,destinationAddress));
    }*/

    @ApiOperation(value = "承运商删除发布信息")
    @PostMapping("/delete")
    public ResponseInfo delete(@RequestBody List<String> carrierReleaseIds) {
        return ResponseUtil.result(carrierReleaseService.updateByListIds(carrierReleaseIds));
    }
}
