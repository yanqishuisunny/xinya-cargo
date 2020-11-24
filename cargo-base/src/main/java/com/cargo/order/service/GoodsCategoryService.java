package com.cargo.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.order.entity.GoodsCategoryEntity;
import com.cargo.order.vo.GoodsCategoryVo;

import java.util.List;

/**
 * <p>
 * 商品分类 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-10-23
 */
public interface GoodsCategoryService extends IService<GoodsCategoryEntity> {
    List<GoodsCategoryVo> tree();
}
