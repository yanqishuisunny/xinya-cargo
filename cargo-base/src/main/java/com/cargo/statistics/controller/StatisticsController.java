package com.cargo.statistics.controller;

import com.cargo.statistics.service.StatisticsService;
import com.cargo.statistics.vo.CarCountVo;
import com.cargo.statistics.vo.CountsVo;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "统计")
@RestController
@RequestMapping("/api/base/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @ApiOperation(value = "查看车辆的数量")
    @GetMapping("/carCount")
    public ResponseInfo<CarCountVo> carCount(){
         //查看车辆表车辆的数据
        return ResponseUtil.success();
    }


    @ApiOperation(value = "用户车辆注册时间数量图")
    @GetMapping("/counts")
    public ResponseInfo<CountsVo> counts(@RequestParam String type){
        CountsVo countsVo =  statisticsService.counts(type);
        return ResponseUtil.success(countsVo);
    }

}
