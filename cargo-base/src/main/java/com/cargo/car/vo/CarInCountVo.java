package com.cargo.car.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 查询一段时间新增车辆数量的vo
 */
@Data
public class CarInCountVo implements Serializable {

    private  Integer count;

    private  String hour;

    private  String day;

    private  String month;

    private LocalDateTime gmtCreate;

}
