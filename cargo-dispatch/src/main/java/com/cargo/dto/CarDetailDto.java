package com.cargo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CarDetailDto {
    @ApiModelProperty("车辆id")
    private String carId;

    @ApiModelProperty("车牌号")
    private String carNo;

    @ApiModelProperty("挂id ")
    private String hangId;


    @ApiModelProperty("挂车")
    private String hangNo;


    @ApiModelProperty("司机名称")
    private String driverName;


    @ApiModelProperty("司机id")
    private String driverId;

    @ApiModelProperty("司机手机号id")
    private String driverPhoneNo;

    @ApiModelProperty("预计到达时间")
    private String estimatedTime;

    @ApiModelProperty("预计发车时间")
    private String estimatTime;
//
//    @ApiModelProperty("实际发车时间")
//    private LocalDateTime actTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("运单号")
    private String waybillId;

    private String waybillNo;

    @ApiModelProperty("运单总重量")
    private BigDecimal totalWeight;

}
