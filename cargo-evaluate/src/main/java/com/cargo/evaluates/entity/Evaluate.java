package com.cargo.evaluates.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@TableName("evaluate")
public class Evaluate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value="evaluate_id", type= IdType.UUID)
    private String evaluateId;

    private String generalOrderId;

    @ApiModelProperty("订单号")
    private String generalOrderNo;


    /**
     * 运单号
     */
    private String waybillNo;

    /**
     * 运单id
     */
    private String waybillId;

    /**
     * 评论类型（1 货主评论运单，2承运商评论订单）
     */
    private Integer evaluateType;

    /**
     * 创建人
     */
    private String creatUserId;

    /**
     * 创建人
     */
    private String creatUserName;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 照片路径
     */
    private String pictureUrl;


    /**
     * 备注
     */
    private String remark;

    /**
     * 评分
     */
    private String score;


    @ApiModelProperty("公司id")
    private String orgId;

    @ApiModelProperty("公司id")
    private String starExtend;



}
