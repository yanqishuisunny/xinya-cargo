package com.cargo.car.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PageDto implements Serializable {

    @ApiModelProperty("条数")
    @NotNull(message = "limit不能为空")
    private int limit;

    @ApiModelProperty("页数")
    @NotNull(message = "页数不能为空")
    private int page;

}
