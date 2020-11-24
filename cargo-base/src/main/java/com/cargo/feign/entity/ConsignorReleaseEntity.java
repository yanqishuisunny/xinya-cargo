package com.cargo.feign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
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
 * @since 2020-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("consignor_release")
public class ConsignorReleaseEntity extends BaseEntity implements Serializable {
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
    @TableId(value = "consignor_release_id", type = IdType.UUID)
    private String consignorReleaseId;

    @ApiModelProperty("货主ID 等同于 创建人ID")
    private String cargoUserId;

    @ApiModelProperty("货主信息发布状态 状态( 0：创建  1:待审核（已提交）   2：审核通过   3：过期失效，-2 审核不过，-1：撤销)")
    private String status;


    @ApiModelProperty("发货单位")
    private String senderOrgName;

    @ApiModelProperty("发货人")
    private String senderUserName;

    @ApiModelProperty("发货人联系方式")
    private String senderUserPhone;

    @ApiModelProperty("有效天数")
    private Integer expiryDays;

    @ApiModelProperty("发布日期")
    private String expiryDaysDate;

    @ApiModelProperty("有效期日期(发布日期加上有效天数算出)")
    private String validityDate;

    @ApiModelProperty("发货-区域ID 省ID")
    private String senderAreaProvinceId;

    private String senderAreaProvinceName;

    @ApiModelProperty("发货-区域ID 市ID")
    private String senderAreaCityId;

    private String senderAreaCityName;

    @ApiModelProperty("发货-区域ID 镇ID")
    private String senderAreaTownId;

    private String senderAreaTownName;

    @ApiModelProperty("发货地经纬度")
    private String senderProcityName;

    @ApiModelProperty("装货详细地址")
    private String senderAreaDetail;

    @ApiModelProperty("装货开始时间")
    private String senderStartTime;

    @ApiModelProperty("装货结束时间")
    private String senderEndTime;

    @ApiModelProperty("收货-区域ID 省ID")
    private String deliveryAreaProvinceId;

    private String deliveryAreaProvinceName;

    @ApiModelProperty("收货-区域ID 市ID")
    private String deliveryAreaCityId;

    private String deliveryAreaCityName;

    @ApiModelProperty("收货-区域ID 镇ID")
    private String deliveryAreaTownId;

    private String deliveryAreaTownName;

    @ApiModelProperty("卸货地经纬度")
    private String deliveryProcityName;

    @ApiModelProperty("收货详细地址")
    private String deliveryAreaDetail;

    @ApiModelProperty("时效天数")
    private Integer prescription;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("计费方式ID")
    private String priceTypeId;

    @ApiModelProperty("预估里程数")
    private BigDecimal gpsMileage;

    @ApiModelProperty("运输类型ID")
    private String trspotTypeId;

    @ApiModelProperty("车辆要求id")
    private String carTypeId;

    @ApiModelProperty("车辆尺寸ID")
    private String carSizeId;

    @ApiModelProperty("审核备注")
    private String checkRemark;

    @ApiModelProperty("特殊要求")
    private String consignorRemark;

    @ApiModelProperty("收货单位")
    private String deliveryOrgName;

    @ApiModelProperty("收货人")
    private String deliveryUserName;

    @ApiModelProperty("收货人联系方式")
    private String deliveryUserPhone;

    private String createUser;

    private String updateUser;


}
