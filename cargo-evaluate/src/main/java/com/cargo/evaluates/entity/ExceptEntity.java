package com.cargo.evaluates.entity;

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
 * 异常单据表
 * </p>
 *
 * @author zhl
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_except")
public class ExceptEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 异常id
     */
	@TableId(value="except_id", type= IdType.UUID)
	private String exceptId;
    /**
     * 异常编号
     */
	private String exceptNo;
    /**
     * 关联的单据id（可能是作业单、运单、订单）
     */
	private String relatId;
    /**
     * 关联的单据编号（可能是作业单号、运单号、订单号）
     */
	private String relatNo;
    /**
     * 异常类型  0:货损，1:交通事故，2:装卸异常，3:其他
     */
	private Integer exceptType;
    /**
     * 事故地点
     */
	private String exceptPlace;
    /**
     * 经纬度
     */
	private String lonLat;
    /**
     * 异常状态  （暂定 0：新建，1：处理中，2：关闭 ）
     */
	private Integer exceptStatus;
    /**
     * 上报内容
     */
	private String exceptTxt;
    /**
     * 备注
     */
	private String memo;
    /**
     * 处理结果
     */
	private String processResult;
    /**
     * 处理人id
     */
	private String handleUserId;
    /**
     * 处理人姓名
     */
	private String handleUserName;
    /**
     * 处理时间
     */
	private LocalDateTime handleTime;
    /**
     * 处理结果
     */
	private String handleResult;

    /**
     * 创建人id
     */
	private String createUserId;
    /**
     * 创建人手机号
     */
	private String createUserPhone;
    /**
     * 创建人姓名
     */
	private String createUserName;
    /**
     * 创建人公司id
     */
	private String orgId;
    /**
     * 创建人公司
     */
	private String orgName;


}
