package com.cargo.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.order.dto.CarrierReleaseDto;
import com.cargo.order.entity.CarEntity;
import com.cargo.order.entity.CarrierReleaseEntity;
import com.cargo.order.entity.GoodsEntity;
import com.cargo.order.vo.CarVo;
import com.cargo.order.vo.CarrierReleaseVo;
import com.cargo.order.vo.ConsignorReleaseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-11-03
 */
@Repository
public interface CarrierReleaseMapper extends BaseMapper<CarrierReleaseEntity> {

    List<CarrierReleaseVo> queryForList(@Param("carRel") CarrierReleaseDto dto, Page<CarrierReleaseVo> page);

    CarrierReleaseVo queryForOne(@Param("id") String carrierReleaseId);

    boolean updateByListIds(@Param("ids")List<String> goodIds);

    List<CarVo> getCars(@Param("carrierReleaseId")String carrierReleaseId, @Param("carTypeId")String carTypeId);
}
