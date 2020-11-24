package com.cargo.alipay.model.dto;

import com.alipay.api.internal.mapping.ApiField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xinzs
 * @Date: 2020/11/4 13:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransCommonQueryDto implements Serializable {

    @ApiField("biz_scene")
    private String bizScene;
    @ApiField("order_id")
    private String orderId;
    @ApiField("out_biz_no")
    private String outBizNo;
    @ApiField("pay_fund_order_id")
    private String payFundOrderId;
    @ApiField("product_code")
    private String productCode;

}
