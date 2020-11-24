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
public class DeviceTotalVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 供应商列表
     */
    @ApiModelProperty("供应商列表")
    private List<DeviceVo> list;

    /**
     * 总数量
     */
    @ApiModelProperty("总数量")
    private int totalNum;

}
