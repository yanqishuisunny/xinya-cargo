package com.cargo.user.entity;

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
 * 菜单表
 * </p>
 *
 * @author 开发者
 * @since 2020-10-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_menu_base_info")
public class MenuBaseInfoEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "base_info_id")
	private String baseInfoId;
    /**
     * 集团ID编号
     */
	private String groupId;
    /**
     * 父级菜单 id
     */
	private String parentId;
    /**
     * 名称
     */
	private String menuName;
    /**
     * 代码
     */
	private String menuCode;
    /**
     * 链接
     */
	private String url;
    /**
     * 组件地址
     */
	private String component;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 菜单层级 1：一级菜单，2：二级菜单
     */
	private String menuFloor;
    /**
     * 菜单类型 0：总公司、1子公司菜单
     */
	private String menuType;


    /**
     * 企业角色 ( 0:承运商 1:货主 4平台)
     */
    private String versionType;

    /**
     * 状态 0:停用  1:启用
     */
	private Integer status;
    /**
     * 系统更新时间
     */
	private LocalDateTime opTime;

    /**
     * 是否隐藏
     */
    private Integer flgHidden;

    /**
     * 是否缓存
     */
    private Integer flgCache;

    /**
     * 图标
     */
    private String iconClass;
    /**
     * 创建人
     */
    private String createUser;


    /**
     * 更新人
     */
    private String updateUser;

}
