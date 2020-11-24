package com.cargo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.user.dto.DriverOrgDto;
import com.cargo.user.dto.OrgDto;
import com.cargo.user.dto.UserInfoDto;
import com.cargo.user.entity.DriverOrgEntity;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.entity.OrgUserAssociationEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.DriverOrgMapper;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.DriverOrgService;
import com.cargo.user.service.OrgService;
import com.cargo.user.service.OrgUserAssociationService;
import com.cargo.user.service.UserInfoService;
import com.cargo.user.vo.DriverOrgVo;
import com.commom.exception.BussException;
import com.commom.gpsUtils.StringUtil;
import com.commom.shiro.ShiroUtil;
import com.commom.snowflake.SnowflakeIdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织邀请司机记录表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-10
 */
@Service
public class DriverOrgServiceImpl extends ServiceImpl<DriverOrgMapper, DriverOrgEntity> implements DriverOrgService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgUserAssociationService orgUserAssociationService;

    @Autowired
    private UserInfoMapper userInfoMapper;



    /**
     * 查询司机企业合作关系详情列表
     * @param dto
     */
    @Override
    public List<OrgEntity> appDriverOrgList(OrgDto dto, String userId) {
        if(StringUtils.isEmpty(dto.getOrgName())){
            throw new BussException("缺少入参");
        }
        QueryWrapper<OrgEntity> orgWrapper = new QueryWrapper<>();
        orgWrapper.eq("org_role",0);
        orgWrapper.like("org_name", dto.getOrgName()).or().like("phone_no", dto.getOrgName());
        return orgService.list(orgWrapper);
    }


    /**
     * 查询司机企业合作关系详情列表
     * @param dto
     */
    @Override
    public IPage<UserInfoEntity> driverlist(IPage<UserInfoEntity> page , UserInfoDto dto,String orgId) {
        QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<>();
        if(!StringUtil.isEmpty(dto.getUserCode())){
            queryWrapper.like("user_code", dto.getUserCode());
        }
        if(!StringUtil.isEmpty(dto.getPhoneNo())){
            queryWrapper.like("phone_no", dto.getPhoneNo());
        }
        queryWrapper.eq("user_role", 3);
        queryWrapper.orderByDesc("gmt_create");
        return this.userInfoMapper.selectPage(page,queryWrapper);
    }


    /**
     * 司机加盟企业
     * @param dto
     */
    @Override
    @Transactional
    public boolean add(DriverOrgDto dto) {
        DriverOrgEntity driverOrgEntity = new DriverOrgEntity();
        BeanUtils.copyProperties(dto,driverOrgEntity);
        if(1 == dto.getType()){
            UserInfoEntity userInfoEntity = userInfoService.getById(ShiroUtil.getUserId());
            driverOrgEntity.setOrgId(dto.getOrgId());
            driverOrgEntity.setOrgName(dto.getOrgName());
            driverOrgEntity.setUserId(userInfoEntity.getUserId());
            driverOrgEntity.setUserName(userInfoEntity.getUserName());
            driverOrgEntity.setPhoneNo(userInfoEntity.getPhoneNo());
        }else{
            if(StringUtil.isEmpty(ShiroUtil.getOrgId())){
                throw new BussException("登陆人没有选择企业");
            }
            OrgEntity orgEntity = orgService.getById(ShiroUtil.getOrgId());
            driverOrgEntity.setOrgId(orgEntity.getOrgId());
            driverOrgEntity.setOrgName(orgEntity.getOrgName());
            driverOrgEntity.setUserId(dto.getUserId());
            UserInfoEntity userInfoEntity = userInfoService.getById(dto.getUserId());
            if(null != userInfoEntity){
                driverOrgEntity.setUserName(userInfoEntity.getUserName());
                driverOrgEntity.setPhoneNo(userInfoEntity.getPhoneNo());
            }
        }
        QueryWrapper<DriverOrgEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", driverOrgEntity.getUserId());
        wrapper.eq("org_id", driverOrgEntity.getOrgId());
        wrapper.in("status", 1,2);
        DriverOrgEntity driver = this.baseMapper.selectOne(wrapper);
        if(null != driver){
            if(1 == driver.getStatus()){
                throw new BussException("该企业已经和司机已经是待合作关系");
            }else if(2 == driver.getStatus()){
                throw new BussException("该企业已经和司机已经是合作中关系");
            }
        }
        driverOrgEntity.setStatus(1);
        driverOrgEntity.setId(SnowflakeIdWorker.generateId().toString());
        driverOrgEntity.setServeStartTime(LocalDateTime.now());
        this.baseMapper.insert(driverOrgEntity);
        return true;
    }



    /**
     * 编辑企业司机合作关系
     * @param dto
     */
    @Override
    @Transactional
    public boolean edit(DriverOrgDto dto) {
        if(StringUtil.isEmpty(dto.getId())){
            throw new BussException("缺少入参");
        }
        DriverOrgEntity driverOrgEntity = this.baseMapper.selectById(dto.getId());
        if(null == driverOrgEntity){
            throw new BussException("没有该合作关系");
        }
        if(null != dto.getStatus() && 5 == dto.getStatus()){
            this.baseMapper.deleteById(driverOrgEntity);
        }else{
            driverOrgEntity.setStatus(dto.getStatus());
            this.baseMapper.updateById(driverOrgEntity);
        }
        if(!StringUtil.isEmpty(dto.getOrgId()) && !StringUtil.isEmpty(dto.getUserId()) || 2 == dto.getStatus()){
            QueryWrapper<OrgUserAssociationEntity> associationWrapper = new QueryWrapper<>();
            associationWrapper.eq("user_id", dto.getUserId());
            associationWrapper.eq("org_id", dto.getOrgId());
            OrgUserAssociationEntity orgUserAssociationEntity = orgUserAssociationService.getOne(associationWrapper);
            OrgEntity orgEntity = orgService.getById(dto.getOrgId());
            if(null == orgUserAssociationEntity && null != orgEntity){
                orgUserAssociationEntity = new OrgUserAssociationEntity();
                orgUserAssociationEntity.setOrgId(orgEntity.getOrgId());
                orgUserAssociationEntity.setOrgRole(orgEntity.getOrgRole().toString());
                orgUserAssociationEntity.setUserId(dto.getUserId());
                orgUserAssociationEntity.setLastLoginTime(LocalDateTime.now());
                orgUserAssociationService.save(orgUserAssociationEntity);
            }
        }
        return true;
    }



    /**
     * 查询司机企业合作关系详情
     * @param dto
     */
    @Override
    public DriverOrgVo detail(DriverOrgDto dto,String userId) {
        DriverOrgVo driverOrgVo = new DriverOrgVo();
        if(null == dto.getStatus() || StringUtil.isEmpty(dto.getOrgId())){
            throw new BussException("缺少入参");
        }
        QueryWrapper<DriverOrgEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("org_id", dto.getOrgId());
        wrapper.eq("status", dto.getStatus());
        DriverOrgEntity driverOrgEntity = this.getOne(wrapper);
        if(null != driverOrgEntity){
            BeanUtils.copyProperties(driverOrgEntity,driverOrgVo);
            return driverOrgVo;
        }
        return driverOrgVo;
    }




    /**
     * 根据企业id查询合作的司机企业关系详情列表
     * @param dto
     */
    @Override
    public IPage<DriverOrgEntity> pcList(IPage<DriverOrgEntity> page , DriverOrgDto dto, String orgId) {
        if(StringUtil.isEmpty(orgId)){
            throw new BussException("当前登录没有选择企业");
        }
        QueryWrapper<DriverOrgEntity> queryWrapper = new QueryWrapper<>();
        if(null != dto.getType()){
            queryWrapper.eq("type", dto.getType());
        }
        if(null != dto.getStatus()){
            queryWrapper.eq("status", dto.getStatus());
        }
        queryWrapper.eq("org_id", orgId);
        queryWrapper.orderByDesc("gmt_create");
        return this.baseMapper.selectPage(page,queryWrapper);
    }





    /**
     * 根据司机id查询关联的企业合作关系详情列表
     * @param dto
     */
    @Override
    public IPage<DriverOrgVo> appList(IPage<DriverOrgEntity> page , DriverOrgDto dto, String userId) {
        if(null == dto.getStatus()){
            throw new BussException("缺少入参");
        }
        QueryWrapper<DriverOrgEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("status", dto.getStatus());
        queryWrapper.orderByDesc("gmt_create");
        page = this.baseMapper.selectPage(page,queryWrapper);
        IPage<DriverOrgVo> iPage = new Page<>();
        List<DriverOrgVo> list =  new ArrayList<>();
        for(DriverOrgEntity driverOrgEntity : page.getRecords()){
            DriverOrgVo vo = new DriverOrgVo();
            BeanUtils.copyProperties(driverOrgEntity,vo);
            list.add(vo);
        }
        iPage.setRecords(list);
        iPage.setTotal(page.getTotal());
        iPage.setCurrent(page.getCurrent());
        iPage.setSize(page.getSize());
        return iPage;
    }



    /**
     * 根据企业id查询合作的司机企业关系详情列表(不分页)
     * @param dto
     */
    @Override
    public List<DriverOrgEntity> pcList(DriverOrgDto dto, String orgId) {
        if(StringUtil.isEmpty(orgId)){
            throw new BussException("当前登录没有选择企业");
        }
        QueryWrapper<DriverOrgEntity> queryWrapper = new QueryWrapper<>();
        if(null != dto.getType()){
            queryWrapper.eq("type", dto.getType());
        }
        if(null != dto.getStatus()){
            queryWrapper.eq("status", dto.getStatus());
        }
        queryWrapper.eq("org_id", orgId);
        queryWrapper.orderByDesc("gmt_create");
        return this.baseMapper.selectList(queryWrapper);
    }




}
