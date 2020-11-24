package com.cargo.feign.service;

import com.cargo.dto.WaybillDto;
import com.cargo.entity.GeneralOrderEntity;
import com.cargo.entity.UserInfoEntity;
import com.cargo.feign.service.impl.FallOrderBackService;
import com.commom.utils.ResponseInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "xinya-order" ,fallback = FallOrderBackService.class)
public interface FeignOrderService {


    @GetMapping(value = "/userInfo/get/{id}")
    public UserInfoEntity getUserById(@PathVariable("id") String id);

    @PostMapping("/ms/order/service/addList")
    @ApiOperation(value = "新增运单--供调度使用")
    public ResponseInfo addList(@RequestBody List<WaybillDto> t);



    @GetMapping("/ms/order/service/orderDetail")
    @ApiOperation(value = "订单详情")
    public GeneralOrderEntity orderDetail(@RequestParam String t) ;

    @PostMapping("/ms/order/service/updateOrder")
    @ApiOperation(value = "调度修改订单状态")
    public void updateOrder(@RequestBody GeneralOrderEntity t);

}
