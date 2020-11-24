package com.cargo.invoice.vo;

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
 * @since 2020-11-09 15:49:06
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InvoiceVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 申请日期
     */
    @ApiModelProperty("申请日期")
    private Date applyDate;
        /**
     * 申请人
     */
    @ApiModelProperty("申请人")
    private String applyUserId;
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
     * 开票金额
     */
    @ApiModelProperty("开票金额")
    private Double invoiceAmount;
        /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String invoiceId;
        /**
     * 申请流水号
     */
    @ApiModelProperty("申请流水号")
    private String invoiceNo;
        /**
     * 发票号
     */
    @ApiModelProperty("发票号")
    private String invoiceNumber;
        /**
     * 流水状态（1、申请中；2、撤回；3、开票中；4、完成) 
     */
    @ApiModelProperty("流水状态（1、申请中；2、撤回；3、开票中；4、完成) ")
    private String invoiceStatus;
        /**
     * 开票性质：（1、普票；2、专票；3、增值票）
     */
    @ApiModelProperty("开票性质：（1、普票；2、专票；3、增值票）")
    private String invoiceType;
        /**
     * 税票地址
     */
    @ApiModelProperty("税票地址")
    private String invoiceUrl;
        /**
     * 税票下载地址
     */
    @ApiModelProperty("税票下载地址")
    private String invoiceUrlDownload;
        /**
     * 删除标记 0:已删除 1:未删除
     */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;
        /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String refuseRemark;
        /**
     * 是否含税（1、含；2、不含）
     */
    @ApiModelProperty("是否含税（1、含；2、不含）")
    private String taxIncluded;
        /**
     * 税点
     */
    @ApiModelProperty("税点")
    private String taxPoint;
        /**
     * 种类（服务费、运输费……）
     */
    @ApiModelProperty("种类（服务费、运输费……）")
    private String taxType;
        /**
     * 
     */
    @ApiModelProperty("")
    private String updateUser;

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

}