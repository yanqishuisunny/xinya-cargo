package com.cargo.statistics.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 组织表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_multiple")
public class MultipleEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
	private String id;
    /**
     * 1.车辆，2.司机
     */
	private Integer type;
    /**
     * 是否启用（0否，1 是）
     */
	private Integer isStart;
    /**
     * 放大倍数
     */
	private Integer multiple;
    /**
     * 增加的数量
     */
	private Integer count;



}
