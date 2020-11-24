package com.cargo.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

import java.lang.Double;
import java.util.Date;
import java.lang.String;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09 15:40:21
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AccountOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
    * 批次号
    */
    @ApiModelProperty("批次号")
    private String accountNo;

    
    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String accountOrderId;

    private String orgId;
    /**
     * 主键
     */
    @ApiModelProperty("批量锁帐")
    private List<String> accountOrderIds;

    
    /**
    * 账目状态（1、已锁；2、未锁；）
    */
    @ApiModelProperty("账目状态（1、已锁；2、未锁；）")
    private String accountStatus;

    @ApiModelProperty("包含账单数")
    private Integer billCount;
    /**
    * 合并总金额
    */
    @ApiModelProperty("合并总金额")
    private Double amountOfBills;

    
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