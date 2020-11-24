package com.cargo.point.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 积分表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("point_account")
public class PointAccountEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

	private String orgId;
    /**
     * 主键
     */
	@ApiModelProperty("主键")
	@TableId(type = IdType.UUID)
	private String pointAccountId;
    /**
     * 积分类型（1、收入；2、支出）
     */
	private String pointType;
    /**
     * 分值
     */
	private String pointCount;
    /**
     * 积分来源
     */
	private String pointSource;
    /**
     * 当前积分
     */
	private String pointCountNewest;
	private String createUser;
	private String updateUser;
    /**
     * 备注
     */
	private String refuseRemark;
}
