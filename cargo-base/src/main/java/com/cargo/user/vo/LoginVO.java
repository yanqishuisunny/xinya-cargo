package com.cargo.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Alex
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginVO implements Serializable {

    @ApiModelProperty("登录token")
    private String token;

    @ApiModelProperty("是否认证")
    private Boolean isAuthentication;

    @ApiModelProperty(hidden = true)
    private int number;

    @ApiModelProperty(hidden = true)
    private String msg;

    private String userId;

    @ApiModelProperty("工号/账号")
    private String userCode;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phoneNo;

    @ApiModelProperty("所属组织id")
    private String orgId;

    @ApiModelProperty("所属组织名称")
    private String orgName;


}
