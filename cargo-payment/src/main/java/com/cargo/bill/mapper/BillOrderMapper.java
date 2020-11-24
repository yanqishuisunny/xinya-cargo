package com.cargo.bill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.bill.dto.BillOrderListDto;
import com.cargo.bill.entity.BillOrderEntity;
import com.cargo.bill.vo.BillOrderSummaryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 帐单表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Repository
public interface BillOrderMapper extends BaseMapper<BillOrderEntity> {

    List<BillOrderSummaryVo> billOrderWayList(@Param("dto") BillOrderListDto dto);

    List<BillOrderSummaryVo> billOrderOwnerList(@Param("dto") BillOrderListDto dto);

}
