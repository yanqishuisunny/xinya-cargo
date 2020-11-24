package com.cargo.feign.service.impl;


import com.cargo.complaint.vo.GeneralOrderDetailVo;
import com.cargo.feign.service.FeignOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FallOrderBackService implements FeignOrderService {


    @Override
    public List<GeneralOrderDetailVo> orderListbyOrderIds(List<String> t) {
        log.error("--------------------FeignClient--启动熔断:{}" , "converlist --> ConsignorReleaseVo");
        return null;
    }
}
