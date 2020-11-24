package com.cargo.location.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * <p>
 * 车辆设备关系表
 * </p>
 *
 * @author Carlos
 * @since 2020-01-13
 */
@Data
public class CarDeviceMappingDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @ApiModelProperty("id")
    private BigInteger id;

    /**
     * 车辆Id
     */
    @ApiModelProperty("车辆Id")
    private String vehicleId;

    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String vehicleNo;

    /**
     *  设备Id
     */
    @ApiModelProperty("设备Id")
    private BigInteger deviceId;


    /**
     * 设备IMEI
     */
    @ApiModelProperty("设备IMEI")
    private String imei;

    /**
     * 所属承运商
     */
    @ApiModelProperty("所属承运商")
    private String carrier;

    /**
     * 设备状态
     */
    @ApiModelProperty("设备状态(1:在线2:离线3:拆除)")
    private String status;


    /**
     * 解绑时间
     */
    @ApiModelProperty("解绑时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date unbindTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;


}
