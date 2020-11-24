package com.cargo.bill.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xinzs
 * @Date: 2020/11/20 16:23
 */
@ApiModel("收益分析——路线")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BillOrderAnalyseVo implements Serializable {

    @ApiModelProperty("已付/未付")
    private BillOrderSummaryVo payAmount;

    @ApiModelProperty("货主货款占比")
    private List<BillOrderSummaryVo> owner;

    @ApiModelProperty("路线车次")
    private List<BillOrderSummaryVo> way;
}
