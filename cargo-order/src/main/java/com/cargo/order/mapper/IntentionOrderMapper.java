package com.cargo.order.mapper;

import com.cargo.order.dto.IntentionOrderDto;
import com.cargo.order.entity.IntentionOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.order.vo.IntentionOrderVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-10-28
 */
@Repository
public interface IntentionOrderMapper extends BaseMapper<IntentionOrderEntity> {

    List<IntentionOrderVo> queryForList(@Param("dto") IntentionOrderDto dto);
}
