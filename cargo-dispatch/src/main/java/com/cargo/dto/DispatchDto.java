package com.cargo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DispatchDto {

    @ApiModelProperty("订单id")
    private String generalOrderId;

    @ApiModelProperty("车辆列表")
    private List<CarDetailDto> waybillEntities;
}
