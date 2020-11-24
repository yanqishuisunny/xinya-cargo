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
 * @Auther: xinzs
 * @Date: 2020/11/16 17:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("org_invoice")
public class OrgInvoiceEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.UUID)
    private String orgInvoiceId;
    /**
     * 税号
     */
    private String orgTaxNumber;
    /**
     * 发票公司ID
     */
    private String orgId;
    /**
     * 发票公司名称
     */
    private String orgName;
    /**
     * 单位地址
     */
    private String orgAddress;
    /**
     * 单位电话
     */
    private String orgMobile;
    /**
     * 开户行
     */
    private String bankName;
    /**
     * 银行账户
     */
    private String bankNumber;
    /**
     * 是否默认（0、否；1、是）
     */
    private String isDefault;
    private String createUser;
    private String updateUser;

    /**
     * 备注
     */
    private String refuseRemark;



}