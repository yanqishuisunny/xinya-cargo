package com.cargo.car.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.car.dto.CarDriverDto;
import com.cargo.car.entity.CarDriverEntity;

/**
 * <p>
 * 车辆与司机关系表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-11-11
 */
public interface CarDriverService extends IService<CarDriverEntity> {

    boolean add(CarDriverDto dto);

    boolean edit(CarDriverDto dto);
}
