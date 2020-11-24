package com.cargo.waybill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.order.vo.ConsignorReleaseVo;
import com.cargo.waybill.dto.WaybillDto;
import com.cargo.waybill.entity.WaybillEntity;
import com.cargo.waybill.vo.WaybillVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 运单表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
@Repository
public interface WaybillMapper extends BaseMapper<WaybillEntity> {

    List<WaybillVo> queryForlist(@Param("dto") WaybillDto dto, Page<WaybillVo> page);
}
