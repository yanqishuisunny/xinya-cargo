package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10 11:07:07
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserInfoDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
    * 别名
    */
    @ApiModelProperty("别名")
    private String aliasName;

    
    /**
    * 数据权限
    */
    @ApiModelProperty("数据权限")
    private String dataRole;

    
    /**
    * 部门
    */
    @ApiModelProperty("部门")
    private String departId;


    /**
     *人员角色(1:货主 2:承运商 3:车主 4:平台 5:其他)
     */
    @ApiModelProperty("人员角色(1:货主 2:承运商 3:车主 4:平台 5:其他)")
    private String userRole;

    
    /**
    * 集团ID编号
    */
    @ApiModelProperty("集团ID编号")
    private String groupId;

    
    /**
    * 菜单权限
    */
    @ApiModelProperty("菜单权限")
    private String menuRole;

    
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
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createUser;


    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateUser;

    /**
     * 用户ID列表
     */
    @ApiModelProperty("用户ID列表")
    private List<String> userIds;

}