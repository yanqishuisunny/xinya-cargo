package com.cargo.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("intention_order_goods")
public class IntentionOrderGoodsEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "intention_order_goods_id", type = IdType.UUID)
    private String intentionOrderGoodsId;

    @ApiModelProperty("意向单ID")
    private String intentionOrderId;

    @ApiModelProperty("货物类型ID")
    private String goodsTypeId;

    @ApiModelProperty("货物种类ID")
    private String goodsCategoryId;

    @ApiModelProperty("货物名称")
    private String goodsName;

    @ApiModelProperty("体积值")
    private BigDecimal goodsVolumeVal;

    @ApiModelProperty("体积单位ID")
    private String goodsVolumeUnitId;

    @ApiModelProperty("重量值")
    private BigDecimal goodsWeightVal;

    @ApiModelProperty("重量单位ID")
    private String goodsWeightUnitId;

    private String createUser;

    private String updateUser;


}
