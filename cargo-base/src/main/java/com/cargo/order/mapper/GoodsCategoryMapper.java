package com.cargo.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.order.entity.GoodsCategoryEntity;
import com.cargo.order.vo.GoodsCategoryVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 商品分类 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-10-23
 */
@Repository
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategoryEntity> {

    List<GoodsCategoryVo> queryList();
}
