package com.cargo.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单角色明细表
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_menu_role_detail")
public class MenuRoleDetailEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "role_detail_id")
	private String roleDetailId;
    /**
     * 菜单角色id
     */
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
     * 父级菜单 id
     */
	private String parentId;
    /**
     * 菜单id
     */
	private String baseInfoId;
    /**
     * 菜单名称
     */
	private String menuName;
    /**
     * 菜单层级 1：一级菜单，2：二级菜单
     */
	private String menuFloor;
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
