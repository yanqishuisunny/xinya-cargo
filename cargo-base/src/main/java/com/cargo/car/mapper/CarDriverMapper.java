package com.cargo.car.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.car.entity.CarDriverEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 车辆与司机关系表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-11-11
 */
@Repository
public interface CarDriverMapper extends BaseMapper<CarDriverEntity> {

}
