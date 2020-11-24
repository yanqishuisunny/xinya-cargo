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
 * @since 2020-10-29 11:10:25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginOrgUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String associationId;
        /**
     * 企业id
     */
    @ApiModelProperty("企业id")
    private String orgId;
    /**
     * 企业名称
     */
    @ApiModelProperty("企业名称")
    private String orgName;
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

    /**
     * 登录账号
     */
    @ApiModelProperty("登录账号")
    private String userCode;
    

}