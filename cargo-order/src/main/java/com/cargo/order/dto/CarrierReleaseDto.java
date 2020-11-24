package com.cargo.order.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cargo.order.entity.CarrierReleaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarrierReleaseDto  {
    private List<CarrierCarsDto> listCarrierCars;
    @ApiModelProperty("车辆ID")
    private String carId;

    @ApiModelProperty("1:所有   2：当前登录人创建的")
    private String type;

    @ApiModelProperty("承运商出发地 省市县 6位编码")
    private String carrierSenderAdCode;

    @ApiModelProperty("承运商目的地 省市县 6位编码")
    private String carrierDeliveryAdCode;

    @TableId(value = "carrier_release_id", type = IdType.UUID)
    private String carrierReleaseId;

    @ApiModelProperty("承运商信息发布状态 状态( 0：创建  1:待审核（已提交）   2：审核通过   3：过期失效，-2 审核不过，-1：撤销)")
    private String status;

    @ApiModelProperty("有效天数")
    private Integer expiryDays;

    @ApiModelProperty("发布日期")
    private String expiryDaysString;

    @ApiModelProperty("时效天数")
    private Integer prescription;

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
}
