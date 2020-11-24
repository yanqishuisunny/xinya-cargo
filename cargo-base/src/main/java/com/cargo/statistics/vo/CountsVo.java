package com.cargo.statistics.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CountsVo implements Serializable {
    @ApiModelProperty("车辆")
    private List<CountVo> carCount;

    @ApiModelProperty("用户")
    private  List<CountVo> userCount;


}
