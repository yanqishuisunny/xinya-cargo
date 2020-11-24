package com.cargo.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.feign.entity.AreCodeEntry;
import com.cargo.feign.service.BaseFeignService;
import com.cargo.order.dto.CarrierCarsDto;
import com.cargo.order.dto.CarrierReleaseDto;
import com.cargo.order.entity.*;
import com.cargo.order.entity.CarrierReleaseEntity;
import com.cargo.order.mapper.CarrierCarsMapper;
import com.cargo.order.mapper.CarrierReleaseMapper;
import com.cargo.order.service.CarrierCarsService;
import com.cargo.order.service.CarrierReleaseService;
import com.cargo.order.vo.CarrierCarsVo;
import com.cargo.order.vo.CarrierReleaseVo;
import com.commom.cache.Constant;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-03
 */
@Service
public class CarrierReleaseServiceImpl extends ServiceImpl<CarrierReleaseMapper, CarrierReleaseEntity> implements CarrierReleaseService {
    @Autowired
    private CarrierReleaseMapper carrierReleaseMapper;
    @Autowired
    private BaseFeignService baseFeignService;
    @Autowired
    private CarrierCarsMapper carrierCarsMapper;
    @Autowired
    private CarrierCarsService carrierCarsService;

    @Override
    public Page<CarrierReleaseVo> queryForList(CarrierReleaseDto dto, Page<CarrierReleaseVo> page) {
        List<CarrierReleaseVo> list = this.baseMapper.queryForList(dto,page);
        if(list.size() != 0){
            list = baseFeignService.converCarrierlist(list);
        }
        page.setRecords(list);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(CarrierReleaseDto dto) {
        CarrierReleaseEntity carrierReleaseEntity = new CarrierReleaseEntity();
        BeanUtils.copyProperties(dto,carrierReleaseEntity);
        carrierReleaseEntity.setCreateUser(ShiroUtil.getUserId());
        carrierReleaseEntity.setStatus(CarrierReleaseEntity.STATUS_CREATE);

        converArea(carrierReleaseEntity , dto.getCarrierSenderAdCode(), dto.getCarrierDeliveryAdCode());

        carrierReleaseMapper.insert(carrierReleaseEntity);
        QueryWrapper<CarrierReleaseEntity> qw = new QueryWrapper<>();
        qw.eq("carrier_release_id",carrierReleaseEntity.getCarrierReleaseId());

        CarrierReleaseEntity newEntity = carrierReleaseMapper.selectById(carrierReleaseEntity.getCarrierReleaseId());
        if(newEntity == null){
            throw new BussException("货主信息发布 新增失败");
        }

        List<CarrierCarsDto> listCarrierCars = dto.getListCarrierCars();
        if(StringUtils.isNotEmpty(dto.getCarId())){
            listCarrierCars = new ArrayList<>();
            CarrierCarsDto carrierCarsDto = new CarrierCarsDto();
            carrierCarsDto.setCarId(dto.getCarId());
            listCarrierCars.add(carrierCarsDto);
        }
        listCarrierCars.stream().forEach(item ->{
            CarrierCarsEntity entity = new CarrierCarsEntity();
            BeanUtils.copyProperties(item,entity);

            entity.setCarrierReleaseId(carrierReleaseEntity.getCarrierReleaseId());
            entity.setCarId(item.getCarId());
            entity.setCreateUser(ShiroUtil.getUserId());
            carrierCarsMapper.insert(entity);

            CarrierCarsEntity carrierCarsEntity = carrierCarsMapper.selectById(entity.getCarrierCarsId());
            if(carrierCarsEntity == null){
                throw new BussException("货主信息发布 货物新增失败");
            }
        });
        return true;
    }

    @Override
    public CarrierReleaseVo queryForOne(String carrierReleaseId) {
        CarrierReleaseVo vo = carrierReleaseMapper.queryForOne(carrierReleaseId);
        if(vo == null){
            throw new BussException("找不到对应carrierReleaseId :"+carrierReleaseId +"  数据");
        }
        QueryWrapper<CarrierCarsEntity> qw = new QueryWrapper<>();
        qw.eq("carrier_release_id",carrierReleaseId);
        List<CarrierCarsEntity> carrierCarsEntities = carrierCarsMapper.selectList(qw);
        List<CarrierCarsVo> listVo =  new ArrayList<>();
        carrierCarsEntities.stream().forEach(item ->{
            CarrierCarsVo carrierCarsVo = new CarrierCarsVo();
            BeanUtils.copyProperties(item,carrierCarsVo);
            listVo.add(carrierCarsVo);
        });
        vo.setCarrierCarsVoList(listVo);
        List<CarrierReleaseVo> list = new ArrayList<>();
        vo.setCarrierSenderAdCode(vo.getLineSenderAreaTownId());
        vo.setCarrierDeliveryAdCode(vo.getLineDeliveryAreaTownId());
        list.add(vo);
        return baseFeignService.converCarrierlist(list).get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateByListIds(List<String> carrierReleaseIds) {
        UpdateWrapper<CarrierReleaseEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("carrier_release_id",carrierReleaseIds);
        updateWrapper.set("is_able", Constant.AbleEnum.NO.getValue());
        this.update(updateWrapper);

        UpdateWrapper<CarrierCarsEntity> updateCars = new UpdateWrapper<>();
        updateCars.in("carrier_release_id",carrierReleaseIds);
        updateCars.set("is_able", Constant.AbleEnum.NO.getValue());
        carrierCarsService.update(updateCars);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCaById(CarrierReleaseDto dto) {
        CarrierReleaseEntity entity = new CarrierReleaseEntity();
        BeanUtils.copyProperties(dto,entity);
        converArea(entity , dto.getCarrierSenderAdCode(), dto.getCarrierDeliveryAdCode());
        int i = carrierReleaseMapper.updateById(entity);
        if(i == 0){
            throw new BussException("更新失败");
        }
        carrierCarsMapper.updateAbleByRelId(entity.getCarrierReleaseId());
        List<CarrierCarsDto> listCarrierCars = dto.getListCarrierCars();

        listCarrierCars.stream().forEach(item ->{
            CarrierCarsEntity carEntity = new CarrierCarsEntity();
            BeanUtils.copyProperties(item,carEntity);
            carEntity.setCarrierReleaseId(entity.getCarrierReleaseId());
            carEntity.setCarId(item.getCarId());
            carEntity.setCreateUser(ShiroUtil.getUserId());
            carrierCarsMapper.insert(carEntity);
            CarrierCarsEntity carrierCarsEntity = carrierCarsMapper.selectById(carEntity.getCarrierCarsId());
            if(carrierCarsEntity == null){
                throw new BussException("货主信息发布 货物新增失败");
            }
        });
        return true;
    }


    void converArea(CarrierReleaseEntity carrierReleaseEntity , String carrierSenderAdCode, String carrierDeliveryAdCode){
        //根据区域编码 去获取出发地相应的区域名称
        AreCodeEntry senderByAdCode = baseFeignService.findByAdCode(carrierSenderAdCode);
        if (!ObjectUtils.isEmpty(senderByAdCode)) {
            //市
            carrierReleaseEntity.setLineSenderAreaCityId(senderByAdCode.getCityCode());
            carrierReleaseEntity.setLineSenderAreaCityName(senderByAdCode.getCity());
            //区
            carrierReleaseEntity.setLineSenderAreaTownId(senderByAdCode.getAreaCode());
            carrierReleaseEntity.setLineSenderAreaTownName(senderByAdCode.getArea());
            //省
            carrierReleaseEntity.setLineSenderAreaProvinceId(senderByAdCode.getProvinceCode());
            carrierReleaseEntity.setLineSenderAreaProvinceName(senderByAdCode.getProvince());
        }

        //根据区域编码 去获取目的地相应的区域名称
        AreCodeEntry deliveryByAdCode = baseFeignService.findByAdCode(carrierDeliveryAdCode);
        if (!ObjectUtils.isEmpty(deliveryByAdCode)) {
            //市
            carrierReleaseEntity.setLineDeliveryAreaCityId(deliveryByAdCode.getCityCode());
            carrierReleaseEntity.setLineDeliveryAreaCityName(deliveryByAdCode.getCity());
            //区
            carrierReleaseEntity.setLineDeliveryAreaTownId(deliveryByAdCode.getAreaCode());
            carrierReleaseEntity.setLineDeliveryAreaTownName(deliveryByAdCode.getArea());
            //省
            carrierReleaseEntity.setLineDeliveryAreaProvinceId(deliveryByAdCode.getProvinceCode());
            carrierReleaseEntity.setLineDeliveryAreaProvinceName(deliveryByAdCode.getProvince());
        }
    }

}
