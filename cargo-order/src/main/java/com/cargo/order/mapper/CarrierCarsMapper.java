package com.cargo.order.mapper;

import com.cargo.order.entity.CarrierCarsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-11-04
 */
@Repository
public interface CarrierCarsMapper extends BaseMapper<CarrierCarsEntity> {

    int updateAbleByRelId(@Param("carrierReleaseId") String carrierReleaseId);
}
