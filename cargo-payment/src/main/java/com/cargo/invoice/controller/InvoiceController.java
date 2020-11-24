package com.cargo.invoice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.account.vo.AccountOrderVo;
import com.cargo.invoice.dto.InvoiceDto;
import com.cargo.invoice.entity.InvoiceEntity;
import com.cargo.invoice.service.InvoiceService;
//import com.cargo.invoice.vo.InvoiceVo;
import com.cargo.invoice.vo.InvoiceVo;
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

import javax.xml.ws.Response;

/**
 * <p>
 * 税票表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Api(tags = "税票表")
@RestController
@RequestMapping("/api/payment/invoice")
public class InvoiceController extends SuperController {

    @Autowired
    InvoiceService invoiceService;

    /**
     * 发票列表
     * @param dto
     * @return
     */
    @ApiOperation(value = "发票列表")
    @PostMapping("/list")
    public ResponseInfo invoiceList(@RequestBody InvoiceDto dto){
        Page<InvoiceVo> page = this.getPage(false);
        Page<InvoiceVo> list = invoiceService.invoiceList(dto, page);
        return ResponseUtil.success(list);
    }


    /**
     * 提交申请
     * @param dto
     * @return
     */
    @ApiOperation(value = "提交申请")
    @PostMapping("/create")
    public ResponseInfo createInvoice(@RequestBody InvoiceDto dto){
        invoiceService.createInvoice(dto);
        return ResponseUtil.success();
    }


    /**
     * 发票详情
     * @param dto
     * @return
     */
    @ApiOperation(value = "发票详情")
    @PostMapping("/detail")
    public ResponseInfo invoiceDetail(@RequestBody InvoiceDto dto){
        InvoiceEntity invoiceEntity = invoiceService.invoiceDetail(dto);
        return ResponseUtil.success(invoiceEntity);
    }


    /**
     * 撤回开票申请
     * @param dto
     * @return
     */
    @ApiOperation(value = "撤回开票申请")
    @PostMapping("/recall")
    public ResponseInfo recallInvoice(@RequestBody InvoiceDto dto){
        invoiceService.recallInvoice(dto);
        return ResponseUtil.success();
    }


    /**
     * 删除开票申请
     * @param dto
     * @return
     */
    @ApiOperation(value = "删除开票申请")
    @PostMapping("/delete")
    public ResponseInfo deleteInvoice(@RequestBody InvoiceDto dto){
        invoiceService.deleteInvoice(dto);
        return ResponseUtil.success();
    }


    /**
     * 受理开票申请
     * @param dto
     * @return
     */
    @ApiOperation(value = "受理开票申请")
    @PostMapping("/accept")
    public ResponseInfo acceptInvoice(@RequestBody InvoiceDto dto){
        invoiceService.acceptInvoice(dto);
        return ResponseUtil.success();
    }


    /**
     * 上传发票信息
     * @param dto
     * @return
     */
    @ApiOperation(value = "上传发票信息")
    @PostMapping("/upload")
    public ResponseInfo uploadInvoice(@RequestBody InvoiceDto dto){
        invoiceService.uploadInvoice(dto);
        return ResponseUtil.success();
    }

}

