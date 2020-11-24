package com.cargo.feign.service;


import com.cargo.entity.UserInfoEntity;
import com.cargo.entity.WaybillEntity;
import com.commom.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FallbackService implements FeignService {


    @Override
    public UserInfoEntity findById(String id) {
        log.error("--------------------FeignClient--启动熔断:{}" , "converlist --> ConsignorReleaseVo");
        return null;
    }

    @Override
    public String carMessageToRedis(String orgId) {
        log.error("--------------------FeignClient--启动熔断:{}" , "converlist --> ConsignorReleaseVo");
        return null;
    }
}
