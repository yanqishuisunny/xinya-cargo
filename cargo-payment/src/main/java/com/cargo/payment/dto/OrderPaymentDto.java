package com.cargo.payment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10 14:34:27
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OrderPaymentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
    * 
    */
    @ApiModelProperty("")
    private String createUser;

    
    /**
    * 
    */
    @ApiModelProperty("")
    private Date gmtCreate;

    
    /**
    * 
    */
    @ApiModelProperty("")
    private Date gmtModified;

    
    /**
    * 删除标记 0:已删除 1:未删除
    */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;

    
    /**
    * 订单id
    */
    @ApiModelProperty("订单id")
    private String orderId;

    
    /**
    * 订单单号
    */
    @ApiModelProperty("订单单号")
    private String orderNo;

    
    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String orderPaymentId;

    
    /**
    * 付款账户
    */
    @ApiModelProperty("付款账户")
    private String paymentAccount;

    
    /**
    * 付款单位
    */
    @ApiModelProperty("付款单位")
    private String paymentOrg;

    
    /**
    * 支付模式（1、APP端；2、PC端）
    */
    @ApiModelProperty("支付模式（1、APP端；2、PC端）")
    private String paymentSource;

    
    /**
    * 支付宝支付方式（1、二维码，2、APP）
    */
    @ApiModelProperty("支付宝支付方式（1、二维码，2、APP）")
    private String paymentStyle;

    
    /**
    * 是否支付成功（1、成功，2、失败）
    */
    @ApiModelProperty("是否支付成功（1、成功，2、失败）")
    private String paymentSuccess;

    
    /**
    * 支付方式（1、支付宝）
    */
    @ApiModelProperty("支付方式（1、支付宝）")
    private String paymentType;

    
    /**
    * 二维码连接
    */
    @ApiModelProperty("二维码连接")
    private String paymentUrl;

    
    /**
    * 收款账户
    */
    @ApiModelProperty("收款账户")
    private String receiveAccount;

    
    /**
    * 收款单位
    */
    @ApiModelProperty("收款单位")
    private String receiveOrg;

    
    /**
    * 备注
    */
    @ApiModelProperty("备注")
    private String refuseRemark;

    
    /**
    * 开票金额
    */
    @ApiModelProperty("开票金额")
    private Double totalAmount;

    
    /**
    * 交易流水号
    */
    @ApiModelProperty("交易流水号")
    private String tradeNo;

    
    /**
    * 
    */
    @ApiModelProperty("")
    private String updateUser;

    
    public interface Create {
    }

    public interface Update {
    }
}