package com.cargo.point.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.point.dto.PointAccountDto;
import com.cargo.point.entity.PointAccountEntity;
import com.cargo.point.mapper.PointAccountMapper;
import com.cargo.point.service.PointAccountService;
import com.cargo.point.vo.PointAccountVo;
import com.commom.core.BeanConverter;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Service
public class PointAccountServiceImpl extends ServiceImpl<PointAccountMapper, PointAccountEntity> implements PointAccountService {

    @Autowired
    PointAccountMapper pointAccountMapper;

    @Override
    public IPage<PointAccountVo> pointRunningList(PointAccountDto dto, Page<PointAccountEntity> page) {
        String orgId = ShiroUtil.getOrgId();

        QueryWrapper<PointAccountEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id",orgId);
        //......
        IPage<PointAccountEntity> pointAccountEntityIPage = pointAccountMapper.selectPage(page, wrapper);

        IPage<PointAccountVo> convert = BeanConverter.convert(PointAccountVo.class, pointAccountEntityIPage);

        return convert;
    }
}
