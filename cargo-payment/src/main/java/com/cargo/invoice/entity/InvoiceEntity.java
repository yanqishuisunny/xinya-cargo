package com.cargo.invoice.entity;

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
 * 税票表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("invoice")
public class InvoiceEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@ApiModelProperty("主键")
	@TableId(type = IdType.UUID)
	private String invoiceId;
    /**
     * 申请流水号
     */
	private String invoiceNo;
    /**
     * 发票号
     */
	private String invoiceNumber;
    /**
     * 开票金额
     */
	private BigDecimal invoiceAmount;
    /**
     * 开票性质：（1、普票；2、专票；3、增值票）
     */
	private String invoiceType;
    /**
     * 流水状态（1、申请中；2、撤回；3、开票中；4、完成) 
     */
	private String invoiceStatus;
    /**
     * 申请人
     */
	private String applyUserId;
    /**
     * 申请日期
     */
	private LocalDateTime applyDate;
    /**
     * 税点
     */
	private String taxPoint;
    /**
     * 是否含税（1、含；2、不含）
     */
	private String taxIncluded;
    /**
     * 种类（服务费、运输费……）
     */
	private String taxType;
    /**
     * 税票地址
     */
	private String invoiceUrl;
    /**
     * 税票下载地址
     */
	private String invoiceUrlDownload;
	@ApiModelProperty("发票公司ID")
	private String invoiceOrgId;

	@ApiModelProperty("发票公司名称")
	private String invoiceOrgName;

	@ApiModelProperty("税号")
	private String invoiceOrgTaxNumber;

	@ApiModelProperty("单位地址")
	private String invoiceOrgAddress;

	@ApiModelProperty("单位电话")
	private String invoiceOrgMobile;

	@ApiModelProperty("开户行")
	private String bankName;

	@ApiModelProperty("银行账户")
	private String bankNumber;
    /**
     * 备注
     */
	private String refuseRemark;
	private String createUser;
	private String updateUser;


}
