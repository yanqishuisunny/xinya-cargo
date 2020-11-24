package com.cargo.car.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.car.entity.CarTypeEntity;
import com.cargo.car.mapper.CarTypeMapper;
import com.cargo.car.service.CarTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Service
public class CarTypeServiceImpl extends ServiceImpl<CarTypeMapper, CarTypeEntity> implements CarTypeService {

}
