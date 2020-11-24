package com.commom.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 信息返回实体封装
 * @author: Carlos
 * @Date: 2019/7/8 14:54
 */
@Data
public class ResponseInfo<T> implements Serializable {

    @ApiModelProperty("1000:成功")
    private int code;

    private String message;

    private T data;
    /**
     * 发生错误的解决方案或原因
     */
    @ApiModelProperty(value = "", hidden = true)
    private String resolvent;
}
