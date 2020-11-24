package com.cargo.order.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 基础数据审核日志表
 * </p>
 *
 * @author jobob
 * @since 2020-11-06
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("examine_log")
public class ExamineLogEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "car_energy_type_id", type = IdType.UUID)
    private String examineLogId;

    private String oldStatus;

    private String newStatus;

    /**
     * 数据ID
     */
    private String sourceId;

    /**
     * 数据类型
     */
    private Integer type;

    /**
     * 备注
     */
    private String checkRemark;

    private String createUser;
}
