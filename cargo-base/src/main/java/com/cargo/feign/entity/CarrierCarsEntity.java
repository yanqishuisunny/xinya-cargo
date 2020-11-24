package com.cargo.feign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("carrier_cars")
public class CarrierCarsEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "carrier_cars_id", type = IdType.UUID)
    private String carrierCarsId;

    /**
     * 承运商信息发布ID
     */
    private String carrierReleaseId;

    /**
     * 车辆ID
     */
    private String carId;

    private String createUser;

    private String updateUser;


}
