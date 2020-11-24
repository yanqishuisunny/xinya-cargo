package com.cargo.account.controller;


import com.cargo.account.dto.AccountRunningDto;
import com.cargo.account.entity.AccountRunningEntity;
import com.cargo.account.service.AccountRunningService;
import com.cargo.account.vo.AccountRunningVo;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 帐目流水表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Api(tags = "帐目流水表")
@RestController
@RequestMapping("/api/payment/accountrunning")
public class AccountRunningController extends SuperController {

    @Autowired
    AccountRunningService accountRunningService;


    @ApiOperation(value = "账目流水列表")
    @PostMapping("/list")
    public ResponseInfo<List<AccountRunningVo>> accountRunning(){
        List<AccountRunningVo> accountRunningvo = accountRunningService.accountRunning();
        return ResponseUtil.success(accountRunningvo);
    }


    /**
     * 最新可开票金额
     * @return
     */
    @ApiOperation(value = "最高可开票金额")
    @PostMapping("/getInvoiceAmount")
    public ResponseInfo<BigDecimal> getInvoiceAmount(){
        BigDecimal invoiceAmount = accountRunningService.getInvoiceAmount();
        return ResponseUtil.success(invoiceAmount);

    }

}

