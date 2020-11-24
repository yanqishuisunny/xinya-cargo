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
 * @since 2020-11-09 15:43:07
 */
@ApiModel("")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountRunningVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String accountRunningId;
        /**
     * 批次号/发票号
     */
    @ApiModelProperty("批次号/发票号")
    private String accountRunningNo;
        /**
     * 流水用途：（1、入账；2、出账；3、开票；4、撤回开票）
     */
    @ApiModelProperty("流水用途：（1、入账；2、出账；3、开票；4、撤回开票）")
    private String accountRunningPurpose;
        /**
     * 流水类型：（1、入；2、出）
     */
    @ApiModelProperty("流水类型：（1、入；2、出）")
    private String accountRunningType;
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
     * 可开票总金额
     */
    @ApiModelProperty("可开票总金额")
    private Double invoiceAmount;
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
     * 流水金额
     */
    @ApiModelProperty("流水金额")
    private Double runningAmount;
        /**
     * 流水状态（1、申请中；2、撤回；3、开票中；4、完成（出账入账直接是完成）；）
     */
    @ApiModelProperty("流水状态（1、申请中；2、撤回；3、开票中；4、完成（出账入账直接是完成）；）")
    private String runningStatus;
        /**
     * 
     */
    @ApiModelProperty("")
    private String updateUser;
    

}