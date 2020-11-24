package com.cargo.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16 17:19:48
 */
@ApiModel("")
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
public class OrgInvoiceDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 开户行
     */
    @ApiModelProperty("开户行")
    private String bankName;


    /**
     * 银行账户
     */
    @ApiModelProperty("银行账户")
    private String bankNumber;


    /**
     *
     */
    @ApiModelProperty("")
    private String createUser;


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
     * 删除标记 0:已删除 1:未删除
     */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;


    /**
     * 是否默认（0、否；1、是）
     */
    @ApiModelProperty("是否默认（0、否；1、是）")
    private String isDefault;


    /**
     * 单位地址
     */
    @ApiModelProperty("单位地址")
    private String orgAddress;


    /**
     * 发票公司ID
     */
    @ApiModelProperty("发票公司ID")
    private String orgId;


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String orgInvoiceId;


    /**
     * 单位电话
     */
    @ApiModelProperty("单位电话")
    private String orgMobile;


    /**
     * 发票公司名称
     */
    @ApiModelProperty("发票公司名称")
    private String orgName;


    /**
     * 税号
     */
    @ApiModelProperty("税号")
    private String orgTaxNumber;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String refuseRemark;


    /**
     *
     */
    @ApiModelProperty("")
    private String updateUser;


    public interface Create {
    }

    public interface Update {
    }
}
