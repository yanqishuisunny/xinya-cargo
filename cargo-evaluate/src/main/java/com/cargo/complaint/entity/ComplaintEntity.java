package com.cargo.complaint.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 投诉表
 * </p>
 *
 * @author zhl
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_complaint")
public class ComplaintEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(type = IdType.UUID)
	private String complaintId;
	private String orgId;
	private String orgName;
    /**
     * 投诉单号
     */
	private String documentNumber;
    /**
     * 单号id
     */
	private String documentId;
    /**
     * 创建时间
     */
	private LocalDateTime conductorTime;
    /**
     * 处理人
     */
	private String conductor;
    /**
     * 处理人id
     */
	private String conductorId;
    /**
     * 状态（0，撤销；1新建，2处理 ，3结束 ）
     */
	private Integer complaintStatus;

    /**
     * 投诉人
     */
	private String createName;
    /**
     * 投诉人
     */
	private String createPhone;
    /**
     * 投诉id
     */
	private String createId;

     /* 备注
     */
	private String remark;
    /**
     * 投诉文本
     */
	private String complaintTxt;


}
