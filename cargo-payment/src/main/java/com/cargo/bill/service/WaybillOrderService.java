package com.cargo.bill.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.bill.dto.WaybillOrderDto;
import com.cargo.bill.dto.WaybillOrderListDto;
import com.cargo.bill.entity.WaybillOrderEntity;
import com.cargo.bill.vo.WaybillOrderVo;

/**
 * <p>
 * 帐单扩展_运单(司机对账)表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-18
 */
public interface WaybillOrderService extends IService<WaybillOrderEntity> {

    IPage<WaybillOrderVo> waybillList(WaybillOrderListDto dto, Page<WaybillOrderEntity> page);

    void confirmWayBill(WaybillOrderDto dto);

    void payWayBill(WaybillOrderDto dto);
}
