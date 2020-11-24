package com.cargo.car.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.car.dto.CarDriverDto;
import com.cargo.car.entity.CarDriverEntity;
import com.cargo.car.entity.CarEntity;
import com.cargo.car.mapper.CarDriverMapper;
import com.cargo.car.mapper.CarMapper;
import com.cargo.car.service.CarDriverService;
import com.cargo.car.service.CarService;
import com.cargo.car.vo.CarVo;
import com.cargo.user.entity.DriverInformationEntity;
import com.cargo.user.mapper.DriverInformationMapper;
import com.cargo.user.service.DriverInformationService;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 车辆与司机关系表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-11
 */
@Service
public class CarDriverServiceImpl extends ServiceImpl<CarDriverMapper, CarDriverEntity> implements CarDriverService {
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private DriverInformationMapper driverInformationMapper;
    @Autowired
    private CarDriverMapper carDriverMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(CarDriverDto dto) {
        CarEntity carEntity = carMapper.selectById(dto.getCarId());
        if(carEntity == null){
            throw new BussException("查不到对应车辆  carId:"+dto.getCarId());
        }
        dto.getListDriverIds().stream().forEach(driverId -> {
            DriverInformationEntity driverEntity = driverInformationMapper.selectById(driverId);
            if(driverEntity == null){
                throw new BussException("查不到对应司机  driverId:"+driverId);
            }
            CarDriverEntity carDriverEntity = new CarDriverEntity();
            carDriverEntity.setCarId(dto.getCarId());
            carDriverEntity.setCreateUser(ShiroUtil.getUserId());
            carDriverEntity.setInformationId(driverEntity.getInformationId());
            carDriverEntity.setIdCardName(driverEntity.getIdCardName());
            carDriverMapper.insert(carDriverEntity);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(CarDriverDto dto) {
        CarEntity carEntity = carMapper.selectById(dto.getCarId());
        if(carEntity == null){
            throw new BussException("查不到对应车辆  carId:"+dto.getCarId());
        }
        UpdateWrapper<CarDriverEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("car_id",dto.getCarId());
        updateWrapper.set("is_able",0);
        this.update(updateWrapper);
        dto.getListDriverIds().stream().forEach(driverId -> {
            DriverInformationEntity driverEntity = driverInformationMapper.selectById(driverId);
            if(driverEntity == null){
                throw new BussException("查不到对应司机  driverId:"+driverId);
            }
            CarDriverEntity carDriverEntity = new CarDriverEntity();
            carDriverEntity.setCarId(dto.getCarId());
            carDriverEntity.setCreateUser(ShiroUtil.getUserId());
            carDriverEntity.setInformationId(driverEntity.getInformationId());
            carDriverEntity.setIdCardName(driverEntity.getIdCardName());
            carDriverMapper.insert(carDriverEntity);
        });
        return true;
    }
}
