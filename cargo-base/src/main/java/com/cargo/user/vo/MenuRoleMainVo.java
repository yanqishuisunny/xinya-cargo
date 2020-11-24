package com.cargo.user.vo;

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
public class MenuRoleMainVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date gmtCreate;
        /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date gmtModified;
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