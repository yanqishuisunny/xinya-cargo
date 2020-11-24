package com.cargo.alipay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.dto.DispatchLogDto;
import com.cargo.entity.DispatchLog;
import com.cargo.mapper.DispatchLogMapper;
import com.cargo.alipay.service.DispatchLogService;
import com.cargo.vo.BannerCarVo;
import com.cargo.vo.DispatchLogVo;
import com.commom.core.BeanConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 调度单表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-27
 */
@Service
public class DispatchLogServiceImpl extends ServiceImpl<DispatchLogMapper, DispatchLog> implements DispatchLogService {

    /**
     * 司机的甘蔗图
     *
     * @param dispatchLogDto
     * @param orgId
     * @return
     */
    @Override
    public List<BannerCarVo> carBannerByDriver(DispatchLogDto dispatchLogDto, String orgId) {
        QueryWrapper<DispatchLog> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(dispatchLogDto.getDriverName())) {
            queryWrapper.like("driver_name",dispatchLogDto.getDriverName());
        }

        if (!ObjectUtils.isEmpty(dispatchLogDto.getSenderAreaTownId())) {
            queryWrapper.eq("sender_area_town_id",dispatchLogDto.getSenderAreaTownId());
        }
        if (!ObjectUtils.isEmpty(dispatchLogDto.getDeliveryAreaTownId())) {
            queryWrapper.eq("delivery_area_town_id",dispatchLogDto.getDeliveryAreaTownId());
        }
        if (!ObjectUtils.isEmpty(orgId)) {
            queryWrapper.eq("org_id",orgId);
        }
        List<DispatchLog> dispatchLogs = this.baseMapper.selectList(queryWrapper);
        List<BannerCarVo> list = new ArrayList<>();
        if (ObjectUtils.isEmpty(dispatchLogs)) {
            return list;
        }
        Map<String, List<DispatchLog>> collect = dispatchLogs.stream().collect(Collectors.groupingBy(DispatchLog::getDriverId));
        BannerCarVo bannerCarVo  ;
        for (String key: collect.keySet()){
            List<DispatchLog> dis = collect.get(key);
            if (!ObjectUtils.isEmpty(dis)) {
                bannerCarVo = new BannerCarVo();
                bannerCarVo.setDriverId(dis.get(0).getDriverId());
                bannerCarVo.setDriverName(dis.get(0).getDriverName());
                bannerCarVo.setDriverPhoneNo(dis.get(0).getDriverPhone());
                bannerCarVo.setDispatchLogVos(BeanConverter.convert(DispatchLogVo.class,dis));
                list.add(bannerCarVo);
            }
        }
        return list;
    }

    /**
     * 车辆的甘蔗图
     *
     * @param dispatchLogDto
     * @param orgId
     * @return
     */
    @Override
    public List<BannerCarVo> carBannerByCar(DispatchLogDto dispatchLogDto, String orgId) {
        QueryWrapper<DispatchLog> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(dispatchLogDto.getCarNo())) {
            queryWrapper.like("car_no",dispatchLogDto.getCarNo());
        }
        if (!ObjectUtils.isEmpty(dispatchLogDto.getSenderAreaTownId())) {
            queryWrapper.eq("sender_area_town_id",dispatchLogDto.getSenderAreaTownId());
        }
        if (!ObjectUtils.isEmpty(dispatchLogDto.getDeliveryAreaTownId())) {
            queryWrapper.eq("delivery_area_town_id",dispatchLogDto.getDeliveryAreaTownId());
        }
        if (!ObjectUtils.isEmpty(orgId)) {
            queryWrapper.eq("org_id",orgId);
        }
        List<DispatchLog> dispatchLogs = this.baseMapper.selectList(queryWrapper);
        List<BannerCarVo> list = new ArrayList<>();
        if (ObjectUtils.isEmpty(dispatchLogs)) {
            return list;
        }
        Map<String, List<DispatchLog>> collect = dispatchLogs.stream().collect(Collectors.groupingBy(DispatchLog::getCarId));
        BannerCarVo bannerCarVo  ;
        for (String key: collect.keySet()){
            List<DispatchLog> dis = collect.get(key);
            if (!ObjectUtils.isEmpty(dis)) {
                bannerCarVo = new BannerCarVo();
                bannerCarVo.setCarId(dis.get(0).getCarId());
                bannerCarVo.setCarNo(dis.get(0).getCarNo());
                bannerCarVo.setDispatchLogVos(BeanConverter.convert(DispatchLogVo.class,dis));
                list.add(bannerCarVo);
            }
        }
        return list;
    }
}
