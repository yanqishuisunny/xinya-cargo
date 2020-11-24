package com.cargo.car.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.car.dto.CarDto;
import com.cargo.car.entity.*;
import com.cargo.car.mapper.*;
import com.cargo.car.service.CarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.car.vo.CarMessageVo;
import com.cargo.car.vo.CarVo;
import com.cargo.examineLog.entity.ExamineLogEntity;
import com.cargo.examineLog.mapper.ExamineLogMapper;
import com.cargo.examineLog.service.ExamineLogService;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, CarEntity> implements CarService {
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CarTypeMapper carTypeMapper;
    @Autowired
    private CarSizeMapper carSizeMapper;
    @Autowired
    private CarEnergyTypeMapper carEnergyTypeMapper;
    @Autowired
    private CarCardTypeMapper carCardTypeMapper;
    @Autowired
    private ExamineLogMapper examineLogMapper;

    @Override
    public Page<CarVo> queryForList(Page<CarVo> page, CarDto dto) {
        List<CarVo> carVoPage = carMapper.queryForList(dto, page);
        carVoPage.stream().forEach(item ->{
            conver(item);
        });
        page.setRecords(carVoPage);
        return page;
    }

    @Override
    public CarVo queryForOne(String carId) {
        CarEntity carEntity = carMapper.selectById(carId);
        CarVo vo = new CarVo();
        BeanUtils.copyProperties(carEntity,vo);
        conver(vo);
        return vo;
    }
    /**
     * 将车辆信息放入redis 中
     *
     * @param orgId
     */
    @Override
    public void carMessageToRedis(String orgId) {
        //清空redis中的信息
        RedisUtil.del("carMessage:"+orgId);
        //找到这个公司下自己的车和合作的车然后放入redis中
        List<CarMessageVo> list = carMapper.carMessageToRedis(orgId);
        String s = JSON.toJSONString(list);
        RedisUtil.set("carMessage:"+orgId,s,86400);
    }
    public void conver(CarVo item){
        //车牌类型
        CarCardTypeEntity carCardTypeEntity = carCardTypeMapper.selectById(item.getCarCardTypeId());
        item.setCarCardTypeName(carCardTypeEntity==null ? "" : carCardTypeEntity.getCarCardTypeName());

        //能源类型
        CarEnergyTypeEntity carEnergyTypeEntity = carEnergyTypeMapper.selectById(item.getCarEnergyTypeId());
        item.setCarEnergyTypeName(carEnergyTypeEntity==null ? "" : carEnergyTypeEntity.getCarEnergyTypeName());

        //车辆尺寸
        CarSizeEntity carSizeEntity = carSizeMapper.selectById(item.getCarSizeId());
        item.setCarSizeName(carSizeEntity==null ? "" : carSizeEntity.getCarSizeName());

        //车辆类型
        CarTypeEntity carTypeEntity = carTypeMapper.selectById(item.getCarTypeId());
        item.setCarTypeName(carTypeEntity==null ? "" : carTypeEntity.getCarTypeName());
    };



    @Override
    public List<CarEntity> selectOneByOrgIdAndCompanyId(String userId) {
        QueryWrapper<CarEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("create_user", userId);
        return this.list(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(CarDto dto) {
        CarEntity oldCar = carMapper.selectById(dto.getCarId());
        if(oldCar == null){
            throw new BussException("查不到对应车辆ID："+dto.getCarId());
        }

        UpdateWrapper<CarEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("car_id",dto.getCarId());
        updateWrapper.set("audit_status",dto.getAuditStatus());
        updateWrapper.set("update_user",ShiroUtil.getUserId());
        if(StringUtils.isNotEmpty(dto.getCheckRemark())){
            updateWrapper.set("check_remark",dto.getCheckRemark());
        }
        boolean update = this.update(updateWrapper);
        if(!update){
            throw new BussException("更新失敗");
        }
        ExamineLogEntity logEntity = new ExamineLogEntity();
        logEntity.setCheckRemark(dto.getCheckRemark());
        logEntity.setType(ExamineLogEntity.TYPE_1);
        logEntity.setCreateUser(ShiroUtil.getUserId());
        logEntity.setOldStatus(oldCar.getAuditStatus());
        logEntity.setNewStatus(dto.getAuditStatus());
        logEntity.setSourceId(dto.getCarId());
        int insert = examineLogMapper.insert(logEntity);
        if(insert != 1){
            throw new BussException("新增日志失敗");
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIds(List<CarDto> listDto) {
        listDto.stream().forEach(item ->{
            CarEntity entity = new CarEntity();
            BeanUtils.copyProperties(item,entity);
            entity.setUpdateUser(ShiroUtil.getUserId());
            this.updateById(entity);
        });
        return true;
    }

}
