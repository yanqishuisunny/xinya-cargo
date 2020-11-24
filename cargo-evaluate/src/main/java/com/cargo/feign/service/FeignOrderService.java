package com.cargo.feign.service;

import com.cargo.complaint.entity.GeneralOrderEntity;
import com.cargo.complaint.vo.GeneralOrderDetailVo;
import com.cargo.feign.service.impl.FallOrderBackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(value = "xinya-order" ,fallback = FallOrderBackService.class)
public interface FeignOrderService {

    @PostMapping("/ms/order/service/orderListbyOrderIds")
    @ApiOperation(value = "订单详情")
    public List<GeneralOrderDetailVo> orderListbyOrderIds(@RequestBody List<String> t) ;

}
