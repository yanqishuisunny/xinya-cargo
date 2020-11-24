package com.cargo.carrier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.carrier.dto.CarrierDto;
import com.cargo.carrier.entity.CarrierEntity;
import com.cargo.carrier.vo.CarrierVo;
import com.cargo.carrier.vo.CarrierdetailVo;

/**
 * <p>
 * 承运商信息表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-05
 */
public interface CarrierService extends IService<CarrierEntity> {

    /**
     * 创建承运商
     * @param dto
     */
    public String addCarrier(CarrierDto dto);

    /**
     * 更新承运商
     * @param dto
     */
    public void updateCarrier(CarrierDto dto);

    /**
     * 查询承运商详情
     * @param id（企业ID）
     * @return
     */
    public CarrierdetailVo selectCarrierById(String id);

    /**
     * 查询承运商详情
     * @param id(角色ID)
     * @return
     */
    public CarrierdetailVo selectCarrierByRoleId(String id);
}
