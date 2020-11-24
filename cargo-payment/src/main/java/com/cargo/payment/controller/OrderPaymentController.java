package com.cargo.payment.controller;


import com.commom.supper.SuperController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 支付信息表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10
 */
@Api(tags = "支付信息表")
@RestController
@RequestMapping("/api/payment/orderpayment")
public class OrderPaymentController extends SuperController {

}

