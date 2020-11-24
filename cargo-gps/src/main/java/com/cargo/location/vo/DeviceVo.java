package com.cargo.location.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeviceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *设备IMEI
     */
    @ApiModelProperty("设备IMEI")
    private String imei;

    /**
     *设备主键
     */
    @ApiModelProperty("设备主键")
    private String deviceId;

    /**
     * 备注
     */
    @ApiModelProperty("所属承运商/车主")
    private String carrier;

    /**
     * 设备型号
     */
    @ApiModelProperty("设备型号")
    private String type;

    /**
     * sim流量到期时间
     */
    @ApiModelProperty("sim流量到期时间")
    private String simExpirationTime;


    /**
     * 供应商名称
     */
    @ApiModelProperty("供应商名称")
    private String supplierName;

    /**
     * 设备状态
     */
    @ApiModelProperty("设备状态(1:在线2:离线3:拆除)")
    private Integer status;

    /**
     *省
     */
    @ApiModelProperty("省")
    private String province;

    /**
     *市
     */
    @ApiModelProperty("市")
    private String city;

    /**
     * 绑定车牌号
     * @return
     */
    @ApiModelProperty("绑定车牌号")
    private String vehicleNo;

}
