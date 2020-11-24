package com.cargo.feign.service;

import com.cargo.feign.dto.DriversLocationDto;
import com.cargo.feign.vo.TrailVo;
import com.commom.utils.ResponseInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
@FeignClient(value = "xinya-gps" ,fallback = GPSFallbackService.class)
public interface GPSFeignService {

    @PostMapping("/ms/service/gps/queryLocationList")
    @ApiOperation(value = "根据司机获取一个时间段内车辆轨迹")
    TrailVo queryLocationList(@RequestBody DriversLocationDto dto);

}
