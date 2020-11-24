package com.cargo.feign.service.impl;


import com.cargo.complaint.entity.UserInfoEntity;
import com.cargo.feign.service.FeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FallbackService implements FeignService {


    @Override
    public UserInfoEntity findById(String userId) {
        log.error("--------------------FeignClient--启动熔断:{}" , "converlist --> ConsignorReleaseVo");
        return null;
    }
}
