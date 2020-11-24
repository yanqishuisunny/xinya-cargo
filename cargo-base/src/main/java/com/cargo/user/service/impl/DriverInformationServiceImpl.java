package com.cargo.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.common.DriverBusCode;
import com.cargo.examineLog.entity.ExamineLogEntity;
import com.cargo.examineLog.mapper.ExamineLogMapper;
import com.cargo.user.dto.DriverInformationDto;
import com.cargo.user.entity.DriverInformationEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.DriverInformationMapper;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.DriverInformationService;
import com.cargo.user.vo.DriverInformationVo;
import com.cargo.user.vo.DriverMessageVo;
import com.cargo.user.vo.UserInfoVo;
import com.commom.exception.BussException;
import com.commom.gpsUtils.StringUtil;
import com.commom.shiro.ShiroUtil;
import com.commom.snowflake.SnowflakeIdWorker;
import com.commom.utils.RedisUtil;
import com.google.common.base.Strings;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 司机信息表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Service
public class DriverInformationServiceImpl extends ServiceImpl<DriverInformationMapper, DriverInformationEntity> implements DriverInformationService {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private DriverInformationMapper driverInformationMapper;

    @Autowired
    private ExamineLogMapper examineLogMapper;


    /**
     * 在用户的时候将所有司机信息放到redis中
     *
     * @param orgId
     */
    @Override
    public void driverToRedis(String orgId) {
        // 清空redis 上预留的信息
        RedisUtil.del("driverMessage:" + orgId);
        //查找车辆信息 将车辆信息放入redis中 会将车辆的所有
        List<DriverMessageVo> list =  this.baseMapper.driverToRedis(orgId);
        if (!ObjectUtils.isEmpty(list)) {
            String s = JSON.toJSONString(list);
            RedisUtil.set("driverMessage:"+orgId,s ,86400);
        }
    }


    @Override
    public boolean editDriver(String userId, DriverInformationDto dto) {
        if (!Strings.isNullOrEmpty(dto.getIdCardName()) && !Strings.isNullOrEmpty(dto.getDriverLicenseName()) && !dto.getIdCardName().equals(dto.getDriverLicenseName())) {
            throw new BussException(DriverBusCode.DRIVER_LICENSE_ERROR);
        }
        DriverInformationEntity driverInformationEntity = this.baseMapper.selectOne(new QueryWrapper<DriverInformationEntity>().eq("user_id", userId));
        if(null == driverInformationEntity){
            driverInformationEntity = new DriverInformationEntity();
            BeanUtils.copyProperties(dto, driverInformationEntity);
            UserInfoEntity userInfoEntity = userInfoMapper.selectById(userId);
            if (userInfoEntity != null) {
                driverInformationEntity.setUserId(userInfoEntity.getUserId());
                driverInformationEntity.setUpdateUser(userInfoEntity.getUserId());
                driverInformationEntity.setUpdateUserName(userInfoEntity.getUserCode());
            }
            driverInformationEntity.setLastSubmitTime(LocalDateTime.now());
            driverInformationEntity.setAuditStatus(0);
            //将证件过期重变成不过期
            driverInformationEntity.setQualificationEntTimeOverdueStatus("0");
            driverInformationEntity.setDriverLicenseOverdueStatus(0);
            driverInformationEntity.setIdCardEndTimeOverdueStatus(0);
            driverInformationEntity.setOverdueStatus(0);
            //修改的时候把接口审核状态变为待审核
            driverInformationEntity.setExtAuditStatus(0);
            driverInformationEntity.setInformationId(SnowflakeIdWorker.generateId().toString());
            baseMapper.insert(driverInformationEntity);
        }else{
            if(!StringUtil.isEmpty(dto.getQualificationUrl())){
                driverInformationEntity.setQualificationUrl(dto.getQualificationUrl());
            }
            if(!StringUtil.isEmpty(dto.getIdCardVerUrl())){
                driverInformationEntity.setIdCardVerUrl(dto.getIdCardVerUrl());
            }
            if(!StringUtil.isEmpty(dto.getIdCardPosUrl())){
                driverInformationEntity.setIdCardPosUrl(dto.getIdCardPosUrl());
            }
            if(!StringUtil.isEmpty(dto.getDriverLicenseVerUrl())){
                driverInformationEntity.setDriverLicenseVerUrl(dto.getDriverLicenseVerUrl());
            }
            if(!StringUtil.isEmpty(dto.getDriverLicenseUrl())){
                driverInformationEntity.setDriverLicenseUrl(dto.getDriverLicenseUrl());
            }
            driverInformationEntity.setLastSubmitTime(LocalDateTime.now());
            if(null != dto.getAuditMode()){
                driverInformationEntity.setAuditMode(dto.getAuditMode().toString());
            }
            driverInformationEntity.setAuditStatus(dto.getAuditStatus());
            driverInformationEntity.setDriverLicenseLevel(dto.getDriverLicenseLevel());
            driverInformationEntity.setDriverLicenseLimit(dto.getDriverLicenseLimit());
            driverInformationEntity.setDriverLicenseStartTime(dto.getDriverLicenseStartTime());
            driverInformationEntity.setDriverStatus(dto.getDriverStatus());
            driverInformationEntity.setIsReward(dto.getIsReward());
            driverInformationEntity.setIsSelfAcceptOrder(dto.getIsSelfAcceptOrder());
            driverInformationEntity.setQualificationNo(dto.getQualificationNo());
            driverInformationEntity.setSource(dto.getSource());
            //重新提交审核后，上次审核失败的原因置空
            driverInformationEntity.setAuditFailReason(null);
            //修改的时候把接口审核状态变为待审核
            driverInformationEntity.setExtAuditStatus(0);
            baseMapper.updateById(driverInformationEntity);
        }
        return true;
    }




