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
 * @Date: 2020/11/3 9:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeAppPayDto {
    @ApiField("agreement_sign_params")
    private SignParams agreementSignParams;
    @ApiField("body")
    private String body;
    @ApiField("business_params")
    private String businessParams;
    @ApiField("disable_pay_channels")
    private String disablePayChannels;
    @ApiField("enable_pay_channels")
    private String enablePayChannels;
    @ApiField("ext_user_info")
    private ExtUserInfo extUserInfo;
    @ApiField("extend_params")
    private ExtendParams extendParams;
    @ApiListField("goods_detail")
    @ApiField("goods_detail")
    private List<GoodsDetail> goodsDetail;
    @ApiField("goods_type")
    private String goodsType;
    @ApiField("invoice_info")
    private InvoiceInfo invoiceInfo;
    @ApiField("merchant_order_no")
    private String merchantOrderNo;
    @ApiField("out_trade_no")
    private String outTradeNo;
    @ApiField("passback_params")
    private String passbackParams;
    @ApiField("product_code")
    private String productCode;
    @ApiField("promo_params")
    private String promoParams;
    @ApiField("royalty_info")
    private RoyaltyInfo royaltyInfo;
    @ApiField("seller_id")
    private String sellerId;
    @ApiField("settle_info")
    private SettleInfo settleInfo;
    @ApiField("specified_channel")
    private String specifiedChannel;
    @ApiField("store_id")
    private String storeId;
    @ApiField("sub_merchant")
    private SubMerchant subMerchant;
    @ApiField("subject")
    private String subject;
    @ApiField("time_expire")
    private String timeExpire;
    @ApiField("timeout_express")
    private String timeoutExpress;
    @ApiField("total_amount")
    private String totalAmount;
}
