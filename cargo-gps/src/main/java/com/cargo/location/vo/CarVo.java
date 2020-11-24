package com.cargo.location.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 车辆表
 * </p>
 *
 * @author Carlos
 * @since 2019-08-06
 */
@Getter
@Setter
@ToString
public class CarVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	private String vehicleId;
    /**
     * 车牌号
     */
    @ApiModelProperty("车牌号")
    private String vehicleNo;
    /**
     * 所属单位ID
     */
    @ApiModelProperty("所属单位ID")
	private String orgId;
    /**
     * 所属组织名称
     */
    @ApiModelProperty("所属承运商")
    private String orgName;
    /**
     * 所属承运商/公司联系号码
     */
    @ApiModelProperty("所属承运商/公司联系号码")
    private String orgContactPhone;
    /**
     * 车辆类型
     */
    @ApiModelProperty("车辆类型")
    private String carType;

    /**
     * 车辆类型编码
     */
    @ApiModelProperty("车辆类型名称")
    private String carTypeName;

    /**
     * 车架号/车辆识别代号
     */
    @ApiModelProperty("车架号/车辆识别代号")
    private String vinNo;
    /**
     * 默认司机
     */
    @ApiModelProperty("默认司机")
    private String driverName;
    /**
     * 默认司机
     */
    @ApiModelProperty("默认司机联系方式")
    private String mobile;

    /**
     * 是否绑定
     */
    @ApiModelProperty("是否绑定:0未绑定，1已绑定")
    private String isBinding;






}
