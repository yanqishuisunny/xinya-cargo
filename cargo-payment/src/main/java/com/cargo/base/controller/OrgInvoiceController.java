package com.cargo.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.base.dto.OrgInvoiceDto;
import com.cargo.base.entity.OrgInvoiceEntity;
import com.cargo.base.service.OrgInvoiceService;
import com.cargo.base.vo.OrgInvoiceVo;
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

import java.util.List;

/**
 * <p>
 * 发票抬头表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Api(tags = "发票抬头表")
@RestController
@RequestMapping("/api/payment/orginvoice")
public class OrgInvoiceController extends SuperController {

    @Autowired
    OrgInvoiceService orgInvoiceService;


    @ApiOperation(value = "新增发票抬头")
    @PostMapping("/add")
    public ResponseInfo addOrgInvoice(@RequestBody OrgInvoiceDto dto){

        orgInvoiceService.addOrgInvoice(dto);

        return ResponseUtil.success();
    }

    @ApiOperation(value = "更新发票抬头")
    @PostMapping("/update")
    public ResponseInfo updateOrgInvoice(@RequestBody OrgInvoiceDto dto){

        orgInvoiceService.updateOrgInvoice(dto);

        return ResponseUtil.success();
    }

    @ApiOperation(value = "发票抬头详情")
    @PostMapping("/detail")
    public ResponseInfo orgInvoiceDetail(@RequestBody OrgInvoiceDto dto){

        OrgInvoiceVo orgInvoiceVo = orgInvoiceService.orgInvoiceDetail(dto);

        return ResponseUtil.success(orgInvoiceVo);
    }


    @ApiOperation(value = "发票抬头列表")
    @PostMapping("/list")
    public ResponseInfo orgInvoiceList(@RequestBody OrgInvoiceDto dto){

        Page<OrgInvoiceEntity> page = this.getPage(false);
        IPage<OrgInvoiceVo> list = orgInvoiceService.orgInvoiceList(dto, page);

        return ResponseUtil.success(list);
    }
}