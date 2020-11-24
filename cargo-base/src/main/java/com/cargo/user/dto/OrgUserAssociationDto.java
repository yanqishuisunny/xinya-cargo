package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
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
 * @since 2020-10-29 11:10:25
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class OrgUserAssociationDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String associationId;

    
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
    * 企业id
    */
    @ApiModelProperty("企业id")
    private String orgId;

    
    /**
    * 备注
    */
    @ApiModelProperty("备注")
    private String remark;

    
    /**
    * 权限编码
    */
    @ApiModelProperty("权限编码")
    private String roleCode;

    
    /**
    * 权限id
    */
    @ApiModelProperty("权限id")
    private String roleId;

    
    /**
    * 用户id
    */
    @ApiModelProperty("用户id")
    private String userId;

    
    public interface Create {
    }

    public interface Update {
    }
}