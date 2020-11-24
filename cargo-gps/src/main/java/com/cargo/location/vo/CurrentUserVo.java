package com.cargo.location.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 登录Vo
 * </p>
 *
 * @author Carlos
 * @since 2020-2-25 10:39:37
 */

@Getter
@Setter
@ToString
public class CurrentUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("用户id")
    private String userId;
    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String userName;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String realName;

    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    private String idcardNo;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phoneNo;

    /**
     * 用户信息完成进度
     * 1:注册完成
     * 2：密码已设置
     * 3：已经选择角色
     */
    @ApiModelProperty("用户信息完成进度 1:注册完成 2：密码已设置 3：已经选择角色")
    private List<String> progress;


    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private int userStatus;

    /**
     * 企业审核状态
     */
    @ApiModelProperty("企业审核状态")
    private int auditStatus;

    /**
     * 账户类型 0:企业 1:个人
     */
    @ApiModelProperty("账户类型 0:企业 1:个人")
    private int bizType;

    /**
     * 企业角色 0:承运商 1:货主 2:两者都是
     */
    @ApiModelProperty("企业角色 0:承运商 1:货主 2:两者都是")
    private int orgRole;

    /**
     * 所属组织名称
     */
    @ApiModelProperty("所属组织名称")
    private String orgName;

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
     * 所属集团名称
     */
    @ApiModelProperty("所属集团名称")
    private String groupName;

    /**
     * 所属集团id
     */
    @ApiModelProperty("所属集团id")
    private String groupId;

    /**
     * 所属公司（单位）名称
     */
    @ApiModelProperty("所属公司（单位）名称")
    private String companyName;

    /**
     * 所属公司（单位）id
     */
    @ApiModelProperty("所属公司（单位）id")
    private String companyId;
    /**
     * 当前角色
     */
    @ApiModelProperty("当前角色")
    private String roleCode;

    @ApiModelProperty(" ")
    private String uuid;

    @ApiModelProperty("用户当前角色id")
    private String roleId;

}
