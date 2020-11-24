package com.cargo.alipay.service;

import com.cargo.dto.DispatchDto;
import com.cargo.entity.UserInfoEntity;
import com.cargo.entity.WaybillEntity;
import com.cargo.vo.DriverMessageVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 调度刚开始的service
 */
public interface DispatchService {

    /**
     * 获取车辆
     * @param
     * @return
     */
    void getCar(String orgId);


    /**
     * 获取司机
     * @param
     * @return
     */
    List<DriverMessageVo> getDriver(String orgId);


    /**
     * app端调度
     * @param dispatchDto
     * @param userById
     */
    void addWayBillByApp(DispatchDto dispatchDto, UserInfoEntity userById);

    /**
     * pc 端调度
     * @param dispatchDto
     * @param userById
     */
    void addWayBillByPC(DispatchDto dispatchDto, UserInfoEntity userById);
}
