package com.cargo.statistics.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CountVo implements Serializable {

    @ApiModelProperty("时间或者小时")
    private  Integer day;

    private LocalDateTime hourTime;


    private LocalDate dayTime;

    private LocalDate mouthTime;

    @ApiModelProperty("数量")
    private  Integer count;

}
