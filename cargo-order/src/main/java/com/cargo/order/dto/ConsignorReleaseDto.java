package com.cargo.order.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cargo.order.entity.ConsignorReleaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class ConsignorReleaseDto {

    private List<GoodsDto> listGoods;

    @ApiModelProperty("货主发货 省市县 6位编码")
    private String senderAdCode;

    @ApiModelProperty("货主收货 省市县 6位编码")
    private String deliveryAdCode;

    @ApiModelProperty("1:查询所有   2：当前登录人创建的")
    private String type;

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
    private String expiryDaysString;

    @ApiModelProperty("发货-区域ID 省ID")
    private String senderAreaProvinceId;

    private String senderAreaProvinceName;

    @ApiModelProperty("发货-区域ID 市ID")
    private String senderAreaCityId;

    private String senderAreaCityName;

    @ApiModelProperty("发货-区域ID 镇ID")
    private String senderAreaTownId;

    private String senderAreaTownName;

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

    @ApiModelProperty("收货详细地址")
    private String deliveryAreaDetail;

    @ApiModelProperty("时效天数")
    private Integer prescription;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("计费方式ID")
    private String priceTypeId;

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

}
