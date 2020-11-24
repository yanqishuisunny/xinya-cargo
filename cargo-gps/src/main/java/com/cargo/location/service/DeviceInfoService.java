package com.cargo.location.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.location.entity.DeviceInfoEntity;

public interface DeviceInfoService extends IService<DeviceInfoEntity> {



    /**
     * 查询设备信息
     * @param deviceId
     * @return
     */
    DeviceInfoEntity queryDeviceInfo(String deviceId , String userId);


}
