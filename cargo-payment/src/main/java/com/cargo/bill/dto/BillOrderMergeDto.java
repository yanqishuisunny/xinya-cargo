package com.cargo.bill.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xinzs
 * @Date: 2020/11/10 14:52
 */
@ApiModel("合并对账单")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillOrderMergeDto implements Serializable {

    @ApiModelProperty("账单id")
    private List<String> billOrderIds;
}
