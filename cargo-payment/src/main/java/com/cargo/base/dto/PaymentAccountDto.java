package com.cargo.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16 17:19:06
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PaymentAccountDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    @ApiModelProperty("公司ID")
    private String orgId;
    /**
    * 开户行
    */
    @ApiModelProperty("开户行")
    private String accountBank;

    
    /**
    * 开户地址
    */
    @ApiModelProperty("开户地址")
    private String accountBankAddress;

    
    /**
    * 账户编码
    */
    @ApiModelProperty("账户编码")
    private String accountCode;

    
    /**
    * 开户人联系方式
    */
    @ApiModelProperty("开户人联系方式")
    private String accountMobile;

    
    /**
    * 账户性质(1、对公；2、对私)
    */
    @ApiModelProperty("账户性质(1、对公；2、对私)")
    private String accountNature;

    
    /**
    * 账户状态（0、关闭；1、开启）
    */
    @ApiModelProperty("账户状态（0、关闭；1、开启）")
    private String accountStatus;

    
    /**
    * 账户类型（1、支付宝；2、微信；3、借记卡）
    */
    @ApiModelProperty("账户类型（1、支付宝；2、微信；3、借记卡）")
    private String accountType;

    
    /**
    * 开户人姓名
    */
    @ApiModelProperty("开户人姓名")
    private String accountUser;

    
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
    * 删除标记 0:已删除 1:未删除
    */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;

    
    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String paymentAccountId;

    
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