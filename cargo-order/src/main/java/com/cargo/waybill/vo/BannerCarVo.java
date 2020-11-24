package com.cargo.waybill.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BannerCarVo implements Serializable {

    @ApiModelProperty("车辆id")
    private  String  carId;

    @ApiModelProperty("车辆号")
    private  String  carNo;

    @ApiModelProperty("司机名称")
    private  String  driverName;

    @ApiModelProperty("司机手机号")
    private  String  driverPhoneNo;

    @ApiModelProperty("司机id")
    private  String  driverId;

    @ApiModelProperty("图中的信息")
    private List<WaybillVo>  waybillVos;
}
