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
public class EditMenuBaseInfoDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    @ApiModelProperty("主键")
    private String baseInfoId;

    
    /**
    * 组件地址
    */
    @ApiModelProperty("组件地址")
    private String component;

    
    /*
    * 修改时间
    *
    @ApiModelProperty("修改时间")
    private Date gmtModified;*/

    
    /**
    * 集团ID编号
    */
    @ApiModelProperty("集团ID编号")
    private String groupId;

    
    /**
    * 删除标记 0:已删除 1:未删除
    */
    @ApiModelProperty("删除标记 0:已删除 1:未删除")
    private String isAble;

    
    /**
    * 代码
    */
    @ApiModelProperty("代码")
    private String menuCode;

    
    /**
    * 菜单层级 1：一级菜单，2：二级菜单
    */
    @ApiModelProperty("菜单层级 1 为一级公司 二级为2")
    private String menuFloor;

    
    /**
    * 名称
    */
    @ApiModelProperty("名称")
    private String menuName;

    
    /**
    * 菜单类型 0：总公司、1子公司菜单
    */
    @ApiModelProperty("菜单类型 0：总公司、1子公司菜单")
    private String menuType;

    
    /*
    * 系统更新时间
    *
    @ApiModelProperty("系统更新时间")
    private Date opTime;*/

    
    /**
    * 父级菜单 id
    */
    @ApiModelProperty("父级菜单 id")
    private String parentId;

    
    /**
    * 排序
    */
    @ApiModelProperty("排序")
    private Integer sort;

    
    /**
    * 状态 0:停用  1:启用
    */
    @ApiModelProperty("状态 0:停用  1:启用")
    private Integer status;

    
    /**
    * 链接
    */
    @ApiModelProperty("链接")
    private String url;

    /**
     * 是否隐藏
     */
    @ApiModelProperty("是否隐藏(1隐藏，0，不隐藏)")
    private Integer flgHidden;

    /**
     * 是否缓存
     */
    @ApiModelProperty("是否缓存(1是 0否)")
    private Integer flgCache;
    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String iconClass;
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