package com.cargo.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.account.dto.AccountRunningDto;
import com.cargo.account.entity.AccountRunningEntity;
import com.cargo.account.mapper.AccountRunningMapper;
import com.cargo.account.service.AccountRunningService;
import com.cargo.account.vo.AccountRunningVo;
import com.commom.core.BeanConverter;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 帐目流水表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Service
public class AccountRunningServiceImpl extends ServiceImpl<AccountRunningMapper, AccountRunningEntity> implements AccountRunningService {

    @Autowired
    AccountRunningMapper accountRunningMapper;

    @Override
    public List<AccountRunningVo> accountRunning() {
        String orgId = ShiroUtil.getOrgId();
        QueryWrapper<AccountRunningEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("org_id", orgId);
        List<AccountRunningEntity> accountRunningEntities = accountRunningMapper.selectList(queryWrapper);

        List<AccountRunningVo> convert = BeanConverter.convert(AccountRunningVo.class, accountRunningEntities);
        return convert;
    }

    @Override
    public BigDecimal getInvoiceAmount() {
        String orgId = ShiroUtil.getOrgId();
        BigDecimal invoiceAmount = BigDecimal.ZERO;

        QueryWrapper<AccountRunningEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("org_id", orgId);
        queryWrapper.last("limit 1");
        AccountRunningEntity lastAccountRunningEntity = accountRunningMapper.selectOne(queryWrapper);

        if (lastAccountRunningEntity != null) {
            invoiceAmount = lastAccountRunningEntity.getInvoiceAmount();
        }

        return invoiceAmount;
    }


}
