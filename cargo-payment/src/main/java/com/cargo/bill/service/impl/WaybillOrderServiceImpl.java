package com.cargo.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.bill.dto.WaybillOrderDto;
import com.cargo.bill.dto.WaybillOrderListDto;
import com.cargo.bill.entity.BillOrderEntity;
import com.cargo.bill.entity.WaybillOrderEntity;
import com.cargo.bill.mapper.BillOrderMapper;
import com.cargo.bill.mapper.WaybillOrderMapper;
import com.cargo.bill.service.WaybillOrderService;
import com.cargo.bill.vo.WaybillOrderVo;
import com.commom.core.BeanConverter;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 帐单扩展_运单(司机对账)表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-18
 */
@Service
public class WaybillOrderServiceImpl extends ServiceImpl<WaybillOrderMapper, WaybillOrderEntity> implements WaybillOrderService {

    @Autowired
    WaybillOrderMapper waybillOrderMapper;
    @Autowired
    BillOrderMapper billOrderMapper;

    @Override
    public IPage<WaybillOrderVo> waybillList(WaybillOrderListDto dto, Page<WaybillOrderEntity> page) {
        String orgId = ShiroUtil.getOrgId();

        QueryWrapper<WaybillOrderEntity> wrapper = new QueryWrapper<>();
        if(dto.getCarId()!=null && !dto.getCarId().isEmpty()){
            wrapper.eq("car_id",dto.getCarId());
        }
        if(dto.getDriverId()!=null&&!dto.getDriverId().isEmpty()){
            wrapper.eq("driver_id",dto.getDriverId());
        }
        if (dto.getWaybillPayStatus()!=null && !dto.getWaybillPayStatus().isEmpty()) {
            wrapper.eq("waybill_pay_status",dto.getWaybillPayStatus());
        }
        if(dto.getStartTime()!=null&&!dto.getStartTime().isEmpty()){
            wrapper.ge("actual_time",dto.getStartTime());
        }
        if(dto.getEndTime()!=null&&!dto.getEndTime().isEmpty()){
            wrapper.le("actual_time",dto.getEndTime());
        }
        wrapper.eq("is_able",1);
        IPage<WaybillOrderEntity> waybillOrderEntityIPage = waybillOrderMapper.selectPage(page, wrapper);

        List<WaybillOrderEntity> list = new ArrayList<>();

        List<WaybillOrderEntity> records = waybillOrderEntityIPage.getRecords();
        for (WaybillOrderEntity record : records) {
            String billOrderId = record.getBillOrderId();
            BillOrderEntity billOrderEntity = billOrderMapper.selectById(billOrderId);
            String orgId1 = billOrderEntity.getOrgId();
            if(orgId1.equals(orgId)){

                if(billOrderEntity!=null&&billOrderEntity.getOrderNo()!=null){
                    record.setOrderNo(billOrderEntity.getOrderNo());
                }
                list.add(record);
            }
        }
        waybillOrderEntityIPage.setRecords(list);
        IPage<WaybillOrderVo> convert = BeanConverter.convert(WaybillOrderVo.class, waybillOrderEntityIPage);

        return convert;
    }

    @Override
    public void confirmWayBill(WaybillOrderDto dto) {
        String waybillOrderId = dto.getWaybillOrderId();

        WaybillOrderEntity waybillOrderEntity = waybillOrderMapper.selectById(waybillOrderId);

        waybillOrderEntity.setWaybillPayStatus(dto.getWaybillPayStatus());
        waybillOrderMapper.updateById(waybillOrderEntity);


    }

    @Override
    public void payWayBill(WaybillOrderDto dto) {
        String userId = ShiroUtil.currentUserId();
        String waybillOrderId = dto.getWaybillOrderId();
        String examineUser = dto.getExamineUser();

        WaybillOrderEntity waybillOrderEntity = waybillOrderMapper.selectById(waybillOrderId);

        waybillOrderEntity.setWaybillPayStatus(dto.getWaybillPayStatus());
        waybillOrderEntity.setAmountPaid(dto.getAmountPaid());
        waybillOrderEntity.setPaymentTime(dto.getPaymentTime());
        waybillOrderEntity.setExamineUserId(userId);
        waybillOrderEntity.setExamineUser(examineUser);


        waybillOrderMapper.updateById(waybillOrderEntity);
    }
}
