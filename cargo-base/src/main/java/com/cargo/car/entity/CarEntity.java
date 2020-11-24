package com.cargo.car.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("car")
@ApiModel("车辆基础")
public class CarEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("车辆ID")
    @TableId(value = "car_id", type = IdType.UUID)
    private String carId;

    @ApiModelProperty("车牌号")
    private String carNo;

    @ApiModelProperty("车辆状态 0:空闲 1：运输中")
    private String carStatus;

    @ApiModelProperty("车牌类型ID")
    private String carCardTypeId;


    @ApiModelProperty("能源类型ID")
    private String carEnergyTypeId;


    @ApiModelProperty("车辆类型ID")
    private String carTypeId;


    @ApiModelProperty("车辆尺寸类型ID")
    private String carSizeId;


    @ApiModelProperty("载重")
    private BigDecimal carLoadWeight;


    @ApiModelProperty("行驶证车辆页")
    private String licenseHomeUrl;

    @ApiModelProperty("行驶证正页")
    private String licenseFrontUrl;

    @ApiModelProperty("行驶证副页")
    private String licenseBackUrl;

    @ApiModelProperty("道路运输许可证照片")
    private String roadTransportUrl;

    @ApiModelProperty("车辆照片正面URL")
    private String carFrontUrl;

    @ApiModelProperty("车辆照片背面URL")
    private String carBackUrl;

    @ApiModelProperty("0 - 待发布； 1-待审核； 2-通过， -2-拒绝")
    private String auditStatus;

    @ApiModelProperty("共享状态：  0:不共享    1：私有    2：共享")
    private String shareStatus;

    @ApiModelProperty("审核备注")
    private String checkRemark;

    private String createUser;

    private String updateUser;

}
