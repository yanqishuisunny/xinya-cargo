package com.cargo.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.feign.vo.ConsignorReleaseVo;
import com.cargo.user.dto.DriverInformationDto;
import com.cargo.user.entity.DriverInformationEntity;
import com.cargo.user.vo.DriverInformationVo;
import com.cargo.user.vo.UserInfoVo;

/**
 * <p>
 * 司机信息表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
public interface DriverInformationService extends IService<DriverInformationEntity> {
    /**
     * 在用户登录的时候将所有司机信息放到redis中
     */
    void  driverToRedis(String orgId);




    boolean editDriver (String userId, DriverInformationDto driverDto);



    /**
     * 根据用户id 查询司机信息
     */
    DriverInformationEntity selectInfoByUserId(String userId);


    Page<DriverInformationVo> queryForExamineList(DriverInformationDto dto, Page<DriverInformationVo> page);


    boolean editStatus(DriverInformationDto dto);


    UserInfoVo detail(String userId);
}
