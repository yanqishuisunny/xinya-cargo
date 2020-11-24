package com.cargo.account.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.account.dto.AccountOrderDto;
import com.cargo.account.dto.AccountOrderListDto;
import com.cargo.account.service.AccountOrderService;
import com.cargo.account.vo.AccountOrderDetailVo;
import com.cargo.account.vo.AccountOrderVo;
import com.commom.shiro.ShiroUtil;
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
 * 帐目表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Api(tags = "帐目表")
@RestController
@RequestMapping("/api/payment/accountorder")
public class AccountOrderController extends SuperController {

    @Autowired
    AccountOrderService accountOrderService;

    /**
     * 账目列表
     * @param dto
     * @return
     */
    @ApiOperation(value = "账目列表")
    @PostMapping("/list")
    public ResponseInfo<Page<AccountOrderVo>> accountOrderMapperList(@RequestBody AccountOrderListDto dto){

        Page<AccountOrderVo> page = this.getPage(false);

        String orgId = ShiroUtil.getOrgId();
        dto.setOrgId(orgId);
        return ResponseUtil.success(accountOrderService.accountOrderMapperList(dto, page));
    }

    /**
     * 锁帐
     * @param dto
     * @return
     */
    @ApiOperation(value = "锁帐")
    @PostMapping("/update")
    public ResponseInfo updateAccountOrder(@RequestBody AccountOrderDto dto){
        accountOrderService.updateAccountOrder(dto);
        return ResponseUtil.success();
    }


    /**
     * 查看明细
     * @param dto
     * @return
     */
    @ApiOperation(value = "查看账目明细")
    @PostMapping("/detail")
    public ResponseInfo<AccountOrderDetailVo> accountOrderDetail(@RequestBody AccountOrderDto dto){
        AccountOrderDetailVo accountOrderDetailVo = accountOrderService.accountOrderDetail(dto);
        return ResponseUtil.success(accountOrderDetailVo);
    }


}

