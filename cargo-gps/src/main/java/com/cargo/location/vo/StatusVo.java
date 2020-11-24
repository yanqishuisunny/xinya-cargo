package com.cargo.location.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StatusVo implements Serializable {

    /**
     * 设备状态(1:在线2:离线3:拆除)
     */
    @ApiModelProperty("设备状态(1:在线2:离线3:拆除)")
    private String status;

    /**
     * 状态对应数量
     */
    @ApiModelProperty("状态对应数量")
    private int statusNumber;

}
