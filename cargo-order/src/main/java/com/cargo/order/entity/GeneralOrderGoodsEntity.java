package com.cargo.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.commom.supper.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单单货物表
 * </p>
 *
 * @author jobob
 * @since 2020-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("general_order_goods")
public class GeneralOrderGoodsEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "general_order_goods_id", type = IdType.UUID)
    private String generalOrderGoodsId;

    /**
     * 订单ID
     */
    private String generalOrderId;

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
