package com.cargo.alipay.model.dto;

import com.alipay.api.domain.MutipleCurrencyDetail;
import com.alipay.api.domain.Participant;
import com.alipay.api.internal.mapping.ApiField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xinzs
 * @Date: 2020/11/3 13:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransUniTransferDto implements Serializable {

    /** 场景码，单笔无密转账到支付宝账户固定为：DIRECT_TRANSFER  **/
    @ApiField("biz_scene")
    private String bizScene;
    @ApiField("business_params")
    private String businessParams;
    @ApiField("mutiple_currency_detail")
    private MutipleCurrencyDetail mutipleCurrencyDetail;
    /** 转账业务的标题，用于在支付宝用户的账单里显示 **/
    @ApiField("order_title")
    private String orderTitle;
    @ApiField("original_order_id")
    private String originalOrderId;
    /** 商户端的唯一订单号，对于同一笔转账请求，商户需保证该订单号唯一 **/
    @ApiField("out_biz_no")
    private String outBizNo;
    @ApiField("passback_params")
    private String passbackParams;
    @ApiField("payee_info")
    private Participant payeeInfo;
    @ApiField("payer_info")
    private Participant payerInfo;
    /** 产品码，单笔无密转账到支付宝账户固定为：TRANS_ACCOUNT_NO_PWD **/
    @ApiField("product_code")
    private String productCode;
    /** 业务备注  **/
    @ApiField("remark")
    private String remark;
    /** 转账金额，TRANS_ACCOUNT_NO_PWD产品取值最低0.1  **/
    @ApiField("trans_amount")
    private String transAmount;

    private Participant participant;

    /** 参与方的唯一标识,收款支付宝账号或者支付宝吧账号唯一会员ID **/
//        participant.setIdentity("2088******");

    /** 参与方的标识类型：ALIPAY_USER_ID 支付宝的会员ID  **/
//        participant.setIdentityType("ALIPAY_USER_ID");

    /** 参与方真实姓名，如果非空，将校验收款支付宝账号姓名一致性。当identity_type=ALIPAY_LOGON_ID时，本字段必填 **/
//        participant.setName("张三");

}
