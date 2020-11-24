package com.cargo.feign.controller;


import com.cargo.location.dto.DriversLocationDto;
import com.cargo.location.dto.LocationDto;
import com.cargo.location.service.LocationService;
import com.cargo.location.vo.TrailVo;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * feignService 调用中心
 *
 * @Author Carlos
 */
@ApiIgnore
@RestController
@RequestMapping("/ms/service/gps")
public class FeignController {

    @Autowired
    private LocationService locationService;


    @PostMapping("/queryLocationList")
    @ApiOperation(value = "根据司机获取一个时间段内车辆轨迹")
    public TrailVo queryLocationList(@RequestBody DriversLocationDto dto) {
        TrailVo trailVo = locationService.queryLocationList(dto);
        return trailVo;
    }


}
