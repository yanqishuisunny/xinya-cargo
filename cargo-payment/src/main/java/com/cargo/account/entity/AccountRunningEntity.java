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
 * 帐目流水表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("account_running")
public class AccountRunningEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@ApiModelProperty("主键")
	@TableId(type = IdType.UUID)
	private String accountRunningId;
    /**
     * 批次号/发票号
     */
	private String accountRunningNo;
    /**
     * 流水类型：（1、入；2、出）
     */
	private String accountRunningType;
    /**
     * 流水用途：（1、入账；2、出账；3、开票；4、撤回开票）
     */
	private String accountRunningPurpose;
    /**
     * 流水金额
     */
	private BigDecimal runningAmount;
    /**
     * 流水状态（1、申请中；2、撤回；3、开票中；4、完成（出账入账直接是完成）；）
     */
	private String runningStatus;
    /**
     * 可开票总金额
     */
	private BigDecimal invoiceAmount;
	private String orgId;
    /**
     * 备注
     */
	private String refuseRemark;
	private String createUser;
	private String updateUser;


}
