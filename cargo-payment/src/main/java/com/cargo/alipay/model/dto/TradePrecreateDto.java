package com.cargo.alipay.model.dto;

import com.alipay.api.domain.*;
import com.alipay.api.internal.mapping.ApiField;
import com.alipay.api.internal.mapping.ApiListField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Auther: xinzs
 * @Date: 2020/10/30 15:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradePrecreateDto {

    @ApiField("alipay_store_id")
    private String alipayStoreId;
    @ApiField("body")
    private String body;
    @ApiField("business_params")
    private BusinessParams businessParams;
    @ApiField("buyer_logon_id")
    private String buyerLogonId;
    @ApiField("disable_pay_channels")
    private String disablePayChannels;
    @ApiField("discountable_amount")
    private String discountableAmount;
    @ApiField("enable_pay_channels")
    private String enablePayChannels;
    @ApiField("ext_user_info")
    private ExtUserInfo extUserInfo;
    @ApiField("extend_params")
    private ExtendParams extendParams;
    @ApiListField("goods_detail")
    @ApiField("goods_detail")
    private List<GoodsDetail> goodsDetail;
    @ApiField("merchant_order_no")
    private String merchantOrderNo;
    @ApiField("operator_id")
    private String operatorId;
    @ApiField("订单号")
    private String outTradeNo;
    @ApiField("product_code")
    private String productCode;
    @ApiField("qr_code_timeout_express")
    private String qrCodeTimeoutExpress;
    @ApiField("royalty_info")
    private RoyaltyInfo royaltyInfo;
    @ApiField("seller_id")
    private String sellerId;
    @ApiField("settle_info")
    private SettleInfo settleInfo;
    @ApiField("store_id")
    private String storeId;
    @ApiField("sub_merchant")
    private SubMerchant subMerchant;
    @ApiField("订单信息")
    private String subject;
    @ApiField("terminal_id")
    private String terminalId;
    @ApiField("timeout_express")
    private String timeoutExpress;
    @ApiField("订单金额")
    private String totalAmount;
    @ApiField("undiscountable_amount")
    private String undiscountableAmount;
}



