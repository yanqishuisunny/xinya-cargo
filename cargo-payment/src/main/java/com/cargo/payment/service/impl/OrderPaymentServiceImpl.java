package com.cargo.payment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.payment.entity.OrderPaymentEntity;
import com.cargo.payment.mapper.OrderPaymentMapper;
import com.cargo.payment.service.OrderPaymentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10
 */
@Service
public class OrderPaymentServiceImpl extends ServiceImpl<OrderPaymentMapper, OrderPaymentEntity> implements OrderPaymentService {

}
