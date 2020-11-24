package com.cargo.evaluates.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 评论主表
 * </p>
 *
 * @author jobob
 * @since 2020-10-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EvaluateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String evaluateId;

    private String generalOrderId;

    @ApiModelProperty("订单号")
    private String generalOrderNo;

    /**
     * 运单号
     */
    @ApiModelProperty("运单号")
    private String waybillNo;

    /**
     * 运单id
     */
    @ApiModelProperty("运单id")
    private String waybillId;

    /**
     * 评论类型（1 货主评论运单，2承运商评论订单）
     */
    @ApiModelProperty(" 评论类型（1 货主评论运单，2承运商评论订单）")
    private Integer evaluateType;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String creatUserId;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String creatUserName;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String content;

    /**
     * 评分
     */
    @ApiModelProperty("评分")
    private String score;

    /**
     * 照片路径
     */
    @ApiModelProperty("照片路径")
    private String pictureUrl;


    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("公司id")
    private String orgId;

    @ApiModelProperty("公司id")
    private String starExtend;


}
