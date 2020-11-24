package com.cargo.bill.service;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.bill.dto.BillOrderDto;
import com.cargo.bill.dto.BillOrderListDto;
import com.cargo.bill.dto.BillOrderListOwnerDto;
import com.cargo.bill.entity.BillOrderEntity;
import com.cargo.bill.vo.AliPayBillOrderVo;
import com.cargo.bill.vo.BillOrderAnalyseVo;
import com.cargo.bill.vo.BillOrderVo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 帐单表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
public interface BillOrderService extends IService<BillOrderEntity> {

    public void addBillOrder(BillOrderDto dto);

    IPage<BillOrderVo> billOrderList(BillOrderListDto dto, Page<BillOrderEntity> page);

    public void examineBillOrder(BillOrderDto dto);

    public AliPayBillOrderVo payBillOrder(BillOrderDto dto) throws Exception;

    public void mergeBillOrder(List<String> billOrderIds);

    IPage<BillOrderVo> billOrderListOwner(BillOrderListOwnerDto dto,Page<BillOrderEntity> page);

    public void pressBillOrder(BillOrderDto dto);

    BillOrderAnalyseVo analysisBillOrder(BillOrderListDto dto) throws ParseException;
}
