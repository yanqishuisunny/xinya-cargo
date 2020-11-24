package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
public class MenuRoleDetailDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
    * 菜单id
    */
    @ApiModelProperty("菜单id")
    private String baseInfoId;

    
    /**
    * 集团ID编号
    */
    @ApiModelProperty("集团ID编号")
    private String groupId;

    
    /**
    * 菜单层级 1：一级菜单，2：二级菜单
    */
    @ApiModelProperty("菜单层级 1：一级菜单，2：二级菜单")
    private String menuFloor;

    
    /**
    * 菜单名称
    */
    @ApiModelProperty("菜单名称")
    private String menuName;

    
    /**
    * 分公司ID
    */
    @ApiModelProperty("分公司ID")
    private String orgId;

    
    /**
    * 父级菜单 id
    */
    @ApiModelProperty("父级菜单 id")
    private String parentId;

    
    /**
    * 角色说明
    */
    @ApiModelProperty("角色说明")
    private String remark;

    
    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String roleDetailId;

    
    /**
    * 菜单角色id
    */
    @ApiModelProperty("菜单角色id")
    private String roleMainId;

    
    /**
    * 状态 0:停用  1:启用
    */
    @ApiModelProperty("状态 0:停用  1:启用")
    private Integer status;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createUser;


    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateUser;
}