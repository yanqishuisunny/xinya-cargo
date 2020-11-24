package com.cargo.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author GF
 * @Date 2019-8-10 15:34:47
 * @Description
 **/
@Data
@ApiModel("ForgetPasswordDTO-忘记密码")
public class ForgetPasswordDto {

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("验证码")
    private String smsCode;
}
