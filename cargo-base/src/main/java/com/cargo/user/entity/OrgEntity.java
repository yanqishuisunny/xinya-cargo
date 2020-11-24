package com.cargo.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 组织表
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_org")
public class OrgEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织ID
     */
    @TableId(value = "org_id", type = IdType.UUID)
    private String orgId;
    /**
     * 父级ID
     */
	private String parentId;
    /**
     * 集团ID
     */
	private String groupId;
    /**
     * 组织名称
     */
	private String orgName;
    /**
     * (企业角色 0:承运商 1:货主 4平台)
     */
	private Integer orgRole;
    /**
     * 组织类型 0:公司 1:网点
     */
	private Integer orgType;
    /**
     * 超级管理员id
     */
    private String userId;
    /**
     * 企业总证件过期状态 1，已过期
     */
	private Integer overdueStatus;
    /**
     * 来源  0:注册 1:创建
     */
	private Integer orgSource;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 0:企业 1:个人
     */
	private Integer bizType;
    /**
     * 人工审核状态0待审核1拒绝2通过
     */
	private Integer auditStatus;
    /**
     * 接口审核状态0未审核1拒绝2通过
     */
	private Integer extAuditStatus;

    /**
     * 审核备注
     */
    private String checkRemark;
}
