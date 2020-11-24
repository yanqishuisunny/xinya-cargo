package com.cargo.waybill.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.order.vo.ConsignorReleaseVo;
import com.cargo.waybill.dto.WaybillDto;
import com.cargo.waybill.entity.WaybillEntity;
import com.cargo.waybill.vo.WaybillVo;

/**
 * <p>
 * 运单表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
public interface WaybillService extends IService<WaybillEntity> {

    Page<WaybillVo> queryForlist(WaybillDto dto, Page<WaybillVo> page);

    boolean addBill(WaybillDto t);
}
