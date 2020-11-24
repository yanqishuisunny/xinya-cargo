package com.cargo.location.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.util.Date;

/**
 * <p>
 * 车辆设备关系表
 * </p>
 *
 * @author Carlos
 * @since 2020-01-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("car_device_mapping")
public class CarDeviceMappingEntity extends Model<CarDeviceMappingEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.UUID)
    private BigInteger id;

    /**
     * 车辆Id
     */
    @TableField(value = "vehicle_id")
    private String vehicleId;

    /**
     * 车牌号
     */
    @TableField("vehicle_no")
    private String vehicleNo;

    /**
     *  设备Id
     */
    @TableField("device_id")
    private BigInteger deviceId;


    /**
     * '设备IMEI'
     */
    @TableField(value = "imei")
    private String imei;


    /**
     * 解绑时间
     */
    @TableField("unbind_time")
    private Date unbindTime;

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
     * 所属承运商
     */
    @TableField("carrier")
    private String carrier;


}
