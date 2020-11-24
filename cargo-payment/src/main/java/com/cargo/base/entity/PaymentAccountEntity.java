package com.cargo.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 支付账户表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("payment_account")
public class PaymentAccountEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@ApiModelProperty("主键")
	@TableId(type = IdType.UUID)
	private String paymentAccountId;
    /**
     * 账户性质(1、对公；2、对私)
     */
	private String accountNature;
    /**
     * 账户类型（1、支付宝；2、微信；3、借记卡）
     */
	private String accountType;
    /**
     * 开户人姓名
     */
	private String accountUser;
    /**
     * 开户人联系方式
     */
	private String accountMobile;
    /**
     * 账户编码
     */
	private String accountCode;
    /**
     * 开户行
     */
	private String accountBank;
    /**
     * 开户地址
     */
	private String accountBankAddress;
    /**
     * 账户状态（0、关闭；1、开启）
     */
	private String accountStatus;
	private String createUser;
	private String updateUser;
    /**
     * 备注
     */
	private String refuseRemark;
	/**
	 * 发票公司ID
	 */

	private String orgId;

}
