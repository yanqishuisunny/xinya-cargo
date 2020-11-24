package com.cargo.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.order.dto.CarrierReleaseDto;
import com.cargo.order.dto.ConsignorReleaseDto;
import com.cargo.order.entity.CarrierReleaseEntity;
import com.cargo.order.vo.CarrierReleaseVo;
import com.cargo.order.vo.ConsignorReleaseVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-11-03
 */
public interface CarrierReleaseService extends IService<CarrierReleaseEntity> {

    Page<CarrierReleaseVo> queryForList(CarrierReleaseDto dto, Page<CarrierReleaseVo> page);


    boolean add(CarrierReleaseDto dto);

    CarrierReleaseVo queryForOne(String carrierReleaseId);

    boolean updateByListIds(List<String> carrierReleaseIds);

    boolean updateCaById(CarrierReleaseDto dto);
}
