package com.cargo.car.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("car_size")
public class CarSizeEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "car_size_id", type = IdType.UUID)
    private String carSizeId;

    /**
     * 车辆尺寸名称
     */
    private String carSizeName;

    private String createUser;

    private String updateUser;


}
