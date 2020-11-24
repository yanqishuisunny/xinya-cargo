package com.cargo.point.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.point.dto.PointAccountDto;
import com.cargo.point.entity.PointAccountEntity;
import com.cargo.point.vo.PointAccountVo;

/**
 * <p>
 * 积分表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
public interface PointAccountService extends IService<PointAccountEntity> {

    IPage<PointAccountVo> pointRunningList(PointAccountDto dto, Page<PointAccountEntity> page);
}
