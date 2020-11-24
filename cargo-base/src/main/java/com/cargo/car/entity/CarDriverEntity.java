package com.cargo.car.entity;

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
 * 车辆与司机关系表
 * </p>
 *
 * @author jobob
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("car_driver")
public class CarDriverEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "car_driver_id", type = IdType.UUID)
    private String carDriverId;

    /**
     * 用户ID
     */
    private String carId;

    /**
     * 司机ID
     */
    private String informationId;

    /**
     * 司机名字
     */
    private String idCardName;

    /**
     * 是否默认创建组织 0:否 1:是
     */
    private Integer isDefault;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;



}
