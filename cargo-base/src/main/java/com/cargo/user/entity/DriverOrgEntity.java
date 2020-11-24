package com.cargo.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 组织邀请司机记录表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_driver_org")
public class DriverOrgEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 组织id
     */
	private String orgId;

	private String orgName;
    /**
     * 司机/用户id
     */
	private String userId;
    /**
     * 司机/用户名称
     */
	private String userName;

	/**
	 * 司机手机号
	 */
	private String phoneNo;

    /**
     * 1:意向发起人是司机,2：意向发起人是企业
     */
	private Integer type;
    /**
     * 服务开始时间
     */
	private LocalDateTime serveStartTime;
    /**
     * 服务结束时间
     */
	private LocalDateTime serveEndTime;
    /**
     * 状态（1：待接受，2：合作中，3：已解除，4：已拒绝）
     */
	private Integer status;
    /**
     * 是否查看（0未，1是）
     */
	private Integer isShow;
    /**
     * 是否在APP服务单位中被删除（0:否,1:是）
     */
	private Integer delFlag;


}
