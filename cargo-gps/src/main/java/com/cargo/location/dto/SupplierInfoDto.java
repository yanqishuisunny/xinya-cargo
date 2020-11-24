package com.cargo.location.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 车辆设备关系表
 * </p>
 *
 * @author Carlos
 * @since 2020-01-13
 */
@Data
public class SupplierInfoDto implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */

    @ApiModelProperty("主键")
    private Integer supplierId;

    /**
     * 供应商名称
     */
    @ApiModelProperty("供应商名称")
    private String supplierName;

    /**
     * 供应商后台地址
     */
    @ApiModelProperty("供应商后台地址")
    private String supplierAddress;

    /**
     * 商务联系人
     */
    @ApiModelProperty("商务联系人")
    private String businessContacts;

    /**
     * 商务联系电话
     */
    @ApiModelProperty("商务联系电话")
    private String businessContactPhone;

    /**
     * 后台账号
     */
    @ApiModelProperty("后台账号")
    private String userName;

    /**
     * 后台密码
     */
    @ApiModelProperty("后台密码")
    private String userPwd;

//    /**
//     *  账号
//     */
//    @ApiModelProperty("账号")
//    private String appKey;
//
//
//    /**
//     * 账密
//     */
//    @ApiModelProperty("账密")
//    private String appSecret;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;


    @ApiModelProperty("条数")
    private int limit;

    @ApiModelProperty("页数")
    private int page;


}
