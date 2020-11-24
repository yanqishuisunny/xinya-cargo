package com.cargo.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.order.dto.ConsignorReleaseDto;
import com.cargo.order.entity.ConsignorReleaseEntity;
import com.cargo.order.entity.GoodsEntity;
import com.cargo.order.vo.ConsignorReleaseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Repository
public interface ConsignorReleaseMapper extends BaseMapper<ConsignorReleaseEntity> {

    List<ConsignorReleaseVo> queryForList(@Param("conRel") ConsignorReleaseDto dto, Page<ConsignorReleaseVo> page);

    ConsignorReleaseVo queryForOne(@Param("id") String consignorReleaseId);

    List<GoodsEntity> getGoodsId(@Param("id") String consignor_release_id);

    boolean updateByListIds(@Param("ids")List<String> goodIds);
}
