package com.cargo.alipay.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.dto.CarDetailDto;
import com.cargo.dto.DispatchDto;
import com.cargo.dto.WaybillDto;
import com.cargo.entity.DispatchLog;
import com.cargo.entity.GeneralOrderEntity;
import com.cargo.entity.UserInfoEntity;
import com.cargo.entity.WaybillEntity;
import com.cargo.feign.service.FeignOrderService;
import com.cargo.feign.service.FeignService;
import com.cargo.alipay.service.DispatchLogService;
import com.cargo.alipay.service.DispatchService;
import com.cargo.vo.DriverMessageVo;
import com.commom.exception.BussException;
import com.commom.snowflake.SnowflakeIdWorker;
import com.commom.utils.DateUtil;
import com.commom.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DispatchServiceImpl implements DispatchService {
    @Autowired
    FeignService feignService;

    @Autowired
    DispatchLogService dispatchLogService;

    @Autowired
    FeignOrderService feignOrderService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    /**
     * 获取车辆
     *
     * @param
     * @return
     */
    @Override
    public void getCar(String orgId) {
     //根据当前登录用户  获取当前的公司下的车辆信息
        String s = feignService.carMessageToRedis("1111111111");
        System.out.println(s);
    }

    /**
     * 获取司机
     *
     * @param
     * @return
     */
    @Override
    public List<DriverMessageVo> getDriver(String orgId) {
        List<DriverMessageVo> driverMessageVos = new ArrayList<>();
        //根据当前登录用户  获取当前的公司下的司机信息
        Object o = RedisUtil.get("driverMessage:" + orgId);
        if (!ObjectUtils.isEmpty(o)) {
            driverMessageVos = JSONArray.parseArray(o.toString(), DriverMessageVo.class);
        }
        return driverMessageVos;
    }


    /**
     * app端调度
     *
     * @param dispatchDto
     * @param userById
     */
    @Override
    public void addWayBillByApp(DispatchDto dispatchDto, UserInfoEntity userById) {

        //先去找找看看有没有这个订单 todo 找订单接口
        GeneralOrderEntity generalOrderEntity = feignOrderService.orderDetail(dispatchDto.getGeneralOrderId());
        if (ObjectUtils.isEmpty(generalOrderEntity)) {
            throw new BussException("订单不存在！");
        }
        List<CarDetailDto> waybillEntities = dispatchDto.getWaybillEntities();
        if (ObjectUtils.isEmpty(waybillEntities)) {
            return;
        }
        List<WaybillDto> list = new ArrayList<>();
        List<DispatchLog> dispatchLogs = new ArrayList<>();
        List<String> waybillIds = new ArrayList<>();
        WaybillDto waybillEntity ;
        for (CarDetailDto carDetailDto:waybillEntities){
            waybillEntity = new WaybillDto();
            BeanUtils.copyProperties(generalOrderEntity,waybillEntity);
            if (ObjectUtils.isEmpty(carDetailDto.getWaybillId())) {
                waybillEntity.setWaybillId(String.valueOf(SnowflakeIdWorker.generateId()));
                waybillEntity.setWaybillNo(getGeneralOrderNo());
                waybillEntity.setType(1);
                waybillEntity.setCarId(carDetailDto.getCarId());
                waybillEntity.setCarNo(carDetailDto.getCarNo());
                waybillEntity.setHangId(carDetailDto.getHangId());
                waybillEntity.setHangNo(carDetailDto.getHangNo());
                waybillEntity.setDriverId(carDetailDto.getDriverId());
                waybillEntity.setDriverName(carDetailDto.getDriverName());
                waybillEntity.setDriverPhoneNo(carDetailDto.getDriverPhoneNo());
               waybillEntity.setCreateUser(userById.getUserId());
                waybillEntity.setOrgId(userById.getOrgId());
                waybillEntity.setEstimatedTime(carDetailDto.getEstimatedTime());
                waybillEntity.setEstimatTime(carDetailDto.getEstimatTime());
               waybillEntity.setComments(carDetailDto.getRemark());
                list.add(waybillEntity);
            }else {
                //等于2 的时候是修改操作  原来的数据都有了
                waybillEntity.setWaybillId(carDetailDto.getWaybillId());
                waybillEntity.setWaybillNo(carDetailDto.getWaybillNo());
                waybillEntity.setType(2);
                waybillEntity.setCarId(carDetailDto.getCarId());
                waybillEntity.setCarNo(carDetailDto.getCarNo());
                waybillEntity.setHangId(carDetailDto.getHangId());
                waybillEntity.setHangNo(carDetailDto.getHangNo());
                waybillEntity.setDriverId(carDetailDto.getDriverId());
                waybillEntity.setDriverName(carDetailDto.getDriverName());
                waybillEntity.setDriverPhoneNo(carDetailDto.getDriverPhoneNo());
                waybillEntity.setCreateUser(userById.getUserId());
                waybillEntity.setOrgId(userById.getOrgId());
                waybillEntity.setEstimatedTime(carDetailDto.getEstimatedTime());
                waybillEntity.setEstimatTime(carDetailDto.getEstimatTime());
                waybillEntity.setComments(carDetailDto.getRemark());
                list.add(waybillEntity);
                waybillIds.add(carDetailDto.getWaybillId());
            }
            //创建调度单日志
            DispatchLog dispatchLog = addDispatchLog(waybillEntity, userById);
            dispatchLogs.add(dispatchLog);

        }
        try{
            feignOrderService.addList(list);
        }catch (Exception e){
            log.error("链接订单服务创建运单失败");
            throw new BussException("调度失败！");
        }
        //删除掉原来订单的状态
        if (!ObjectUtils.isEmpty(waybillIds)) {
            QueryWrapper<DispatchLog> dispatchLogQueryWrapper = new QueryWrapper<>();
            dispatchLogQueryWrapper.in("waybill_id",waybillIds);
            dispatchLogService.remove(dispatchLogQueryWrapper);
        }
        dispatchLogService.saveBatch(dispatchLogs);
        //修改此订单状态为调度中
        GeneralOrderEntity generalOrder = new GeneralOrderEntity();
        generalOrder.setGeneralOrderId(generalOrderEntity.getGeneralOrderId());
        generalOrder.setGeneralDispatchStatus("1");
        feignOrderService.updateOrder(generalOrder);

    }


    /**
     * 根据运单创建调度日志
     * @return
     */
    private DispatchLog addDispatchLog(WaybillDto waybillEntity,UserInfoEntity userById){
        DispatchLog  dispatchLog = new DispatchLog();
        BeanUtils.copyProperties(waybillEntity,dispatchLog);
        dispatchLog.setEstimatTime(DateUtil.formatStringLocalDateTime(waybillEntity.getEstimatTime()));
        dispatchLog.setEstimatedTime(DateUtil.formatStringLocalDateTime(waybillEntity.getEstimatedTime()));
        dispatchLog.setOrderId(userById.getOrgId());
        dispatchLog.setCreateId(userById.getUserId());
        dispatchLog.setCreateName(userById.getAliasName());
        return dispatchLog;

    }


    @Override
    public void addWayBillByPC(DispatchDto dispatchDto, UserInfoEntity userById) {
        GeneralOrderEntity generalOrderEntity = feignOrderService.orderDetail(dispatchDto.getGeneralOrderId());
        if (ObjectUtils.isEmpty(generalOrderEntity)) {
            throw new BussException("订单不存在！");
        }
        List<CarDetailDto> waybillEntities = dispatchDto.getWaybillEntities();
        if (ObjectUtils.isEmpty(waybillEntities)) {
            return;
        }
        List<WaybillDto> list = new ArrayList<>();
        List<DispatchLog> dispatchLogs = new ArrayList<>();
        List<String> waybillIds = new ArrayList<>();
        WaybillDto waybillEntity ;
        for (CarDetailDto carDetailDto:waybillEntities){
            waybillEntity = new WaybillDto();
            BeanUtils.copyProperties(generalOrderEntity,waybillEntity);
            if (ObjectUtils.isEmpty(carDetailDto.getWaybillId())) {
                waybillEntity.setWaybillId(String.valueOf(SnowflakeIdWorker.generateId()));
                waybillEntity.setWaybillNo(getGeneralOrderNo());
                waybillEntity.setType(1);
                waybillEntity.setCarId(carDetailDto.getCarId());
                waybillEntity.setCarNo(carDetailDto.getCarNo());
                waybillEntity.setHangId(carDetailDto.getHangId());
                waybillEntity.setHangNo(carDetailDto.getHangNo());
                waybillEntity.setDriverId(carDetailDto.getDriverId());
                waybillEntity.setDriverName(carDetailDto.getDriverName());
                waybillEntity.setDriverPhoneNo(carDetailDto.getDriverPhoneNo());
                waybillEntity.setCreateUser(userById.getUserId());
                waybillEntity.setOrgId(userById.getOrgId());
                waybillEntity.setEstimatedTime(carDetailDto.getEstimatedTime());
                waybillEntity.setEstimatTime(carDetailDto.getEstimatTime());
                waybillEntity.setComments(carDetailDto.getRemark());
                list.add(waybillEntity);
            }else {
                //等于2 的时候是修改操作  原来的数据都有了
                waybillEntity.setWaybillId(carDetailDto.getWaybillId());
                waybillEntity.setWaybillNo(carDetailDto.getWaybillNo());
                waybillEntity.setType(2);
                waybillEntity.setCarId(carDetailDto.getCarId());
                waybillEntity.setCarNo(carDetailDto.getCarNo());
                waybillEntity.setHangId(carDetailDto.getHangId());
                waybillEntity.setHangNo(carDetailDto.getHangNo());
                waybillEntity.setDriverId(carDetailDto.getDriverId());
                waybillEntity.setDriverName(carDetailDto.getDriverName());
                waybillEntity.setDriverPhoneNo(carDetailDto.getDriverPhoneNo());
                waybillEntity.setCreateUser(userById.getUserId());
                waybillEntity.setOrgId(userById.getOrgId());
                waybillEntity.setEstimatedTime(carDetailDto.getEstimatedTime());
                waybillEntity.setEstimatTime(carDetailDto.getEstimatTime());
                waybillEntity.setComments(carDetailDto.getRemark());
                list.add(waybillEntity);
                waybillIds.add(carDetailDto.getWaybillId());
            }
            //创建调度单日志
            DispatchLog dispatchLog = addDispatchLog(waybillEntity, userById);
            dispatchLogs.add(dispatchLog);

        }
//        try{
            feignOrderService.addList(list);
//        }catch (Exception e){
//            log.error("链接订单服务创建运单失败");
//            throw new BussException("调度失败！");
//        }
        //删除掉原来订单的状态
        if (!ObjectUtils.isEmpty(waybillIds)) {
            QueryWrapper<DispatchLog> dispatchLogQueryWrapper = new QueryWrapper<>();
            dispatchLogQueryWrapper.in("waybill_id",waybillIds);
            dispatchLogService.remove(dispatchLogQueryWrapper);
        }
        dispatchLogService.saveBatch(dispatchLogs);
        //修改此订单状态为调度中
        GeneralOrderEntity generalOrder = new GeneralOrderEntity();
        generalOrder.setGeneralOrderId(generalOrderEntity.getGeneralOrderId());
        generalOrder.setGeneralDispatchStatus("1");
        feignOrderService.updateOrder(generalOrder);
    }



    public String getGeneralOrderNo(){
        String getWayBillIdlock = "wayBill:add";
        String time = dateFormat.format(new Date());
        String wayBiiiNo = "wayBillId:"+time;
        RedisUtil.tryLock(getWayBillIdlock,"1",100);
        String id = time+"0001";

        boolean b = RedisUtil.hasKey(wayBiiiNo);
        if(b){
            Long lon = Long.parseLong(RedisUtil.get(wayBiiiNo).toString())+1;
            RedisUtil.set(wayBiiiNo,lon);
            id=lon.toString();
        }else{
            RedisUtil.set(wayBiiiNo,id,60);
        }
        RedisUtil.unlock(wayBiiiNo);
        return id;
    }


}
