package com.cargo.location.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@TableName("supplier_info")
public class SupplierInfoEntity extends Model<SupplierInfoEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "supplier_id",type = IdType.AUTO)
    private Integer supplierId;

    /**
     * 供应商编号
     */
    @TableField(value = "supplier_code")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @TableField("supplier_name")
    private String supplierName;

    /**
     * 供应商后台地址
     */
    @TableField("supplier_address")
    private String supplierAddress;

    /**
     * 登录名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 登录密码
     */
    @TableField("user_pwd")
    private String userPwd;

    /**
     * 设备数量
     */
    @TableField(exist = false)
    private Integer equipmentNumber;


    /**
     * 商务联系人
     */
    @TableField("business_contacts")
    private String businessContacts;

    /**
     * 商务联系电话
     */
    @TableField("business_contact_phone")
    private String businessContactPhone;

    /**
     *  账号
     */
    @TableField("app_key")
    private String appKey;


    /**
     * 账密
     */
    @TableField(value = "app_secret")
    private String appSecret;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * '备注'
     */
    @TableField("remark")
    private String remark;

    /**
     * '描述'
     */
    @TableField("description")
    private String description;

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


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
