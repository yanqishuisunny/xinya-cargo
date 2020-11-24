package com.cargo.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModel;
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
 * @since 2020-11-03
 */
@Data
@ApiModel("承运商信息表")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("carrier_release")
public class CarrierReleaseEntity extends BaseEntity implements Serializable {
    /*
     * 状态( 0：创建  1:待审核   2：审核通过   3：过期失效， -2 审核不过，-1：撤销)
     * */
    public static final String STATUS_CREATE = "0";
    public static final String STATUS_EXAMINE_WAIT = "1";
    public static final String STATUS_EXAMINE_SUCCESS = "2";
    public static final String STATUS_EXPIRE = "3";
    public static final String STATUS_EXAMINE_FAIL = "-2";
    public static final String STATUS_EXAMINE_REVOKE = "-1";


    private static final long serialVersionUID = 1L;
    @TableId(value = "carrier_release_id", type = IdType.UUID)
    private String carrierReleaseId;

    @ApiModelProperty("承运商信息发布状态 状态( 0：创建  1:待审核（已提交）   2：审核通过   3：过期失效，-2 审核不过，-1：撤销)")
    private String status;

    @ApiModelProperty("有效天数")
    private Integer expiryDays;

    @ApiModelProperty("发布日期")
    private String expiryDaysDate;

    @ApiModelProperty("有效期日期(发布日期加上有效天数算出)")
    private String validityDate;

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

    private String updateUser;


}
