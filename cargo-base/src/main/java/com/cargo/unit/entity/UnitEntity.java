package com.cargo.unit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("unit")
public class UnitEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "unit_id", type = IdType.UUID)
    private String unitId;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 单位类型（0：无归属   1:长度   2：重量    3：体积）
     */
    private Integer unitType;

    private String createUser;

    private String updateUser;



}
