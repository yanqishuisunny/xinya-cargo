package com.cargo.location.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProDistributionVo implements Serializable {

    /**
     * 省份
     */
    @ApiModelProperty("省份")
    private String province;

    /**
     * 省份对应设备数量
     */
    @ApiModelProperty("省份对应设备数量")
    private int proNumber;


    /**
     * 区域负责人
     */
    @ApiModelProperty("区域负责人")
    private String regionalHead;

    /**
     * 联系方式
     */
    @ApiModelProperty("联系方式")
    private String contactPhone;

}
