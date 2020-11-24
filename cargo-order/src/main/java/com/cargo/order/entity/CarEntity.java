package com.cargo.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import com.commom.utils.MapperUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("car")
public class CarEntity extends BaseEntity implements Serializable {
    public static void main(String[] args) throws Exception {
        System.out.println(MapperUtils.getResultMapNew(new CarEntity().getClass()));
    }
    private static final long serialVersionUID = 1L;
    @TableId(value = "car_id", type = IdType.UUID)
    private String carId;

    private String carNo;

    private String carStatus;
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

}
