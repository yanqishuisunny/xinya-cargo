package com.cargo.location.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class UserInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 别名
     */
    @ApiModelProperty("别名")
    private String aliasName;
        /**
     * 数据权限id
     */
    @ApiModelProperty("数据权限id")
    private String dataRole;
    /**
     * 数据权限名称
     */
    @ApiModelProperty("数据权限名称")
    private String dataRoleName;
    /**
     * 数据权限Code
     */
    @ApiModelProperty("数据权限Code")
    private String dataRoleCode;
        /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private String departId;
    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String departName;
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
     * 菜单权限ID
     */
    @ApiModelProperty("菜单权限ID")
    private String menuRole;
    /**
     * 菜单权限名称
     */
    @ApiModelProperty("菜单权限名称")
    private String menuRoleName;
    /**
     * 菜单权限Code
     */
    @ApiModelProperty("菜单权限Code")
    private String menuRoleCode;
        /**
     * 分公司ID
     */
    @ApiModelProperty("分公司ID")
    private String orgId;
        /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;
        /**
     * 角色说明
     */
    @ApiModelProperty("角色说明")
    private String remark;
        /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String userId;
        /**
     * 状态 0:停用  1:启用
     */
    @ApiModelProperty("状态 0:停用  1:启用")
    private Integer status;
        /**
     * 工号
     */
    @ApiModelProperty("工号")
    private String userCode;
        /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phoneNo;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;


    /**
     * 用户类型（1普通2企管3超管）
     */
    @ApiModelProperty("用户类型（1普通2企管3超管）")
    private String userType;

    /**
     * 最近登陆时间
     */
    @ApiModelProperty(value = "最近登陆时间")
    private LocalDateTime lastLoginTime;

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