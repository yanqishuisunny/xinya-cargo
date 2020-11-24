package com.cargo.location.vo;

import lombok.Data;

@Data
public class GisLocationVo {
    /**
     * 消息id
     */
    private Long id;
    /**
     * 定位时间
     */
    private String locationTime;
    /**
     * 车牌号
     */
    private String vehicleNo;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 经度
     */
    private String lng;
    /**
     * 速度 (单位:km/h)
     */
    private String speed;

}

