package com.cargo.location.controller;

import com.cargo.location.dto.DriversLocationDto;
import com.cargo.location.dto.LocationDto;
import com.cargo.location.model.MapLocation;
import com.cargo.location.service.LocationService;
import com.cargo.location.vo.TrailVo;
import com.commom.shiro.ShiroUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Title： LocationController </p>
 * <p>Description：定位数据接口 </p>
 * <p>Company：ail </p>
 *
 * @author Carlos
 * @version V1.0
 * @date 2020/1/13 14:31
 */

@Api(tags = "位置定位")
@RequestMapping("/api/gps/location")
@RestController
public class LocationController {


    @Autowired
    private LocationService locationService;


    @PostMapping("/addList")
    @ApiOperation(value = "同步司机定位")
    public ResponseInfo queryLast(@RequestBody List<MapLocation> result) {
        return ResponseUtil.success(locationService.addMongoData(result, ShiroUtil.getUserId()));
    }


    @PostMapping("/queryLocationList")
    @ApiOperation(value = "根据司机获取一个时间段内车辆轨迹")
    public ResponseInfo<TrailVo> queryLocationList(@RequestBody DriversLocationDto dto) {
        TrailVo trailVo = locationService.queryLocationList(dto);
        return ResponseUtil.success(trailVo);
    }


}