    @Override
    public DriverInformationEntity selectInfoByUserId(String userId) {
        QueryWrapper<DriverInformationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return this.getOne(wrapper);
    }

    @Override
    public Page<DriverInformationVo> queryForExamineList(DriverInformationDto dto, Page<DriverInformationVo> page) {
        ArrayList<Integer> integers = new ArrayList<>();
        if(dto.getAuditStatus() != null){
            integers.add(dto.getAuditStatus());
            dto.setListAuditStatus(integers);
        }else{
            integers.add(2);
            integers.add(3);
            integers.add(4);
            dto.setListAuditStatus(integers);
        }
        return page.setRecords(driverInformationMapper.queryForExamineList(dto,page));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editStatus(DriverInformationDto dto) {
        DriverInformationEntity oldentity = driverInformationMapper.selectById(dto.getInformationId());
        if(oldentity == null){
            throw new BussException("查不到司机数据 InformationId："+dto.getInformationId());
        }
        UpdateWrapper<DriverInformationEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("information_id",dto.getInformationId());
        updateWrapper.set("audit_status",dto.getAuditStatus());
        updateWrapper.set("update_user", ShiroUtil.getUserId());
        if(!StringUtils.isNullOrEmpty(dto.getCheckRemark())){
            updateWrapper.set("check_remark",dto.getCheckRemark());
        }
        boolean update = this.update(updateWrapper);
        if(!update){
            throw new BussException("更新失敗");
        }
        ExamineLogEntity logEntity = new ExamineLogEntity();
        logEntity.setCheckRemark(dto.getCheckRemark());
        logEntity.setType(ExamineLogEntity.TYPE_2);
        logEntity.setCreateUser(ShiroUtil.getUserId());
        logEntity.setOldStatus(oldentity.getAuditStatus()+ "");
        logEntity.setNewStatus(dto.getAuditStatus()+"");
        logEntity.setSourceId(dto.getInformationId());
        int insert = examineLogMapper.insert(logEntity);
        if(insert != 1){
            throw new BussException("新增日志失敗");
        }
        return true;
    }



    @Override
    public UserInfoVo detail(String userId) {
        UserInfoEntity userInfoEntity = userInfoMapper.selectById(userId);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfoEntity,userInfoVo);
        QueryWrapper<DriverInformationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        DriverInformationEntity driverInformationEntity = this.getOne(wrapper);
        DriverInformationVo driverInformationVo =  new DriverInformationVo();
        BeanUtils.copyProperties(driverInformationEntity,driverInformationVo);
        userInfoVo.setDriverInformationVo(driverInformationVo);
        return userInfoVo;
    }


}
