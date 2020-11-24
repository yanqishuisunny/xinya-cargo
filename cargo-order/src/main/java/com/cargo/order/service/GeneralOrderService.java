package com.cargo.order.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.order.dto.GeneralOrderDto;
import com.cargo.order.entity.GeneralOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.order.vo.GeneralOrderDetailVo;
import com.cargo.order.vo.GeneralOrderVo;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-11-09
 */
public interface GeneralOrderService extends IService<GeneralOrderEntity> {

    Page<GeneralOrderVo> queryForList(GeneralOrderDto dto, Page<GeneralOrderVo> page);

    boolean add(GeneralOrderDto dto);

    GeneralOrderVo queryForOne(String generalOrderId);

    boolean updateStatus(GeneralOrderDto dto);

    /**
     * 根据订单id 查询订单详情
     * @param t
     * @return
     */
    List<GeneralOrderDetailVo> selectDetails(List<String> t);

    boolean getWayBillByGenId(GeneralOrderDto dto);

    JSONObject orderCensus(GeneralOrderDto dto);
}
