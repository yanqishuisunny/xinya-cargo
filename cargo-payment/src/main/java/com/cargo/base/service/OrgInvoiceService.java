package com.cargo.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.base.dto.OrgInvoiceDto;
import com.cargo.base.entity.OrgInvoiceEntity;
import com.cargo.base.vo.OrgInvoiceVo;

import java.util.List;

/**
 * @Auther: xinzs
 * @Date: 2020/11/16 17:29
 */
public interface OrgInvoiceService extends IService<OrgInvoiceEntity> {

    public void addOrgInvoice(OrgInvoiceDto dto);

    public void updateOrgInvoice(OrgInvoiceDto dto);

    OrgInvoiceVo orgInvoiceDetail(OrgInvoiceDto dto);

    IPage<OrgInvoiceVo> orgInvoiceList(OrgInvoiceDto dto, Page<OrgInvoiceEntity> page);
}
