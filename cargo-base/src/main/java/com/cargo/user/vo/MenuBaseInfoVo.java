package com.cargo.user.vo;

import com.commom.entity.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10 11:07:07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MenuBaseInfoVo extends TreeNode implements Serializable {

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
        /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date gmtCreate;
        /**
     * 集团ID编号
     */
    @ApiModelProperty("集团ID编号")
    private String groupId;
        /**
     * 代码
     */
    @ApiModelProperty("代码")
    private String menuCode;
        /**
     * 菜单层级 1：一级菜单，2：二级菜单
     */
    @ApiModelProperty("菜单层级 1：一级菜单，2：二级菜单")
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

    /**
     * 1:货主 2:承运商 4:平台
     */
    @ApiModelProperty("企业角色 ( 0:承运商 1:货主 4平台)")
    private String versionType;

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
     * 选中状态
     */
    @ApiModelProperty("选中状态 1选中 0未选中")
    private Integer checked;
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