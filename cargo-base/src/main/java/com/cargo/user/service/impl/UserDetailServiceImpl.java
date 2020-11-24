package com.cargo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.common.UserEnum;
import com.cargo.core.BaseBusCode;
import com.cargo.user.entity.DriverInformationEntity;
import com.cargo.user.entity.UserDetailEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.mapper.UserDetailMapper;
import com.cargo.user.mapper.UserInfoMapper;
import com.cargo.user.service.DriverInformationService;
import com.cargo.user.service.UserDetailService;
import com.commom.core.AssertBuss;
import com.commom.gpsUtils.StringUtil;
import com.commom.snowflake.SnowflakeIdWorker;
import com.commom.utils.DateUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 用户详细情况表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-05
 */
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetailEntity> implements UserDetailService {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private DriverInformationService driverInformationService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFullUserDetail(UserInfoEntity user , UserDetailEntity userDetail) {
        QueryWrapper<UserDetailEntity> udWrapper = new QueryWrapper<>();
        UserDetailEntity userDetailEntity = getOne(udWrapper.eq("user_id",userDetail.getUserId()));
        if(null == userDetailEntity){
            userDetailEntity = new UserDetailEntity();
        }
        userDetailEntity.setIdcardUrl(userDetail.getIdcardUrl());
        userDetailEntity.setIdcardReUrl(userDetail.getIdcardReUrl());
        userDetailEntity.setLivingUrl(userDetail.getLivingUrl());
        userDetailEntity.setIdCardEndTime(userDetail.getIdCardEndTime());
        userDetailEntity.setIdCardStartTime(userDetail.getIdCardStartTime());
        userDetailEntity.setFlgPerfect(true);
        userDetailEntity.setIdCardAuthority(userDetail.getIdCardAuthority());
        userDetailEntity.setIdcardName(userDetail.getIdcardName());
        userDetailEntity.setOverdueStatus(0);
        if(StringUtil.isEmpty(userDetailEntity.getId())){
            userDetailEntity.setIdcardNo(userDetail.getIdcardNo());
            userDetailEntity.setUserId(user.getUserId());
            userDetailEntity.setId(SnowflakeIdWorker.generateId().toString());
            this.baseMapper.insert(userDetailEntity);
        }else{
            AssertBuss.isFalse(exitsByIdCard(userDetail.getIdcardNo(),userDetail.getUserId()), UserEnum.Code.IDCARD_REPEAT_ERROR);
            userDetailEntity.setIdcardNo(userDetail.getIdcardNo());
            this.baseMapper.updateById(userDetailEntity);
        }
        UserInfoEntity userEntity = userInfoMapper.selectById(user.getUserId());
        userEntity.setFlgPerfect(true);
        userEntity.setUserName(userDetail.getIdcardName());
        userEntity.setEmail(user.getEmail());
        userInfoMapper.updateById(userEntity);
        //实名认证的时候  把身份证上的名字放到t_driver_information上去
        DriverInformationEntity driverInformationEntity = driverInformationService.getOne(new QueryWrapper<DriverInformationEntity>().eq("user_id",userDetail.getUserId()));
        if(null != driverInformationEntity){
            driverInformationEntity.setIdCardPosUrl(userDetail.getIdcardUrl());
            driverInformationEntity.setIdCardVerUrl(userDetail.getIdcardReUrl());
            driverInformationEntity.setIdCardName(userDetail.getIdcardName());
            driverInformationEntity.setIdCardNo(userDetail.getIdcardNo());
            driverInformationEntity.setIdCardStartTime(userDetail.getIdCardStartTime());
            driverInformationEntity.setIdCardEndTime(userDetail.getIdCardEndTime());
            driverInformationEntity.setIdCardAuthority(userDetail.getIdCardAuthority());
            if (!Strings.isNullOrEmpty(driverInformationEntity.getDriverLicenseUrl())) {
                driverInformationEntity.setAuditStatus(2);
            }else{
                driverInformationEntity.setAuditStatus(1);
            }
            if(null != driverInformationEntity.getFirstSubmitTime()){
                driverInformationEntity.setFirstSubmitTime(LocalDateTime.now());
            }
            driverInformationEntity.setLastSubmitTime(LocalDateTime.now());
            driverInformationEntity.setAuditMode("1");
            driverInformationService.updateById(driverInformationEntity);
        }else{
            driverInformationEntity = new DriverInformationEntity();
            //修改，直接update即可
            driverInformationEntity.setAuditStatus(1);
            driverInformationEntity.setUpdateUser(userEntity.getUserId());
            driverInformationEntity.setUpdateUserName(userEntity.getUserCode());
            driverInformationEntity.setLastSubmitTime(LocalDateTime.now());
            //将证件过期重变成不过期
            driverInformationEntity.setQualificationEntTimeOverdueStatus("0");
            driverInformationEntity.setDriverLicenseOverdueStatus(0);
            driverInformationEntity.setIdCardEndTimeOverdueStatus(0);
            driverInformationEntity.setOverdueStatus(0);
            //重新提交审核后，上次审核失败的原因置空
            driverInformationEntity.setAuditFailReason(null);
            driverInformationEntity.setDriverLicenseNo(Strings.isNullOrEmpty(driverInformationEntity.getDriverLicenseNo()) ? driverInformationEntity.getIdCardNo() : driverInformationEntity.getDriverLicenseNo());
            //修改的时候把接口审核状态变为待审核
            driverInformationEntity.setExtAuditStatus(0);
            driverInformationEntity.setIdCardPosUrl(userDetail.getIdcardUrl());
            driverInformationEntity.setIdCardVerUrl(userDetail.getIdcardReUrl());
            driverInformationEntity.setIdCardName(userDetail.getIdcardName());
            driverInformationEntity.setIdCardNo(userDetail.getIdcardNo());
            if(null != driverInformationEntity.getFirstSubmitTime()){
                driverInformationEntity.setFirstSubmitTime(LocalDateTime.now());
            }
            driverInformationEntity.setLastSubmitTime(LocalDateTime.now());
            driverInformationEntity.setAuditMode("1");
            driverInformationEntity.setUserId(user.getUserId());
            driverInformationEntity.setCreateUser(user.getUserId());
            driverInformationEntity.setCreateUserName(user.getUserCode());
            driverInformationEntity.setIdCardStartTime(userDetail.getIdCardStartTime());
            driverInformationEntity.setIdCardEndTime(userDetail.getIdCardEndTime());
            driverInformationEntity.setIdCardAuthority(userDetail.getIdCardAuthority());
            driverInformationEntity.setInformationId(SnowflakeIdWorker.generateId().toString());
            driverInformationService.save(driverInformationEntity);
        }
    }



    @Override
    public boolean exitsByIdCard(String card,String userId) {
        if(Strings.isNullOrEmpty(card)){
            return false;
        }
        QueryWrapper<UserDetailEntity> wrapper = new QueryWrapper<>();
        if(!Strings.isNullOrEmpty(userId)){
            wrapper.ne("user_id",userId);
        }
        int count = count(wrapper.eq("idcard_no",card));
        return retBool(count);
    }



    @Override
    public UserDetailEntity selectOneByUserId(String userId) {
        QueryWrapper<UserDetailEntity> udWrapper = new QueryWrapper<>();
        return getOne(udWrapper.eq("user_id",userId));
    }


}
