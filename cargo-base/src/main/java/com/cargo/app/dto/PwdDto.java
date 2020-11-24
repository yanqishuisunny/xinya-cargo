package com.cargo.app.dto;

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
 * 修改密码
 * </p>
 *
 * @author 开发者
 * @since 2019-09-06 15:48:24
 */

@ApiModel
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PwdDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    
    /**
    * 登陆密码
    */
    @ApiModelProperty("登陆密码")
    private String password;

    
    /**
    * 短信验证码
    */
    @ApiModelProperty("短信验证码")
    private String smscode;



    /**
     * 类型!(LOGIN:登录,FORGET:忘记密码,REGISTER:注册验证码)
     */
    @ApiModelProperty("类型!(LOGIN:登录,FORGET:忘记密码,REGISTER:注册验证码)")
    private String type;

    
    /**
    * 手机号
    */
    @ApiModelProperty("手机号")
    private String phone;


}