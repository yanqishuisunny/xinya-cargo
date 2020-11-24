package com.cargo.evaluates.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 评论主表
 * </p>
 *
 * @author jobob
 * @since 2020-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EvaluateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String evaluateId;

    private String generalOrderId;

    @ApiModelProperty("订单号")
    private String generalOrderNo;

    /**
     * 运单号
     */
    @ApiModelProperty("运单号")
    private String waybillNo;

    /**
     * 运单id
     */
    @ApiModelProperty("运单id")
    private String waybillId;

    /**
     * 评论类型（1 货主评论运单，2承运商评论订单）
     */
    @ApiModelProperty("评论类型（1 货主评论运单，2承运商评论订单）")
    private Integer evaluateType;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String creatUserId;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String creatUserName;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String content;

    /**
     * 照片路径
     */
    @ApiModelProperty("照片路径")
    private String pictureUrl;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 修改时间
     */
    private String gmtModified;

    /**
     * 是否有效
     */
    private Integer isAble;

    /**
     * 评分
     */
    @ApiModelProperty("评分")
    private String score;

    @ApiModelProperty("公司id")
    private String orgId;

    @ApiModelProperty("jsoN 扩展")
    private String starExtend;



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


    @ApiModelProperty("收货详细地址")
    private String deliveryAreaDetail;


    /**
     * 货物类型名称
     */
    @ApiModelProperty("货物类型名称")
    private String goodsName;



}
