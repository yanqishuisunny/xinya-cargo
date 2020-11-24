package com.cargo.app.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author GF
 * @Date 2019-8-15 15:43:10
 * @Description
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("AppRegisterDTO-司机组织")
public class AppEditDto extends Convert implements Serializable {

    @ApiModelProperty("账号")
    private String userCode;

    @ApiModelProperty(value = "密码" ,required = true)
    private String password;

}
