package com.cargo.car.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.car.dto.CarDto;
import com.cargo.car.entity.CarEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.car.vo.CarVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
public interface CarService extends IService<CarEntity> {

    Page<CarVo> queryForList(Page<CarVo> page, CarDto dto);

    CarVo queryForOne(String carId);

    /**
     * 将车辆信息放入redis 中
     * @param orgId
     */
    void carMessageToRedis(String orgId);


    /**
     * 根据组织ID 和  企业ID  查找车辆
     * @param userId
     * @return
     */
    List<CarEntity> selectOneByOrgIdAndCompanyId(String userId);

    boolean updateStatus(CarDto dto);

    boolean updateByIds(List<CarDto> listDto);
}
