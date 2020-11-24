package com.cargo.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.base.dto.PaymentAccountDto;
import com.cargo.base.entity.PaymentAccountEntity;
import com.cargo.base.service.PaymentAccountService;
import com.cargo.base.vo.PaymentAccountVo;
import com.cargo.point.entity.PointAccountEntity;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 支付账户表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Api(tags = "支付账户表")
@RestController
@RequestMapping("/api/payment/account")
public class PaymentAccountController extends SuperController {

    @Autowired
    PaymentAccountService paymentAccountService;

    @ApiOperation(value = "新增支付账户")
    @PostMapping("/add")
    public ResponseInfo addPaymentAccount(@RequestBody PaymentAccountDto dto){
        paymentAccountService.addPaymentAccount(dto);
        return ResponseUtil.success();
    }

    @ApiOperation(value = "更新支付账户")
    @PostMapping("/update")
    public ResponseInfo updatePaymentAccount(@RequestBody PaymentAccountDto dto){
        paymentAccountService.updatePaymentAccount(dto);
        return ResponseUtil.success();
    }

    @ApiOperation(value = "支付账户详情")
    @PostMapping("/detail")
    public ResponseInfo paymentAccountDetail(@RequestBody PaymentAccountDto dto){
        PaymentAccountVo paymentAccountVo = paymentAccountService.paymentAccountDetail(dto);
        return ResponseUtil.success(paymentAccountVo);
    }

    @ApiOperation(value = "支付账户列表")
    @PostMapping("/list")
    public ResponseInfo paymentAccountList(@RequestBody PaymentAccountDto dto){
        Page<PaymentAccountEntity> page = this.getPage(false);
        IPage<PaymentAccountVo> list = paymentAccountService.paymentAccountList(dto, page);
        return ResponseUtil.success(list);
    }

    @ApiOperation(value = "删除支付账户")
    @PostMapping("/delete")
    public ResponseInfo paymentAccountDelete(@RequestBody PaymentAccountDto dto){
        paymentAccountService.paymentAccountDelete(dto);
        return ResponseUtil.success();
    }

}

