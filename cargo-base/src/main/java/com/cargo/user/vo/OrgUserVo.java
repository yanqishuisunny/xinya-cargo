package com.cargo.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.Date;
import java.lang.String;
import java.lang.Integer;


/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29 11:10:25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrgUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 人工审核状态0待审核1拒绝2通过
     */
    @ApiModelProperty("人工审核状态0待审核1拒绝2通过")
    private Integer auditStatus;
        /**
     * 0:企业 1:个人
     */
    @ApiModelProperty("0:企业 1:个人")
    private Integer bizType;
        /**
     * 接口审核状态0未审核1拒绝2通过
     */
    @ApiModelProperty("接口审核状态0未审核1拒绝2通过")
    private Integer extAuditStatus;
        /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date gmtCreate;
        /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date gmtModified;
        /**
     * 集团ID
     */
    @ApiModelProperty("集团ID")
    private String groupId;
        /**
     * 删除标记 0:已删除 1:未删除
     */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;
        /**
     * 组织ID
     */
    @ApiModelProperty("组织ID")
    private String orgId;
        /**
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    private String orgName;
        /**
     * 企业角色 0:承运商 1:货主 3车主
     */
    @ApiModelProperty("企业角色 0:承运商 1:货主 3车主")
    private Integer orgRole;
        /**
     * 来源  0:注册 1:创建
     */
    @ApiModelProperty("来源  0:注册 1:创建")
    private Integer orgSource;
        /**
     * 组织类型 0:公司 1:网点
     */
    @ApiModelProperty("组织类型 0:公司 1:网点")
    private Integer orgType;
        /**
     * 企业总证件过期状态 1，已过期
     */
    @ApiModelProperty("企业总证件过期状态 1，已过期")
    private Integer overdueStatus;
        /**
     * 父级ID
     */
    @ApiModelProperty("父级ID")
    private String parentId;
    

}