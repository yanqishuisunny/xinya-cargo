package com.cargo.feign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.order.entity.GeneralOrderEntity;
import com.cargo.order.service.GeneralOrderService;
import com.cargo.order.vo.GeneralOrderDetailVo;
import com.cargo.waybill.dto.WaybillDto;
import com.cargo.waybill.entity.WaybillEntity;
import com.cargo.waybill.entity.WaybillStatusLogEntity;
import com.cargo.waybill.service.WaybillService;
import com.cargo.waybill.service.WaybillStatusLogService;
import com.cargo.waybill.vo.WaybillVo;
import com.commom.core.BeanConverter;
import com.commom.utils.DateUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiIgnore
@RestController
@RequestMapping("/ms/order/service")
public class FeignController {

    @Autowired
    private WaybillService waybillService;

    @Autowired
    private WaybillStatusLogService waybillStatusLogService;

    @Autowired
    private GeneralOrderService generalOrderService;

    @PostMapping("/addList")
    @ApiOperation(value = "新增运单--供调度使用")
    @Transactional
    public ResponseInfo addList(@RequestBody List<WaybillDto> t) {
        if (ObjectUtils.isEmpty(t)) {
            return ResponseUtil.success();
        }
       List<WaybillEntity> save = new ArrayList<>();
       List<WaybillEntity> update = new ArrayList<>();
       List<String> stringList = new ArrayList<>();
        //创建运单日志
        WaybillStatusLogEntity waybillStatusLogEntity ;
        List<WaybillStatusLogEntity> list = new ArrayList<>();
        for (WaybillDto waybillEntity : t) {
            if(1== waybillEntity.getType()){
                save.add(BeanConverter.convert(WaybillEntity.class,waybillEntity));
            }else {
                update.add(BeanConverter.convert(WaybillEntity.class,waybillEntity));
                stringList.add(waybillEntity.getWaybillId());
            }
            waybillStatusLogEntity = new WaybillStatusLogEntity();
            waybillStatusLogEntity.setWaybillId(waybillEntity.getWaybillId());
            waybillStatusLogEntity.setWaybillNo(waybillEntity.getWaybillNo());
            waybillStatusLogEntity.setStatus(waybillEntity.getWaybillStatus());
            waybillStatusLogEntity.setTrackingTime(DateUtil.formatLocalDateTimeString(LocalDateTime.now()));
            list.add(waybillStatusLogEntity);
        }
        //删除需要修改的运单记录
        if (!ObjectUtils.isEmpty(stringList)) {
            QueryWrapper<WaybillStatusLogEntity> queryWrapper = new QueryWrapper();
            queryWrapper.in("waybill_id",stringList);
            waybillStatusLogService.remove(queryWrapper);
        }
        //保存信息
        if (!ObjectUtils.isEmpty(list)) {
            waybillStatusLogService.saveBatch(list);
        }
        //保存运单数据
        if (!ObjectUtils.isEmpty(save)) {
            waybillService.saveBatch(save);
        }
        //修改运单数据
        if (!ObjectUtils.isEmpty(update)) {
            waybillService.updateBatchById(update);
        }
        return ResponseUtil.success();
    }

    @GetMapping("/orderDetail")
    @ApiOperation(value = "订单详情")
    public GeneralOrderEntity orderDetail(@RequestParam String t) {
        if (ObjectUtils.isEmpty(t)) {
            return null;
        }
        GeneralOrderEntity byId = generalOrderService.getById(t);
        return byId;
    }

    @PostMapping("/updateOrder")
    @ApiOperation(value = "调度修改订单状态")
    public void updateOrder(@RequestBody GeneralOrderEntity t) {
        generalOrderService.updateById(t);
    }

    @PostMapping("/orderListbyOrderIds")
    @ApiOperation(value = "订单详情")
    public List<GeneralOrderDetailVo> orderListbyOrderIds(@RequestBody List<String> t){
        List<GeneralOrderDetailVo> list =    generalOrderService.selectDetails(t);
        return list;
    }

}
