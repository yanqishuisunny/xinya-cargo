package com.cargo.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.base.dto.PaymentAccountDto;
import com.cargo.base.entity.PaymentAccountEntity;
import com.cargo.base.vo.PaymentAccountVo;

/**
 * <p>
 * 支付账户表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
public interface PaymentAccountService extends IService<PaymentAccountEntity> {

    public void addPaymentAccount(PaymentAccountDto dto);

    public void updatePaymentAccount(PaymentAccountDto dto);

    PaymentAccountVo paymentAccountDetail(PaymentAccountDto dto);

    IPage<PaymentAccountVo> paymentAccountList(PaymentAccountDto dto, Page<PaymentAccountEntity> page);

    public void paymentAccountDelete(PaymentAccountDto dto);
}
