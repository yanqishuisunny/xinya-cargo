package com.cargo.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 司机一段时间内的注册数量
 */
@Data
public class DriverInCountVo implements Serializable {

    private  Integer count;

    private  String hour;

    private  String day;

    private  String month;

    private LocalDateTime gmtCreate;
}
