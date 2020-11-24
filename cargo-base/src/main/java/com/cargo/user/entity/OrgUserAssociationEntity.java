package com.cargo.user.entity;

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
 * 企业用户关联表
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_org_user_association")
public class OrgUserAssociationEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "association_id", type = IdType.UUID)
    private String associationId;
    /**
     * 用户id
     */
	private String userId;
    /**
     * 企业id
     */
	private String orgId;
    /**
     * 权限id
     */
	private String roleId;
    /**
     * 权限编码
     */
	private String roleCode;
    /**
     * 企业角色 0:承运商 1:货主 4平台
     */
    private String orgRole;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 备注
     */
	private String remark;


}
