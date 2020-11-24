package com.cargo.payment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("order_payment")
public class OrderPaymentEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@ApiModelProperty("主键")
	@TableId(type = IdType.UUID)
	private String orderPaymentId;
    /**
     * 订单id
     */
	private String orderId;
    /**
     * 订单单号
     */
	private String orderNo;
    /**
     * 支付方式（1、支付宝）
     */
	private String paymentType;
    /**
     * 支付模式（1、APP端；2、PC端）
     */
	private String paymentSource;
    /**
     * 收款单位
     */
	private String receiveOrg;
    /**
     * 收款账户
     */
	private String receiveAccount;
    /**
     * 付款单位
     */
	private String paymentOrg;
    /**
     * 付款账户
     */
	private String paymentAccount;
    /**
     * 开票金额
     */
	private BigDecimal totalAmount;
    /**
     * 交易流水号
     */
	private String tradeNo;
    /**
     * 支付宝支付方式（1、二维码，2、APP）
     */
	private String paymentStyle;
    /**
     * 二维码连接
     */
	private String paymentUrl;
    /**
     * 是否支付成功（1、成功，2、失败）
     */
	private String paymentSuccess;
    /**
     * 备注
     */
	private String refuseRemark;

	private String createUser;
	private String updateUser;

}
