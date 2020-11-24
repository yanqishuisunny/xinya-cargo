package com.cargo.account.entity;

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
 * 帐目表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("account_order")
public class AccountOrderEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@ApiModelProperty("主键")
	@TableId(type = IdType.UUID)
	private String accountOrderId;
    /**
     * 批次号
     */
	private String accountNo;

	private String orgId;
    /**
     * 合并总金额
     */
	private BigDecimal amountOfBills;
	@ApiModelProperty("包含账单数")
	private Integer billCount;
    /**
     * 账目状态（1、已锁；2、未锁；）
     */
	private String accountStatus;
    /**
     * 备注
     */
	private String refuseRemark;
	private String createUser;
	private String updateUser;


}
