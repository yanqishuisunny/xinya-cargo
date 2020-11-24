package com.cargo.location.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("device_info")
public class DeviceInfoEntity extends Model<DeviceInfoEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "device_id")
    private BigInteger deviceId;

    /**
     * '设备IMEI'
     */
    @TableField(value = "imei")
    private String imei;

    /**
     * '供应商Id'
     */
    @TableField("supplier_id")
    private Integer supplierId;

    /**
     *  供应商编号
     */
    @TableField("supplier_code")
    private String supplierCode;


    /**
     * 'sim卡号
     */
    @TableField("sim")
    private String sim;


    /**
     * '设备型号'
     */
    @TableField("type")
    private String type;

    /**
     * '到期时间'
     */
    @TableField(value = "expiration")
    private Date expiration;

    /**
     * '备注'
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 删除标记 0:已删除 1:未删除
     */
    @TableField("enable")
    private Boolean enable;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    private Date modifyTime;

    /**
     * 修改人
     */
    @TableField("modify_by")
    private String modifyBy;

    /**
     * 设备激活时间
     */
    @TableField(value = "device_activation_time")
    private Date deviceActivationTime;

    /**
     * sim卡激活时间
     */
    @TableField("sim_activation_time")
    private Date simActivationTime;

    /**
     * sim流量到期时间
     */
    @TableField("sim_expiration_time")
    private Date simExpirationTime;

    /**
     * 安装人员
     */
    @TableField("install_person")
    private String installPerson;

    /**
     * 安装人员电话
     */
    @TableField("install_phone")
    private String installPhone;

    /**
     * 安装位置照片
     */
    @TableField(value = "install_position_url")
    private String installPositionUrl;

    /**
     * 安装车辆照片
     */
    @TableField(value = "install_car_url")
    private String installCarUrl;

    /**
     * 安装时间
     */
    @TableField("install_time")
    private Date installTime;

    /**
     *省
     */
    @TableField("province")
    private String province;

    /**
     *市
     */
    @TableField("city")
    private String city;



    @Override
    protected Serializable pkVal() {
        return null;
    }

}
