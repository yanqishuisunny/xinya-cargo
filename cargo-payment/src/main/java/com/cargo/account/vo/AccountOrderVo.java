package com.cargo.account.vo;

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
 * @since 2020-11-09 15:40:21
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountOrderVo implements Serializable {

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

    @ApiModelProperty("包含账单数")
    private Integer billCount;
        /**
     * 账目状态（1、已锁；2、未锁；）
     */
    @ApiModelProperty("账目状态（1、已锁；2、未锁；）")
    private String accountStatus;
        /**
     * 合并总金额
     */
    @ApiModelProperty("合并总金额")
    private Double amountOfBills;
    private String orgId;
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
    

}