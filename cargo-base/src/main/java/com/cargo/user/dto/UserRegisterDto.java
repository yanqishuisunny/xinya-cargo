package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业用户注册入参实体
 *
 * @author 何立辉 2019-10-16 19:34:41
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRegisterDto extends Convert {
    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
    private String userCode;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    private String phoneNo;

    /**
     * 验证码 验证通过令牌
     */
    @ApiModelProperty("验证令牌")
    private String token;

}
