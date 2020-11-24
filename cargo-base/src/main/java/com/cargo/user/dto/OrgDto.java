package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29 11:10:25
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OrgDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    
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
    * (企业角色 0:承运商 1:货主 4平台)
    */
    @ApiModelProperty("(企业角色 0:承运商 1:货主 4平台)")
    private Integer orgRole;

    
    /**
    * 来源  0:注册 1:创建
    */
    @ApiModelProperty("来源  0:注册 1:创建")
    private Integer orgSource;


    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phoneNo;

    
    /**
    * 组织类型 0:公司 1:网点
    */
    @ApiModelProperty("组织类型 0:公司 1:网点")
    private Integer orgType;

    /**
     * 审核状态  1:待审核  2:审核拒绝  3:审核通过
     */
    @ApiModelProperty("审核状态  1:待审核  2:审核拒绝  3:审核通过")
    private Integer auditStatus;
    /**
     * 搜索状态条件
     */
    private List<Integer> listAuditStatus;
    /**
     * 审核备注
     */
    private String checkRemark;
}