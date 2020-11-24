package com.cargo.feign.service;


import com.cargo.feign.entity.AreCodeEntry;
import com.cargo.feign.entity.OrgEntity;
import com.cargo.order.vo.*;
import com.commom.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BaseFallbackService implements BaseFeignService {


    @Override
    public List<ConsignorReleaseVo> converlist(List<ConsignorReleaseVo> list) {
        log.error("--------------------FeignClient--启动熔断:{}" , "converlist --> ConsignorReleaseVo");
        return null;
    }

    @Override
    public List<CarrierReleaseVo> converCarrierlist(List<CarrierReleaseVo> list) {
        return null;
    }

    @Override
    public AreCodeEntry findByAdCode(String adCode) {
        log.error("--------------------FeignClient--启动熔断:{}" , "AreCodeEntry");
        return null;
    }

    @Override
    public List<IntentionOrderVo> converIntentionOrder(List<IntentionOrderVo> list) {
        log.error("--------------------FeignClient--启动熔断:{}" , "IntentionOrderVo");
        return null;
    }
    @Override
    public List<GeneralOrderVo> converOrder(List<GeneralOrderVo> list) {
        log.error("--------------------FeignClient--启动熔断:{}" , "OrderVo");
        return null;
    }

    @Override
    public CarVo getCar(String carId) {
        log.error("--------------------FeignClient--启动熔断:{}" , "CarVo");
        return null;
    }

    @Override
    public OrgEntity getOrgById(String orgId) {
        return null;
    }
}