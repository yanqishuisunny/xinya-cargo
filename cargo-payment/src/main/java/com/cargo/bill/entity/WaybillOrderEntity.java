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

/**
 * <p>
 * 帐单扩展_运单(司机对账)表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("waybill_order")
public class WaybillOrderEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.UUID)
	private String waybillOrderId;
    /**
     * 账单表ID
     */
	private String billOrderId;
    /**
     * 订单ID
     */
	private String orderId;
    /**
     * 订单号
     */
	private String orderNo;
    /**
     * 运单id
     */
	private String waybillId;
    /**
     * 运单号
     */
	private String waybillNo;
    /**
     * 车辆id
     */
	private String carId;
    /**
     * 车牌号
     */
	private String carNo;
    /**
     * 车挂id
     */
	private String hangId;
    /**
     * 车挂号
     */
	private String hangNo;
    /**
     * 司机id
     */
	private String driverId;
    /**
     * 司机姓名
     */
	private String driverName;
    /**
     * 运单核销状态（1、待付款；2、已付款）
     */
	private String waybillPayStatus;
    /**
     * 已付司机金额
     */
	private BigDecimal amountPaid;
    /**
     * 运单实际到达时间
     */
	private String actualTime;
    /**
     * 付款时间
     */
	private String paymentTime;
    /**
     * 付款人ID
     */
	private String examineUserId;
    /**
     * 付款人
     */
	private String examineUser;

    /**
     * 备注
     */
	private String refuseRemark;

}
