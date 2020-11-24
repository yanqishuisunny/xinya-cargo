package com.cargo.waybill.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 运单跟踪表
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_waybill_status_log")
public class WaybillStatusLogEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "waybill_tracking_id", type = IdType.UUID)
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
