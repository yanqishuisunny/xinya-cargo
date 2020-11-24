package com.cargo.order.mapper;

import com.cargo.order.dto.GeneralOrderDto;
import com.cargo.order.entity.GeneralOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.order.vo.GeneralOrderDetailVo;
import com.cargo.order.vo.GeneralOrderVo;
import com.cargo.order.vo.GeneralOrderWayBillVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-11-09
 */
@Repository
public interface GeneralOrderMapper extends BaseMapper<GeneralOrderEntity> {


    List<GeneralOrderVo> queryForList(@Param("dto") GeneralOrderDto dto);

    GeneralOrderVo queryForOne(@Param("id") String generalOrderId);

    /**
     * 根据订单id 查询订单详情
     * @param t
     * @return
     */
    List<GeneralOrderDetailVo> selectDetails(@Param("t") List<String> t);


    List<GeneralOrderWayBillVo> getWayBillByGenId(GeneralOrderDto dto);


}
