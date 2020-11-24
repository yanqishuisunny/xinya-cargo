package com.cargo.complaint.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 投诉log表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_complaint_log")
public class ComplaintLogEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "complaint_log_id", type = IdType.UUID)
	private String complaintLogId;
    /**
     * 投诉id
     */
	private String complaintId;
    /**
     * 状态
     */
	private Integer complaintStatus;
    /**
     * 投诉文本
     */
	private String complaintTxt;

    /**
     * 创建人
     */
	private String createName;
    /**
     * 创建人
     */
	private String createId;

    /**
     * 备注
     */
	private String remark;


}
