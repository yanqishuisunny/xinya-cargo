package com.cargo.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单角色主表
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_menu_role_main")
public class MenuRoleMainEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "role_main_Id")
	private String roleMainId;
    /**
     * 集团ID编号
     */
	private String groupId;
    /**
     * 分公司ID
     */
	private String orgId;
    /**
     * 角色编号
     */
	private String roleCode;
    /**
     * 角色名称
     */
	private String roleName;
    /**
     * 角色说明
     */
	private String remark;
    /**
     * 状态 0:停用  1:启用
     */
	private Integer status;
    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

}
