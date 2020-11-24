package com.cargo.location.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 告警推送dto
 * </p>
 *
 * @author Carlos
 * @since 2020-01-13
 */
@Data
public class DeviceAlarmDto implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 设备编码
     */

    @ApiModelProperty("设备编码")
    @NotBlank
    private String imei;

    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    private String deviceName;

    /**
     * 供应商名称
     */
    @ApiModelProperty("告警类型")
    @NotBlank
    private String alarmType;

    /**
     * 告警类型名称
     */
    @ApiModelProperty("告警类型名称")
    private String alarmName;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private String lng;

    /**
     * 告警时间
     */
    @ApiModelProperty("告警时间")
    private Date alarmTime;


}
