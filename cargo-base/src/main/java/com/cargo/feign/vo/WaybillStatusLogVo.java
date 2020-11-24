package com.cargo.feign.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class WaybillStatusLogVo implements Serializable {

    private String waybillTrackingId;

    /**
     * 运单id
     */
    private String waybillId;

    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 跟踪状态(0：新建，10：，20：，30：)
     */
    private Integer status;

    /**
     * 跟踪时间
     */
    private String trackingTime;

    /**
     * 跟踪备注
     */
    private String trackingMemo;

    /**
     * 当前位置
     */
    private String location;

    /**
     * 当前位置
     */
    private String locationDetails;

    /**
     * 创建人
     */
    private String createUserName;
}
