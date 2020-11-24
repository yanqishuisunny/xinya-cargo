package com.cargo.area.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 何立辉
 * @since 2019-07-25
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_area_new")
public class AreaEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "area_id", type = IdType.UUID)
    private String id;

    /**
     * 区域编码
     */
    private String code;
    private String gbtCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 名称缩写
     */
    private String nameAbbreviation;

    private String parentId;

    /**
     * 交通局区域代码
     */
    private String rtaId;

    /**
     * 等级
     */
    @TableField(value = "area_level")
    private Integer level;

    /**
     * 备注
     */
    private String memo;

    /**
     * 是否锁定
     */
    private String lockedFlag;

    /**
     * 显示序号
     */
    private Integer displayOrder;

    @TableField(value = "create_user")
    private String createUser;

    @TableField(value = "modify_user")
    private String modifyUser;

}
