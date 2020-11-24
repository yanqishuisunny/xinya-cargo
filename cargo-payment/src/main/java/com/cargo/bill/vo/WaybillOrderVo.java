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
 * @since 2020-11-18 17:31:40
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WaybillOrderVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 已付司机金额
     */
    @ApiModelProperty("已付司机金额")
    private Double amountPaid;
        /**
     * 账单表ID
     */
    @ApiModelProperty("账单表ID")
    private String billOrderId;
        /**
     * 车辆id
     */
    @ApiModelProperty("车辆id")
    private String carId;
        /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String carNo;
        /**
     * 司机id
     */
    @ApiModelProperty("司机id")
    private String driverId;
        /**
     * 司机姓名
     */
    @ApiModelProperty("司机姓名")
    private String driverName;
        /**
     * 付款人
     */
    @ApiModelProperty("付款人")
    private String examineUser;
        /**
     * 付款人ID
     */
    @ApiModelProperty("付款人ID")
    private String examineUserId;
        /**
     * 
     */
    @ApiModelProperty("")
    private String gmtCreate;
        /**
     * 
     */
    @ApiModelProperty("")
    private String gmtModified;
        /**
     * 车挂id
     */
    @ApiModelProperty("车挂id")
    private String hangId;
        /**
     * 车挂号
     */
    @ApiModelProperty("车挂号")
    private String hangNo;
        /**
     * 删除标记 0:已删除 1:未删除
     */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;
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
     * 付款时间
     */
    @ApiModelProperty("付款时间")
    private String paymentTime;
    /**
     * 运单实际到达时间
     */
    @ApiModelProperty("运单实际到达时间")
    private String actualTime;
        /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String refuseRemark;
        /**
     * 运单id
     */
    @ApiModelProperty("运单id")
    private String waybillId;
        /**
     * 运单号
     */
    @ApiModelProperty("运单号")
    private String waybillNo;
        /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String waybillOrderId;
        /**
     * 运单核销状态（1、待付款；2、已付款）
     */
    @ApiModelProperty("运单核销状态（1、待付款；2、已付款）")
    private String waybillPayStatus;
    

}