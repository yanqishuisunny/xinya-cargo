package com.cargo.location.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author Carlos
 * @since 2020-01-13
 */
@Data
public class DeviceInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String deviceId;

    /**
     * 设备IMEI
     */
    @ApiModelProperty("设备IMEI")
    private String imei;

    /**
     * 供应商Id
     */
    @ApiModelProperty("供应商Id")
    private Integer supplierId;

    /**
     *  供应商编号
     */
    @ApiModelProperty("供应商编号")
    private String supplierCode;


    /**
     * sim卡号
     */
    @ApiModelProperty("sim卡号")
    private String sim;


    /**
     * 设备型号
     */
    @ApiModelProperty("设备型号")
    private String type;

    /**
     * 保修到期时间
     */
    @ApiModelProperty("保修到期时间")
    private String expiration;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 状态
     */
    @ApiModelProperty("设备状态(1:在线2:离线3:拆除)")
    private Integer status;


    /**
     * 设备激活时间
     */
    @ApiModelProperty("设备激活时间")
    private String deviceActivationTime;

    /**
     * sim卡激活时间
     */
    @ApiModelProperty("sim卡激活时间")
    private String simActivationTime;

    /**
     * sim流量到期时间
     */
    @ApiModelProperty("sim流量到期时间")
    private String simExpirationTime;

    /**
     * 安装人员
     */
    @ApiModelProperty("安装人员")
    private String installPerson;

    /**
     * 安装人员电话
     */
    @ApiModelProperty("安装人员电话")
    private String installPhone;

    /**
     * 安装位置照片
     */
    @ApiModelProperty("安装位置照片")
    private String installPositionUrl;

    /**
     * 安装车辆照片
     */
    @ApiModelProperty("安装车辆照片")
    private String installCarUrl;

    /**
     * 安装时间
     */
    @ApiModelProperty("安装时间")
    private String installTime;

    /**
     *省
     */
    @ApiModelProperty("省")
    private String province;

    /**
     *市
     */
    @ApiModelProperty("市")
    private String city;


    /**
     * 绑定车牌号
     * @return
     */
    @ApiModelProperty("绑定车牌号")
    private String vehicleNo;

    /**
     * 绑定的车辆ID
     */
    @ApiModelProperty("绑定的车辆ID")
    private String vehicleId;

    /**
     * 车辆类型
     * @return
     */
    @ApiModelProperty("车辆类型")
    private String carType;

    /**
     * 车辆类型编码
     */
    @ApiModelProperty("车辆类型名称")
    private String carTypeName;

    /**
     * 车辆识别代号
     * @return
     */
    @ApiModelProperty("车辆识别代号")
    private String vinNo ;

    /**
     * 所属车主/承运商
     * @return
     */
    @ApiModelProperty("所属车主/承运商")
    private String carrier;

    /**
     * 所属承运商/车主的orgID
     */
    @ApiModelProperty("所属承运商/车主的orgID")
    private String orgID;

    /**
     * 所属承运商/车主的orgname
     */
    @ApiModelProperty("所属承运商/车主的orgname")
    private String orgName;

    /**
     * 所属承运商/车主的联系方式
     * @return
     */
    @ApiModelProperty("所属承运商/车主的联系方式")
    private String orgContactPhone;

    /**
     * 默认司机
     * @return
     */
    @ApiModelProperty("默认司机")
    private String driverName;

    /**
     * 默认司机联系方式
     * @return
     */
    @ApiModelProperty("默认司机联系方式")
    private String mobile;

    /**
     * 设备供应商名称
     */
    @ApiModelProperty("设备供应商名称")
    private String supplierName;


}
