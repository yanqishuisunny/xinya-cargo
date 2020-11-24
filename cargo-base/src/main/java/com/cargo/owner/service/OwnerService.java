package com.cargo.owner.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.owner.dto.OwnerDto;
import com.cargo.owner.entity.OwnerEntity;
import com.cargo.owner.vo.OwnerVo;


/**
 * <p>
 * 组织扩展表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
public interface OwnerService extends IService<OwnerEntity> {

    Page<OwnerVo> queryForExamineList(OwnerDto dto, Page<OwnerVo> page);

    boolean editStatus(OwnerDto dto);
}
