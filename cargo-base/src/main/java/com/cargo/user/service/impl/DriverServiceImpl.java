package com.cargo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.user.dto.DriverDto;
import com.cargo.user.entity.DriverEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.DriverMapper;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.DriverService;
import com.cargo.user.vo.DriverOrgVo;
import com.cargo.user.vo.DriverVo;
import com.cargo.user.vo.UserInfoVo;
import com.commom.exception.BussException;
import com.commom.gpsUtils.StringUtil;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 司机合作关系表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-13
 */
@Service
public class DriverServiceImpl extends ServiceImpl<DriverMapper, DriverEntity> implements DriverService {


    @Autowired
    private UserInfoMapper userInfoMapper;


    /**
     * 司机加盟企业
     * @param dto
     */
    @Override
    @Transactional
    public boolean add(DriverDto dto) {
//        if(StringUtil.isEmpty(ShiroUtil.getOrgId())){
//            throw new BussException("登陆人没有选择企业");
//        }
        QueryWrapper<DriverEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", ShiroUtil.getUserId());
        wrapper.eq("driver_id",dto.getDriverId());
        wrapper.in("status", 1,2);
        DriverEntity driver = this.baseMapper.selectOne(wrapper);
        if(null != driver){
            throw new BussException("该司机已经产生合作关系");
        }
        QueryWrapper<DriverEntity> driverWrapper = new QueryWrapper<>();
        driverWrapper.eq("user_id", dto.getDriverId());
        driverWrapper.eq("driver_id",ShiroUtil.getUserId());
        driverWrapper.in("status", 1,2);
        driver = this.baseMapper.selectOne(driverWrapper);
        if(null != driver){
            throw new BussException("该司机已经产生合作关系");
        }
        DriverEntity driverEntity = new DriverEntity();
        BeanUtils.copyProperties(dto,driverEntity);
        driverEntity.setUserId(ShiroUtil.getUserId());
        driverEntity.setStatus(1);
        UserInfoEntity userInfoEntity = userInfoMapper.selectById(ShiroUtil.getUserId());
        if(null != userInfoEntity){
            driverEntity.setUserName(userInfoEntity.getUserCode());
            driverEntity.setPhoneNo(userInfoEntity.getPhoneNo());
        }
        this.baseMapper.insert(driverEntity);
        return true;
    }



    /**
     * 编辑企业司机合作关系
     * @param dto
     */
    @Override
    @Transactional
    public boolean edit(DriverDto dto) {
        if(StringUtil.isEmpty(dto.getId())){
            throw new BussException("缺少入参");
        }
        DriverEntity driverEntity = this.baseMapper.selectById(dto.getId());
        if(null == driverEntity){
            throw new BussException("没有该合作关系");
        }
        if(3 == driverEntity.getStatus() || 4 == driverEntity.getStatus()){
            throw new BussException("已删除和已拒绝合作的司机不能在编辑");
        }
        driverEntity.setStatus(dto.getStatus());
        this.baseMapper.updateById(driverEntity);
        return true;
    }



    /**
     * 查询司机企业合作关系详情
     * @param dto
     */
    @Override
    public DriverOrgVo detail(DriverDto dto,String userId) {
        DriverOrgVo driverOrgVo = new DriverOrgVo();
        QueryWrapper<DriverEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("driver_id", dto.getDriverId());
        wrapper.eq("status", dto.getStatus());
        DriverEntity driverEntity = this.getOne(wrapper);
        if(null != driverEntity){
            BeanUtils.copyProperties(driverEntity,driverOrgVo);
            return driverOrgVo;
        }
        return driverOrgVo;
    }




    /**
     * 查询司机和合作的司机列表
     * @param dto
     */
    @Override
    public IPage<DriverVo> list(IPage<DriverEntity> page , DriverDto dto) {
        QueryWrapper<DriverEntity> queryWrapper = new QueryWrapper<>();
        if(null != dto.getStatus()){
            queryWrapper.eq("status", dto.getStatus());
        }
        queryWrapper.eq("user_id", ShiroUtil.getUserId()).or().eq("driver_id",  ShiroUtil.getUserId());
        queryWrapper.orderByDesc("gmt_create");
        page = this.baseMapper.selectPage(page,queryWrapper);
        IPage<DriverVo> iPage = new Page<>();
        List<DriverVo> list =  new ArrayList<>();
        for(DriverEntity driverEntity : page.getRecords()){
            DriverVo vo = new DriverVo();
            BeanUtils.copyProperties(driverEntity,vo);
            if(!StringUtil.isEmpty(vo.getUserId()) && ShiroUtil.getUserId().equals(vo.getUserId())){
                vo.setType(1);
            }else{
                vo.setType(2);
            }
            list.add(vo);
        }
        iPage.setRecords(list);
        iPage.setTotal(page.getTotal());
        iPage.setCurrent(page.getCurrent());
        iPage.setSize(page.getSize());
        return iPage;
    }



    /**
     * 查询平台其他司机列表
     * @param dto
     */
    @Override
    public IPage<UserInfoVo> driverlist(IPage<UserInfoEntity> page , DriverDto dto) {
        QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("org_name", dto.getUserName()).or().like("phone_no", dto.getPhoneNo());
        queryWrapper.eq("user_role", 3);
        queryWrapper.ne("user_id",ShiroUtil.getUserId());
        queryWrapper.orderByDesc("gmt_create");
        page = this.userInfoMapper.selectPage(page,queryWrapper);
        IPage<UserInfoVo> iPage = new Page<>();
        List<UserInfoVo> list =  new ArrayList<>();
        for(UserInfoEntity userInfoEntity : page.getRecords()){
            UserInfoVo vo = new UserInfoVo();
            BeanUtils.copyProperties(userInfoEntity,vo);
            list.add(vo);
        }
        iPage.setRecords(list);
        iPage.setTotal(page.getTotal());
        iPage.setCurrent(page.getCurrent());
        iPage.setSize(page.getSize());
        return iPage;
    }



    /**
     * 查询司机和合作的司机列表
     * @param dto
     */
    @Override
    public List<DriverVo> driList(DriverDto dto) {
        UserInfoEntity userInfoEntity = userInfoMapper.selectById(ShiroUtil.getUserId());
        QueryWrapper<DriverEntity> queryWrapper = new QueryWrapper<>();
        if(null != dto.getStatus()){
            queryWrapper.eq("status", dto.getStatus());
        }
        queryWrapper.eq("user_id", ShiroUtil.getUserId());
        queryWrapper.eq("status", 2);
        queryWrapper.orderByDesc("gmt_create");
        List<DriverEntity> driverEntitieList = this.baseMapper.selectList(queryWrapper);
        List<DriverVo> list =  new ArrayList<>();
        for(DriverEntity driverEntity : driverEntitieList){
            DriverVo vo = new DriverVo();
            BeanUtils.copyProperties(driverEntity,vo);
            list.add(vo);
        }
        DriverVo vo = new DriverVo();
        vo.setUserId(userInfoEntity.getUserId());
        vo.setUserName(userInfoEntity.getUserCode());
        vo.setPhoneNo(userInfoEntity.getPhoneNo());
        vo.setDriverId(userInfoEntity.getUserId());
        vo.setDriverName(userInfoEntity.getUserCode());
        vo.setDriverPhoneNo(userInfoEntity.getPhoneNo());
        list.add(vo);
        return list;
    }


}
