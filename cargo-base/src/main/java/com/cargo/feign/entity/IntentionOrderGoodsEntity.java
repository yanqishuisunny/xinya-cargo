package com.cargo.feign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

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

    /**
     * 意向单ID
     */
    private String intentionOrderId;

    /**
     * 货物类型ID
     */
    private String goodsTypeId;

    /**
     * 货物种类ID
     */
    private String goodsCategoryId;

    /**
     * 货物名称
     */
    private String goodsName;

    /**
     * 体积值
     */
    private BigDecimal goodsVolumeVal;

    /**
     * 体积单位ID
     */
    private String goodsVolumeUnitId;

    /**
     * 重量值
     */
    private BigDecimal goodsWeightVal;

    private String goodsWeightUnitId;

    private String createUser;

    private String updateUser;


}
