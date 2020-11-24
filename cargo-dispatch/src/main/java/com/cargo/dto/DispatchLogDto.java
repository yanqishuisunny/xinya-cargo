package com.cargo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 调度单表
 * </p>
 *
 * @author jobob
 * @since 2020-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DispatchLogDto extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String dispatchId;

    /**
     * 关联订单id
     */
    private String orderId;

    /**
     * 关联订单号
     */
    private String orderNo;

    /**
     * 关联运单id
     */
    private String waybillId;

    /**
     * 关联运单号
     */
    private String waybillNo;

    /**
     * 车牌号
     */
    @ApiModelProperty("carNo")
    private String carNo;

    /**
     * 车牌id
     */
    private String carId;

    /**
     * 车挂号
     */
    private String hangNo;

    /**
     * 司机id
     */
    private String driverId;

    /**
     * 司机名字
     */
    @ApiModelProperty("司机名字")
    private String driverName;

    /**
     * 司机手机号
     */
    private String driverPhone;

    /**
     * 运单重量
     */
    private String weight;


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
    /**
     * 货物代码
     */
    private String goodCode;

    /**
     * 货物名称
     */
    private String goodName;



    /**
     * 创建人
     */
    private String createName;

    /**
     * 创建人
     */
    private String createId;


    /**
     * 修改人
     */
    private String modifiedBy;


    /**
     * 备注
     */
    private String remark;

    private String orgId;
    private String orgName;

    @ApiModelProperty("预计到达时间")
    private LocalDateTime estimatedStartTime;
    private LocalDateTime estimatedEndTime;

    @ApiModelProperty("预计发车时间")
    private LocalDateTime estimatStartTime;
    private LocalDateTime estimatEndTime;

}
