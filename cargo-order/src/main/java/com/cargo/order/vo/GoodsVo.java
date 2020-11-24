package com.cargo.order.vo;

import com.cargo.order.entity.GoodsEntity;
import lombok.Data;

@Data
public class GoodsVo extends GoodsEntity {

    /**
     * 货物类型名称
     */
    private String goodsTypeName;
    /**
     * 货物种类名称
     */
    private String goodsCategoryName;
    /**
     * 体积单位名称
     */
    private String goodsVolumeUnitName;
    /**
     * 重量值名称
     */
    private String goodsWeightUnitName;

}
