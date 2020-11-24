package com.cargo.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.order.dto.IntentionOrderDto;
import com.cargo.order.entity.IntentionOrderEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.order.vo.IntentionOrderVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-10-28
 */
public interface IntentionOrderService extends IService<IntentionOrderEntity> {

    Page<IntentionOrderVo> queryForList(IntentionOrderDto dto, Page<IntentionOrderVo> page);

    IntentionOrderVo queryForOne(String intentionOrderId);

    boolean saveIntentionOrder(IntentionOrderDto dto);

    boolean updateByIds(List<String> ids);

    boolean updateStatus(IntentionOrderDto dto);
}
