package com.cargo.bill.entity;

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
 * 帐单表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bill_order")
public class BillOrderEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@ApiModelProperty("主键")
	@TableId(type = IdType.UUID)
	private String billOrderId;
    /**
     * 账目ID
     */
	private String accountOrderId;
    /**
     * 订单ID
     */
	private String orderId;
    /**
     * 订单号
     */
	private String orderNo;
    /**
     * 账单状态（1、待审核；2、待付款；3、已付款）
     */
	private String billStatus;
	/**
	 * 是否已合账（0、未合账；1、已合账）
	 */
	private String accountType;
    /**
     * 承运商ID
     */
	private String carrierId;
    /**
     * 承运商名所属组织ID
     */
	private String orgId;
    /**
     * 承运商名所属组织名称
     */
	private String orgName;
    /**
     * 起始地
     */
	private String fromArea;
    /**
     * 目的地
     */
	private String toArea;
    /**
     * 货物名称
     */
	private String goodsName;
    /**
     * 运输完成时间
     */
	private LocalDateTime transportFinishTime;
    /**
     * 待付金额
     */
	private BigDecimal amountToBePaid;
    /**
     * 已付金额
     */
	private BigDecimal amountPaid;
    /**
     * 计费方式（1：公里数  2：重量   3：总价）
     */
	private String priceTypeId;
    /**
     * 支付方式（1、支付宝）
     */
	private String paymentType;
    /**
     * 支付模式（1、APP端；2、PC端）
     */
	private String paymentSource;
    /**
     * 交易流水号
     */
	private String tradeNo;
    /**
     * 付款时间
     */
	private LocalDateTime paymentTime;
    /**
     * 审核人ID
     */
	private String examineUserId;
    /**
     * 审核时间
     */
	private LocalDateTime examineTime;
    /**
     * 是否异常（0、异常；1、正常）
     */
	private String isAbnormal;
	/**
	 * 货主ID
	 */
	private String ownerOrgId;
	/**
	 * 货主
	 */
	private String ownerOrgName;
	/**
	 * 货主联系方式
	 */
	private String ownerOrgPhone;
	/**
	 * 催款次数
	 */
	private Integer pressCount;
	/**
	 * 催款时间
	 */
	private String pressTime;
    /**
     * 备注
     */
	private String refuseRemark;
	private String createUser;
	private String updateUser;


}
