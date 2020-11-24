package com.cargo.location.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeviceDistributionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 省份设备分布
     */
    @ApiModelProperty("省份设备分布")
    private List<ProDistributionVo> proList;

    /**
     * 城市设备分布
     */
    @ApiModelProperty("城市设备分布")
    private List<CyDistributionVo> cyList;

    /**
     * 状态设备分布
     */
    @ApiModelProperty("状态设备分布")
    private List<StatusVo> ststusList;

    /**
     * 设备数量
     */
    @ApiModelProperty("设备数量")
    private int deviceNum;

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
