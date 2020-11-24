package com.cargo.car.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.car.entity.LineEntity;
import com.cargo.car.mapper.LineMapper;
import com.cargo.car.service.LineService;
import com.cargo.car.vo.CarVo;
import com.cargo.car.vo.LineVo;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class LineServiceImpl extends ServiceImpl<LineMapper, LineEntity> implements LineService {
    @Autowired
    private LineMapper lineMapper;

    @Override
    public Page<LineVo> queryForList(Page<LineVo> page) {
        List<LineVo> lineVos = lineMapper.queryForList(ShiroUtil.getUserId(), page);
        lineVos.stream().forEach(item ->{
            item.setSenderAdCode(item.getSenderAreaTownId());
            item.setDeliveryAdCode(item.getDeliveryAreaTownId());
        });
        return page.setRecords(lineVos);
    }

    @Override
    public LineVo queryForOne(String id) {
        LineVo vo = lineMapper.queryForOne(id);
        if(vo != null) {
            vo.setSenderAdCode(vo.getSenderAreaTownId());
            vo.setDeliveryAdCode(vo.getDeliveryAreaTownId());
        }
        return vo;
    }
}
