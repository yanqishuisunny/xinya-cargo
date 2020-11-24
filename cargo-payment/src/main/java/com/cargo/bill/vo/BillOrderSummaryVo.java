package com.cargo.bill.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: xinzs
 * @Date: 2020/11/20 15:30
 */

@ApiModel("收益分析")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BillOrderSummaryVo implements Serializable {

    @ApiModelProperty("每条路线运输次数")
    private Integer num;

    @ApiModelProperty("每条路线总价")
    private BigDecimal totalAmount;

    @ApiModelProperty("每个货主占比总价")
    private BigDecimal ownerTotalAmount;

    @ApiModelProperty("每条路线均价")
    private BigDecimal price;

    @ApiModelProperty("始发地")
    private String fromArea;

    @ApiModelProperty("目的地")
    private String toArea;

    @ApiModelProperty("货主")
    private String ownerOrgId;

    @ApiModelProperty("货主")
    private String ownerOrgName;

    @ApiModelProperty("订单待付总额")
    private BigDecimal amountToBePaids;

    @ApiModelProperty("订单已付总额")
    private BigDecimal amountPaids;

}
