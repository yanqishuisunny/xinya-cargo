package com.cargo.waybill.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.feign.service.BaseFeignService;
import com.cargo.order.vo.ConsignorReleaseVo;
import com.cargo.waybill.dto.WaybillDto;
import com.cargo.waybill.entity.WaybillEntity;
import com.cargo.waybill.entity.WaybillStatusLogEntity;
import com.cargo.waybill.mapper.WaybillMapper;
import com.cargo.waybill.mapper.WaybillStatusLogMapper;
import com.cargo.waybill.service.WaybillService;
import com.cargo.waybill.vo.WaybillVo;
import com.commom.core.BeanConverter;
import com.commom.shiro.ShiroUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 * 运单表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
@Service
public class WaybillServiceImpl extends ServiceImpl<WaybillMapper, WaybillEntity> implements WaybillService {
    @Autowired
    private WaybillMapper waybillMapper;
    @Autowired
    private WaybillStatusLogMapper waybillStatusLogMapper;
    @Autowired
    private BaseFeignService baseFeignService;

    @Override
    public Page<WaybillVo> queryForlist(WaybillDto dto, Page<WaybillVo> page) {
        dto.setCurrentUserId(ShiroUtil.getUserId() );
        if(StringUtils.isNotEmpty(dto.getWayBillTabStatus())){
            dto.setListStatus(new ArrayList<Integer>());
            if(dto.getWayBillTabStatus().equals("0")){
                dto.getListStatus().add(0);
            }else if(dto.getWayBillTabStatus().equals("1")){
                dto.getListStatus().add(1);
                dto.getListStatus().add(2);
                dto.getListStatus().add(3);
                dto.getListStatus().add(4);
                dto.getListStatus().add(5);
            }else if(dto.getWayBillTabStatus().equals("2")){
                dto.getListStatus().add(6);
            }
        }
        return page.setRecords(waybillMapper.queryForlist(dto,page));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBill(WaybillDto t) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        WaybillEntity convert = BeanConverter.convert(WaybillEntity.class, t);
        //baseFeignService.
        this.save(convert);
        //创建运单日志
        WaybillStatusLogEntity waybillStatusLogEntity = new WaybillStatusLogEntity();
        waybillStatusLogEntity.setWaybillId(convert.getWaybillId());
        waybillStatusLogEntity.setWaybillNo(convert.getWaybillNo());
        waybillStatusLogEntity.setStatus(convert.getWaybillStatus());
        waybillStatusLogEntity.setTrackingTime(format.format(new Date()));
        waybillStatusLogMapper.insert(waybillStatusLogEntity);
        return false;
    }
}
