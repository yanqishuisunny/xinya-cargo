package com.cargo.feign.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("car_type")
public class CarTypeEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String carTypeId;

    /**
     * 车辆类型
     */
    private String carTypeName;

    @TableField("parentId")
    private String parentId;


    private String createUser;

    private String updateUser;

}
