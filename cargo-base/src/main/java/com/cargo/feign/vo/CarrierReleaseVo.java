package com.cargo.feign.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cargo.car.vo.CarVo;
import com.cargo.feign.entity.CarrierReleaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarrierReleaseVo  {
    private List<CarrierCarsVo> carrierCarsVoList;
    private List<CarVo> listCars;

    /**
     *回显出发地
     * */
    private String carrierSenderAdCode;
    /**
     *回显目的地
     * */
    private String carrierDeliveryAdCode;
    /**
     * 计费方式名称
     */
    private String priceTypeNanme;

    @TableId(value = "carrier_release_id", type = IdType.UUID)
    private String carrierReleaseId;

    @ApiModelProperty("承运商信息发布状态 状态( 0：创建  1:待审核（已提交）   2：审核通过   3：过期失效，-2 审核不过，-1：撤销)")
    private String status;

    @ApiModelProperty("有效天数")
    private Integer expiryDays;

    @ApiModelProperty("发布日期")
    private String expiryDaysString;

    @ApiModelProperty("有效期日期(发布日期加上有效天数算出)")
    private String validityString;

    @ApiModelProperty("时效天数")
    private Integer prescription;

    @ApiModelProperty("出发地-区域ID 省ID")
    private String lineSenderAreaProvinceId;

    private String lineSenderAreaProvinceName;

    @ApiModelProperty("出发地-区域ID 市ID")
    private String lineSenderAreaCityId;

    private String lineSenderAreaCityName;
    @ApiModelProperty("出发地-区域ID 镇ID")
    private String lineSenderAreaTownId;

    private String lineSenderAreaTownName;

    @ApiModelProperty("目的地-区域ID 省ID")
    private String lineDeliveryAreaProvinceId;

    private String lineDeliveryAreaProvinceName;
    @ApiModelProperty("目的地-区域ID 市ID")
    private String lineDeliveryAreaCityId;

    private String lineDeliveryAreaCityName;

    @ApiModelProperty("目的地-区域ID 镇ID")
    private String lineDeliveryAreaTownId;

    private String lineDeliveryAreaTownName;
    @ApiModelProperty("开始时间")
    private String carrierStartTime;

    @ApiModelProperty("结束时间")
    private String carrierEndTime;

    @ApiModelProperty("线路报价")
    private BigDecimal linePrice;

    @ApiModelProperty("计费方式")
    private String priceTypeId;

    @ApiModelProperty("备注")
    private String carrierRemark;

    @ApiModelProperty("审核备注")
    private String checkRemark;

    private String createUser;

    private String upStringUser;
}
