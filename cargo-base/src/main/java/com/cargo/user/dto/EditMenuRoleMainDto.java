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
 * @since 2020-10-10 11:07:07
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class EditMenuRoleMainDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /*
    * 修改时间

    @ApiModelProperty("修改时间")
    private Date gmtModified;
*/
    
    /**
    * 集团ID编号
    */
    @ApiModelProperty("集团ID编号")
    private String groupId;

    
    /**
    * 分公司ID
    */
    @ApiModelProperty("分公司ID")
    private String orgId;

    
    /**
    * 角色说明
    */
    @ApiModelProperty("角色说明")
    private String remark;

    
    /**
    * 角色编号
    */
    @ApiModelProperty("角色编号")
    private String roleCode;

    
    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String roleMainId;

    
    /**
    * 角色名称
    */
    @ApiModelProperty("角色名称")
    private String roleName;

    
    /**
    * 状态 0:停用  1:启用
    */
    @ApiModelProperty("状态 0:停用  1:启用")
    private Integer status;

    /**
     * 角色IDs
     */
    @ApiModelProperty("角色IDs")
    private List<String> menuRoleMainIds;

}