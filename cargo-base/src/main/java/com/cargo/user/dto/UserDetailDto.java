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
 * @since 2020-11-05 15:51:52
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UserDetailDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     *
     */
    @ApiModelProperty("")
    private String id;

    
    /**
    * 是否实名认证
    */
    @ApiModelProperty("是否实名认证")
    private String flgPerfect;


    
    /**
    * 身份证名称
    */
    @ApiModelProperty("身份证名称")
    private String idcardName;

    
    /**
    * 身份证号
    */
    @ApiModelProperty("身份证号")
    private String idcardNo;

    
    /**
    * 身份证反面照片
    */
    @ApiModelProperty("身份证反面照片")
    private String idcardReUrl;

    
    /**
    * 身份证正面照片
    */
    @ApiModelProperty("身份证正面照片")
    private String idcardUrl;

    
    /**
    * 身份证发证机关
    */
    @ApiModelProperty("身份证发证机关")
    private String idCardAuthority;

    
    /**
    * 身份证发证开始时间
    */
    @ApiModelProperty("身份证发证开始时间")
    private String idCardEndTime;

    
    /**
    * 身份证发证结束时间
    */
    @ApiModelProperty("身份证发证结束时间")
    private String idCardStartTime;

    
    /**
    * 活体检测图
    */
    @ApiModelProperty("活体检测图")
    private String livingUrl;

    
    /**
    * 个人总证件过期状态 1，已过期
    */
    @ApiModelProperty("个人总证件过期状态 1，已过期")
    private Integer overdueStatus;

    
    /**
    * 用户id
    */
    @ApiModelProperty("用户id")
    private String userId;

}