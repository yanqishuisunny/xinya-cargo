package com.cargo.order.entity;

import java.math.BigDecimal;
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
 * @since 2020-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("intention_order")
@ApiModel("意向单表")
public class IntentionOrderEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //1:意向发起人是货主    2：意向发起人是承运商
    public static final Integer TYPE_1 = 1;
    public static final Integer TYPE_2 = 2;




    @TableId(value = "intention_order_id", type = IdType.UUID)
    private String intentionOrderId;

    @ApiModelProperty("意向单状态")
    private String intentionStatus;

    @ApiModelProperty("意向单号")
    private String intentionOrderNo;

    @ApiModelProperty("1:意向发起人是货主    2：意向发起人是承运商")
    private Integer type;

    @ApiModelProperty("意向单来自")
    private String fromUserId;

    @ApiModelProperty("意向单发向")
    private String toUserId;

    @ApiModelProperty("type为1时:承运商发布信息ID    2时：货主发布信息ID")
    private String resourceId;

    @ApiModelProperty("货主ID")
    private String cargoUserId;

    @ApiModelProperty("承运商ID")
    private String carrierUserId;

    @ApiModelProperty("发货单位")
    private String senderOrgName;

    @ApiModelProperty("发货人")
    private String senderUserName;

    @ApiModelProperty("发货人联系方式")
    private String senderUserPhone;

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

    @ApiModelProperty("收货-区域ID 省名称")
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

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("计费方式ID")
    private String priceTypeId;

    @ApiModelProperty("预估里程数")
    private BigDecimal gpsMileage;

    @ApiModelProperty("运输类型ID")
    private String trspotTypeId;

    @ApiModelProperty("车辆要求ID")
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

    @ApiModelProperty("拒绝备注")
    private String refuseRemark;


    private String createUser;

    private String updateUser;



}
