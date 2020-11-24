package com.cargo.bill.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xinzs
 * @Date: 2020/11/20 14:56
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AliPayBillOrderVo extends BillOrderVo implements Serializable {

    @ApiModelProperty(value = "收款二维码")
    private String qrCode;
    @ApiModelProperty(value = "收款方")
    private String collectionOrg;
    @ApiModelProperty(value = "收款账号")
    private String collectionCode;
}
