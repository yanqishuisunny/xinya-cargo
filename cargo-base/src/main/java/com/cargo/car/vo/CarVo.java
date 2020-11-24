package com.cargo.car.vo;

import com.cargo.car.entity.CarEntity;
import lombok.Data;

@Data
public class CarVo extends CarEntity {
    /**
     * 车牌类型ID
     */
    private String carCardTypeName;

    /**
     * 能源类型ID
     */
    private String carEnergyTypeName;

    /**
     * 车辆类型id
     */
    private String carTypeName;

    /**
     * 车辆尺寸类型ID
     */
    private String carSizeName;
}
