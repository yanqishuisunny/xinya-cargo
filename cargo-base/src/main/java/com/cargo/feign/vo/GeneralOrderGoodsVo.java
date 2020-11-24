package com.cargo.feign.vo;

import com.cargo.feign.entity.GeneralOrderGoodsEntity;
import lombok.Data;

@Data
public class GeneralOrderGoodsVo extends GeneralOrderGoodsEntity {
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
