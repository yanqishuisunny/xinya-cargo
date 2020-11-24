package com.cargo.examineLog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

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
    /**
     * type  1：车辆审核   2：司机审核   3:货主审核   4:企业审核  5:承运商审核
     * */
    public static final Integer TYPE_1 = 1;
    public static final Integer TYPE_2 = 2;
    public static final Integer TYPE_3 = 3;
    public static final Integer TYPE_4 = 4;
    public static final Integer TYPE_5 = 5;

    private static final long serialVersionUID = 1L;
    @TableId(value = "examine_log_id", type = IdType.UUID)
    private String examineLogId;

    private String oldStatus;

    private String newStatus;

    /**
     * 数据ID
     */
    private String sourceId;

    /**
     * 数据类型 1:车辆car
     */
    private Integer type;

    /**
     * 备注
     */
    private String checkRemark;

    private String createUser;
}
