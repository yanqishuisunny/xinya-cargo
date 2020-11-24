package com.cargo.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.user.dto.DriverDto;
import com.cargo.user.dto.DriverOrgDto;
import com.cargo.user.entity.DriverEntity;
import com.cargo.user.entity.DriverOrgEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.vo.DriverOrgVo;
import com.cargo.user.vo.DriverVo;
import com.cargo.user.vo.UserInfoVo;

import java.util.List;

/**
 * <p>
 * 司机合作关系表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-13
 */
public interface DriverService extends IService<DriverEntity> {



    /**
     * 司机加盟企业
     * @param dto
     */
    boolean add(DriverDto dto);


    /**
     * 编辑企业司机合作关系
     * @param dto
     */
    boolean edit(DriverDto dto);



    /**
     * 查询司机企业合作关系详情
     * @param dto
     */
    DriverOrgVo detail(DriverDto dto, String userId);


    /**
     * 查询司机和合作的司机列表
     * @param dto
     */
    IPage<DriverVo> list(IPage<DriverEntity> page, DriverDto dto);



    /**
     * 查询平台其他司机列表
     * @param dto
     */
    IPage<UserInfoVo> driverlist(IPage<UserInfoEntity> page, DriverDto dto);



    /**
     * 查询司机和合作的司机列表
     * @param dto
     */
    List<DriverVo> driList(DriverDto dto);


}
