package com.cargo.bill.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.lang.Double;
import java.util.Date;
import java.lang.String;


/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09 15:18:30
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BillOrderVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 账目ID
     */
    @ApiModelProperty("账目ID")
    private String accountOrderId;
        /**
     * 已付金额
     */
    @ApiModelProperty("已付金额")
    private Double amountPaid;
        /**
     * 待付金额
     */
    @ApiModelProperty("待付金额")
    private Double amountToBePaid;
        /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String billOrderId;
        /**
     * 账单状态（1、待审核；2、待付款；3、已付款）
     */
    @ApiModelProperty("账单状态（1、待审核；2、待付款；3、已付款;）")
    private String billStatus;
    /**
     * 是否已合账（0、未合账；1、已合账）
     */
    @ApiModelProperty("是否已合账（0、未合账；1、已合账）")
    private String accountType;
        /**
     * 承运商ID
     */
    @ApiModelProperty("承运商ID")
    private String carrierId;
        /**
     * 
     */
    @ApiModelProperty("")
    private String createUser;
        /**
     * 审核时间
     */
    @ApiModelProperty("审核时间")
    private Date examineTime;
        /**
     * 审核人ID
     */
    @ApiModelProperty("审核人ID")
    private String examineUserId;
        /**
     * 起始地
     */
    @ApiModelProperty("起始地")
    private String fromArea;
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
     * 货物名称
     */
    @ApiModelProperty("货物名称")
    private String goodsName;
        /**
     * 删除标记 0:已删除 1:未删除
     */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;
        /**
     * 是否异常（0、异常；1、正常）
     */
    @ApiModelProperty("是否异常（0、异常；1、正常）")
    private String isAbnormal;
        /**
     * 订单ID
     */
    @ApiModelProperty("订单ID")
    private String orderId;
        /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private String orderNo;
        /**
     * 承运商名所属组织ID
     */
    @ApiModelProperty("承运商名所属组织ID")
    private String orgId;
        /**
     * 承运商名所属组织名称
     */
    @ApiModelProperty("承运商名所属组织名称")
    private String orgName;
        /**
     * 支付模式（1、APP端；2、PC端）
     */
    @ApiModelProperty("支付模式（1、APP端；2、PC端）")
    private String paymentSource;
        /**
     * 付款时间
     */
    @ApiModelProperty("付款时间")
    private Date paymentTime;
        /**
     * 支付方式（1、支付宝）
     */
    @ApiModelProperty("支付方式（1、支付宝）")
    private String paymentType;
        /**
     * 计费方式（1：公里数  2：重量   3：总价）
     */
    @ApiModelProperty("计费方式（1：公里数  2：重量   3：总价）")
    private String priceTypeId;
        /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String refuseRemark;
        /**
     * 目的地
     */
    @ApiModelProperty("目的地")
    private String toArea;
        /**
     * 交易流水号
     */
    @ApiModelProperty("交易流水号")
    private String tradeNo;
        /**
     * 运输完成时间
     */
    @ApiModelProperty("运输完成时间")
    private Date transportFinishTime;
    /**
     * 货主ID
     */
    @ApiModelProperty("货主ID")
    private String ownerOrgId;
    /**
     * 货主
     */
    @ApiModelProperty("货主")
    private String ownerOrgName;
    /**
     * 货主联系方式
     */
    @ApiModelProperty("货主联系方式")
    private String ownerOrgPhone;
    /**
     * 催款次数
     */
    @ApiModelProperty("催款次数")
    private Integer pressCount;
    /**
     * 催款时间
     */
    @ApiModelProperty("催款时间")
    private String pressTime;
        /**
     * 
     */
    @ApiModelProperty("")
    private String updateUser;
    

}