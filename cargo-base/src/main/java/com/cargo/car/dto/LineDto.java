package com.cargo.car.dto;

import com.cargo.car.entity.LineEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LineDto {
    private String lineId;

    @ApiModelProperty("发货 省市县 6位编码")
    private String senderAdCode;


    @ApiModelProperty("收货 省市县 6位编码")
    private String deliveryAdCode;

}
