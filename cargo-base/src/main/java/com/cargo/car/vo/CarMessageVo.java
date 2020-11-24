package com.cargo.car.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CarMessageVo  implements Serializable {

    private String carId;

    private String carNo;

    /**
     * 车牌类型ID
     */
    private String carCardTypeId;

    /**
     * 能源类型ID
     */
    private String carEnergyTypeId;

    /**
     * 车辆类型id
     */
    private String carTypeId;

    /**
     * 车辆尺寸类型ID
     */
    private String carSizeId;

    /**
     * 载重
     */
    private BigDecimal carLoadWeight;

    /**
     * 行驶证图片
     */
    private String licenseHomeUrl;
    /**
     * 行驶证图片
     */
    private String licenseFrontUrl;
    /**
     * 行驶证图片
     */
    private String licenseBackUrl;
    /**
     * 道路运输许可证照片
     */
    private String roadTransportUrl;
    /**
     * 车辆照片
     */
    private String carFrontUrl;
    /**
     * 车辆照片
     */
    private String carBackUrl;

    private String createUser;

    private String updateUser;

    private LocalDateTime gmtCreate;

    private String carCardTypeName;

    private String carEnergyTypeName;

    private String carSizeName;

    private String carTypeName;
}
