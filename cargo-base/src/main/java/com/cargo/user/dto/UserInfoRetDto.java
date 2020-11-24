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
 * 登录实体
 * </p>
 *
 * @author 何立辉
 * @since 2019-08-06 09:54:25
 */

@ApiModel
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserInfoRetDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账号")
    private String userCode;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String password;

    @ApiModelProperty("企业id")
    private String orgId;



}