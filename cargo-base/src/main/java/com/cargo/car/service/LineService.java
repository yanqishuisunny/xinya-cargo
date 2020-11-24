package com.cargo.car.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.car.entity.LineEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.car.vo.CarVo;
import com.cargo.car.vo.LineVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-11-02
 */
public interface LineService extends IService<LineEntity> {

    Page<LineVo> queryForList(Page<LineVo> page);

    LineVo queryForOne(String id);
}
