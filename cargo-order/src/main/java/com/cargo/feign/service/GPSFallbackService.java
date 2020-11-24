package com.cargo.feign.service;

import com.cargo.feign.dto.DriversLocationDto;
import com.cargo.feign.vo.TrailVo;
import com.commom.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GPSFallbackService implements GPSFeignService{
    @Override
    public TrailVo queryLocationList(DriversLocationDto dto) {
        log.error("--------------------FeignClient--启动熔断:{}" , "dto --> TrailVo");
        return null;
    }
}
