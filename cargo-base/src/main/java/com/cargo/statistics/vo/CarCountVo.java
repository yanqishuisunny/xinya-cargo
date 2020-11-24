package com.cargo.statistics.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CarCountVo {

    @ApiModelProperty("全部车辆数量")
    private  Integer carAll;

    @ApiModelProperty("忙碌车辆的数量")
    private  Integer carBusy;
}
