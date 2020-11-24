package com.cargo.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户详细情况表
 * </p>
 *
 * @author 开发者
 * @since 2020-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_user_info_detail")
public class UserDetailEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
	private String id;
    /**
     * 用户id
     */
	private String userId;
    /**
     * 是否实名认证
     */
	private Boolean flgPerfect;
    /**
     * 活体检测图
     */
	private String livingUrl;
    /**
     * 身份证正面照片
     */
	private String idcardUrl;
    /**
     * 身份证反面照片
     */
	private String idcardReUrl;
    /**
     * 身份证发证开始时间
     */
	private String idCardEndTime;
    /**
     * 身份证发证结束时间
     */
	private String idCardStartTime;
    /**
     * 个人总证件过期状态 1，已过期
     */
	private Integer overdueStatus;
    /**
     * 身份证发证机关
     */
	private String idCardAuthority;
    /**
     * 身份证号
     */
	private String idcardNo;
    /**
     * 身份证名称
     */
	private String idcardName;


}
