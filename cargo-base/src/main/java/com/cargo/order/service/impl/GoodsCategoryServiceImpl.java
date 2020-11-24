package com.cargo.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.order.entity.GoodsCategoryEntity;
import com.cargo.order.mapper.GoodsCategoryMapper;
import com.cargo.order.service.GoodsCategoryService;
import com.cargo.order.vo.GoodsCategoryVo;
import com.commom.utils.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品分类 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-23
 */
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategoryEntity> implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public List<GoodsCategoryVo> tree() {
        QueryWrapper<GoodsCategoryEntity> qw = new QueryWrapper<>();
        qw.eq("parent_id",GoodsCategoryEntity.PARENTID_0);
        List<GoodsCategoryVo> listVo = goodsCategoryMapper.queryList();
        listVo.stream().forEach(e ->{
            e.setId(e.getGoodsCategoryId());
        });
        return listVo.stream()
                .filter(n -> Objects.equals("0", n.getParentId()))
                .map(e -> TreeUtils.findChildren(e, listVo))
                .collect(Collectors.toList());

    }
}
