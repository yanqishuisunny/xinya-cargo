package com.cargo.location.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author Carlos
 * @since 2020-01-13
 */
@Data
@ApiModel
@ToString
@EqualsAndHashCode(callSuper = false)
public class DeviceInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;



    /**
     * ID
     */
    @ApiModelProperty("主键")
    private BigInteger deviceId;

    /**
     * 设备IMEI
     */
    @ApiModelProperty("设备IMEI")
    private String imei;

    /**
     * 设备型号
     */
    @ApiModelProperty("设备型号")
    private String type;

    /**
     * 供应商Id
     */
    @ApiModelProperty("供应商Id")
    @Max(value = 999999999,message = "供应商主键有误")
    private Integer supplierId;

    /**
     *  供应商编号
     */
    @ApiModelProperty("供应商编号")
    private String supplierCode;

    /**
     * 设备供应商名称
     */
    @ApiModelProperty("设备供应商名称")
    private String supplierName;


    /**
     * 设备激活时间
     */
    @ApiModelProperty("设备激活时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deviceActivationTime;

    /**
     * 保修到期时间
     */
    @ApiModelProperty("保修到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiration;


    /**
     * sim卡号
     */
    @ApiModelProperty("sim卡号")
    private String sim;

    /**
     * sim卡激活时间
     */
    @ApiModelProperty("sim卡激活时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date simActivationTime;

    /**
     * sim流量到期时间
     */
    @ApiModelProperty("sim流量到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date simExpirationTime;

    /**
     * sim流量到期时间
     */
    @ApiModelProperty("sim流量到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endSimExpirationTime;

    /**
     * 安装时间
     */
    @ApiModelProperty("安装时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date installTime;



    /**
     * 状态(1.在线、2.离线、3.拆除)
     */
    @ApiModelProperty("状态")
    private int status;

    //安装信息
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
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

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
     * 车辆类型名称
     * @return
     */
    @ApiModelProperty("车辆类型名称")
    private String carTypeName;

    /**
     * 车辆识别代号
     * @return
     */
    @ApiModelProperty("车辆识别代号")
    private String  vinNo;

    /**
     * 所属车主/承运商
     * @return
     */
    @ApiModelProperty("所属车主/承运商")
    private String carrier;

    /**
     * 联系方式
     * @return
     */
    @ApiModelProperty("所属车主/承运商联系方式")
    private String organisationContactPhone;

    /**
     * 默认司机
     * @return
     */
    @ApiModelProperty("默认司机")
    private String defaultDriver;

    /**
     * 联系方式
     * @return
     */
    @ApiModelProperty("默认司机联系方式")
    private String defaultDriverContactPhone;

    //省市
    /**
     * province省
     */
    @ApiModelProperty("省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty("市")
    private String city;

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
     * 分页开始位置
     */
    private int startOffset;
    /**
     * 页数
     */
    private int pageSize;


}
