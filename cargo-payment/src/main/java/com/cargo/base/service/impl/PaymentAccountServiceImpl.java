package com.cargo.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.base.dto.PaymentAccountDto;
import com.cargo.base.entity.PaymentAccountEntity;
import com.cargo.base.mapper.PaymentAccountMapper;
import com.cargo.base.service.PaymentAccountService;
import com.cargo.base.vo.PaymentAccountVo;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 支付账户表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Service
public class PaymentAccountServiceImpl extends ServiceImpl<PaymentAccountMapper, PaymentAccountEntity> implements PaymentAccountService {

    @Autowired
    PaymentAccountMapper paymentAccountMapper;

    @Override
    public void addPaymentAccount(PaymentAccountDto dto) {

        String orgId = ShiroUtil.getOrgId();
        String userId = ShiroUtil.currentUserId();

        PaymentAccountEntity paymentAccountEntity = new PaymentAccountEntity();
        BeanUtils.copyProperties(dto,paymentAccountEntity);

        //1.校验

        paymentAccountEntity.setOrgId(orgId);
        paymentAccountEntity.setCreateUser(userId);
        paymentAccountMapper.insert(paymentAccountEntity);
    }

    @Override
    public void updatePaymentAccount(PaymentAccountDto dto) {

        String paymentAccountId = dto.getPaymentAccountId();

        PaymentAccountEntity paymentAccountEntity = paymentAccountMapper.selectById(paymentAccountId);

        BeanUtils.copyProperties(dto,paymentAccountEntity,getNullPropertyNames(dto));

        paymentAccountMapper.updateById(paymentAccountEntity);
    }

    @Override
    public PaymentAccountVo paymentAccountDetail(PaymentAccountDto dto) {
        String paymentAccountId = dto.getPaymentAccountId();

        if(paymentAccountId==null||paymentAccountId.isEmpty()){
            throw new BussException("支付账号信息不得为空");
        }
        PaymentAccountEntity paymentAccountEntity = paymentAccountMapper.selectById(paymentAccountId);
        PaymentAccountVo convert = BeanConverter.convert(PaymentAccountVo.class, paymentAccountEntity);
        return convert;
    }

    @Override
    public IPage<PaymentAccountVo> paymentAccountList(PaymentAccountDto dto, Page<PaymentAccountEntity> page) {
        String orgId = ShiroUtil.getOrgId();

        QueryWrapper<PaymentAccountEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id",orgId);
        //......
        IPage<PaymentAccountEntity> paymentAccountEntityIPage = paymentAccountMapper.selectPage(page, wrapper);

        IPage<PaymentAccountVo> convert = BeanConverter.convert(PaymentAccountVo.class, paymentAccountEntityIPage);
        return convert;
    }

    @Override
    public void paymentAccountDelete(PaymentAccountDto dto) {
        String paymentAccountId = dto.getPaymentAccountId();

        PaymentAccountEntity paymentAccountEntity = paymentAccountMapper.selectById(paymentAccountId);

        if(paymentAccountEntity==null){
            throw new BussException("该账号不存在");
        }
        paymentAccountMapper.deleteById(paymentAccountId);

    }









    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
