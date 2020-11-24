package com.cargo.user.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 开发者
 * @since 2020-11-13 14:25:37
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DriverDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     *主键
     */
    @ApiModelProperty("主键")
    private String id;

    
    /**
    * 是否在APP服务单位中被删除（0:否,1:是）
    */
    @ApiModelProperty("是否在APP服务单位中被删除（0:否,1:是）")
    private Integer delFlag;

    
    /**
    * 合作司机id
    */
    @ApiModelProperty("合作司机id")
    private String driverId;

    
    /**
    * 合作司机名称
    */
    @ApiModelProperty("合作司机名称")
    private String driverName;

    
    /**
    * 合作司机手机号
    */
    @ApiModelProperty("合作司机手机号")
    private String driverPhoneNo;

    
    /**
    * 是否查看（0未，1是）
    */
    @ApiModelProperty("是否查看（0未，1是）")
    private Integer isShow;

    
    /**
    * 司机手机号
    */
    @ApiModelProperty("司机手机号")
    private String phoneNo;

    
    /**
    * 状态（1：待接受，2：合作中，3：已解除，4：已拒绝）
    */
    @ApiModelProperty("状态（1：待接受，2：合作中，3：已解除，4：已拒绝）")
    private Integer status;

    
    /**
    * 司机/用户id
    */
    @ApiModelProperty("司机/用户id")
    private String userId;

    
    /**
    * 司机/用户名称
    */
    @ApiModelProperty("司机/用户名称")
    private String userName;

}