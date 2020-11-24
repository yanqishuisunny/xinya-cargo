package com.cargo.invoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Type;

/**
 * @Auther: xinzs
 * @Date: 2020/11/11 16:05
 */
@ApiModel("")
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class InvoiceAcceptOrUpload extends InvoiceDto{

    @ApiModelProperty("申请日期")
    private String type;
}
