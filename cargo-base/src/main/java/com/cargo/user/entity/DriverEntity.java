package com.cargo.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 司机合作关系表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_driver")
public class DriverEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

	private String id;
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
     * 合作司机id
     */
	private String driverId;
    /**
     * 合作司机名称
     */
	private String driverName;
    /**
     * 合作司机手机号
     */
	private String driverPhoneNo;
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
