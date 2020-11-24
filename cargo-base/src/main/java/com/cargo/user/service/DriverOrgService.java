package com.cargo.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.user.dto.DriverOrgDto;
import com.cargo.user.dto.OrgDto;
import com.cargo.user.dto.UserInfoDto;
import com.cargo.user.entity.DriverOrgEntity;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.vo.DriverOrgVo;

import java.util.List;

/**
 * <p>
 * 组织邀请司机记录表 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10
 */
public interface DriverOrgService extends IService<DriverOrgEntity> {


    /**
     * 查询司机企业合作关系详情列表
     * @param dto
     */
    List<OrgEntity> appDriverOrgList(OrgDto dto,String userId);


    /**
     * 查询司机企业合作关系详情列表
     * @param dto
     */
    IPage<UserInfoEntity> driverlist(IPage<UserInfoEntity> page, UserInfoDto dto,String orgId);


    /**
     * 司机加盟企业
     * @param dto
     */
    boolean add(DriverOrgDto dto);


    /**
     * 编辑企业司机合作关系
     * @param dto
     */
    boolean edit(DriverOrgDto dto);



    /**
     * 查询司机企业合作关系详情
     * @param dto
     */
    DriverOrgVo detail(DriverOrgDto dto,String userId);


    /**
     * 根据企业id查询合作的司机企业关系详情列表
     * @param dto
     */
    IPage<DriverOrgEntity> pcList(IPage<DriverOrgEntity> page,DriverOrgDto dto, String orgId);



    /**
     * 根据司机id查询关联的企业合作关系详情列表
     * @param dto
     */
    IPage<DriverOrgVo> appList(IPage<DriverOrgEntity> page, DriverOrgDto dto, String userId);


    /**
     * 根据企业id查询合作的司机企业关系详情列表(不分页)
     * @param dto
     */
    List<DriverOrgEntity> pcList(DriverOrgDto dto, String orgId);


}
