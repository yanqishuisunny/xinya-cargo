package com.cargo.feign.dto;

import com.commom.cache.modelmapper.Convert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10 09:38:58
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DriverOrgDto extends Convert implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 合作关系主键
     */
    @ApiModelProperty("合作关系主键")
    private String id;

    /**
     * 是否查看（0未，1是）
     */
    @ApiModelProperty("是否查看（0未，1是）")
    private Integer isShow;


    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private String orgId;


    /**
     *
     */
    @ApiModelProperty("")
    private String orgName;


    /**
     * 服务结束时间
     */
    @ApiModelProperty("服务结束时间")
    private LocalDateTime serveEndTime;


    /**
     * 服务开始时间
     */
    @ApiModelProperty("服务开始时间")
    private LocalDateTime serveStartTime;


    /**
     * 状态（1：待接受，2：合作中，3：已解除，4：已拒绝）
     */
    @ApiModelProperty("状态（1：待接受，2：合作中，3：已解除，4：已拒绝 5: 撤回/删除）")
    private Integer status;


    /**
     * 1:意向发起人是司机,2：意向发起人是企业
     */
    @ApiModelProperty("1:意向发起人是司机,2：意向发起人是企业")
    private Integer type;


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

    /**
     * 司机手机号
     */
    @ApiModelProperty("司机手机号")
    private String phoneNo;


}