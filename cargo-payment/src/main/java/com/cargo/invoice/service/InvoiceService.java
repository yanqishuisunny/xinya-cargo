package com.cargo.invoice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.invoice.dto.InvoiceDto;
import com.cargo.invoice.entity.InvoiceEntity;
import com.cargo.invoice.vo.InvoiceVo;
//import com.cargo.invoice.vo.InvoiceVo;

/**
 * <p>
 * 税票表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
public interface InvoiceService extends IService<InvoiceEntity> {

    /**
     * 发票列表
     * @param dto
     * @return
     */
    Page<InvoiceVo> invoiceList(InvoiceDto dto, Page<InvoiceVo> pag);

    /**
     * 提交申请
     * @param dto
     * @return
     */
    public void createInvoice(InvoiceDto dto);

    /**
     * 发票详情
     * @param dto
     * @return
     */
    public InvoiceEntity invoiceDetail(InvoiceDto dto);

    /**
     * 撤回开票申请
     * @param dto
     * @return
     */
    public void recallInvoice(InvoiceDto dto);

    /**
     * 删除开票申请
     * @param dto
     * @return
     */
    public void deleteInvoice(InvoiceDto dto);;

    /**
     * 受理开票申请
     * @param dto
     */
    public void acceptInvoice(InvoiceDto dto);

    /**
     * 上传发票
     * @param dto
     */
    public void uploadInvoice(InvoiceDto dto);
}
