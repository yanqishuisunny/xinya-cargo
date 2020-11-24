package com.cargo.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.order.dto.ConsignorReleaseDto;
import com.cargo.order.entity.ConsignorReleaseEntity;
import com.cargo.order.vo.ConsignorReleaseVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */

public interface ConsignorReleaseService extends IService<ConsignorReleaseEntity> {

    boolean add(ConsignorReleaseDto dto);

    Page<ConsignorReleaseVo> queryForList(ConsignorReleaseDto dto, Page<ConsignorReleaseVo> page);

    ConsignorReleaseVo queryForOne(String consignorReleaseId);

    boolean updateByListIds(List<String> consignorReleaseIds);

    boolean updateConById(ConsignorReleaseDto entity);

    boolean updateStatus(ConsignorReleaseDto dto);
}
