package com.cargo.feign.service.impl;


import com.cargo.dto.WaybillDto;
import com.cargo.entity.GeneralOrderEntity;
import com.cargo.entity.UserInfoEntity;
import com.cargo.entity.WaybillEntity;
import com.cargo.feign.service.FeignOrderService;
import com.cargo.feign.service.FeignService;
import com.commom.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Component
@Slf4j
public class FallOrderBackService implements FeignOrderService {


    @Override
    public UserInfoEntity getUserById(String id) {
        log.error("--------------------FeignClient--启动熔断:{}" , "converlist --> ConsignorReleaseVo");
        return null;
    }

    @Override
    public ResponseInfo addList(List<WaybillDto> t) {
        log.error("--------------------FeignClient--启动熔断:{}" , "converlist --> ConsignorReleaseVo");
        return null;
    }

    @Override
    public GeneralOrderEntity orderDetail(String t) {
        log.error("--------------------FeignClient--启动熔断:{}" , "converlist --> ConsignorReleaseVo");
        return null;
    }

    @Override
    public void updateOrder(GeneralOrderEntity t) {
        log.error("--------------------FeignClient--启动熔断:{}" , "converlist --> ConsignorReleaseVo");
    }

}
