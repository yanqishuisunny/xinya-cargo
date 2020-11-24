package com.commom.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * <p>
 * 登录Vo
 * </p>
 *
 * @author 何立辉
 * @since 2019-07-31 10:39:37
 */

@Getter
@Setter
@ToString
public class CurrentUser implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户表中的用户类型 (1-普通用户，2-企业管理员，3-超级管理员)
    public static final int USERTYPE_1 = 1;
    public static final int USERTYPE_2 = 2;
    public static final int USERTYPE_3 = 3;

    /**
     * id
     */
    @ApiModelProperty("用户id")
    private String userId;
    /**
     * 账号
     */
    @ApiModelProperty("工号/账号")
    private String userCode;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phoneNo;


    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private int status;

    /**
     * 用户表中的用户类型 (1-普通用户，2-企业管理员，3-超级管理员)
     */
    @ApiModelProperty("用户类型(1-普通用户，2-企业管理员，3-超级管理员)")
    private int userType;

    /**
     * 所属组织id
     */
    @ApiModelProperty("所属组织id")
    private String orgId;

    /**
     * 所属组织名称
     */
    @ApiModelProperty("所属组织名称")
    private String orgName;



    @ApiModelProperty("权限")
    private String menuRole;

    /**
     * 当前角色
     */
    @ApiModelProperty("当前权限角色")
    private String roleCode;


    @ApiModelProperty("当前权限角色id")
    private String roleId;


    @ApiModelProperty("当前登录账号公司的等级")
    private Integer orgType;

    /**
     * 企业审核状态
     */
    @ApiModelProperty("企业审核状态")
    private int auditStatus;

    @ApiModelProperty("当前企业对应超级管理员id")
    private String adminId;

    @ApiModelProperty("权限角色状态")
    private Integer menuStatus;



}
