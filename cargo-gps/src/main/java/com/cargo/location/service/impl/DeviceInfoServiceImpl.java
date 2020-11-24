package com.cargo.location.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.feign.service.FeignService;
import com.cargo.location.mapper.DeviceInfoDao;
import com.cargo.location.entity.DeviceInfoEntity;
import com.cargo.location.vo.UserInfoVo;
import com.cargo.location.service.DeviceInfoService;
import com.commom.exception.BussException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoDao, DeviceInfoEntity> implements DeviceInfoService {


    @Resource
    private DeviceInfoDao deviceInfoDao;

    @Resource
    private FeignService userFeignService;



    /**
     * 查询设备信息  根据设备id查询出该设备，在进行修改  ,点击编辑按钮也可以使用这个接口
     *
     * @param deviceId
     * @return
     */
    @Override
    public DeviceInfoEntity queryDeviceInfo(String deviceId , String userId) {
        UserInfoVo userInfoVo = userFeignService.getUserById(userId);
        if(userInfoVo == null){
           throw new BussException("没有此用户");
        }
        return deviceInfoDao.selectById(deviceId);
    }

}
