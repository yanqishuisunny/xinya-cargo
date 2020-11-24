package com.cargo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.carrier.entity.CarrierEntity;
import com.cargo.carrier.service.CarrierService;
import com.cargo.examineLog.entity.ExamineLogEntity;
import com.cargo.examineLog.mapper.ExamineLogMapper;
import com.cargo.owner.entity.OwnerEntity;
import com.cargo.owner.service.OwnerService;
import com.cargo.user.dto.DriverInformationDto;
import com.cargo.user.dto.OrgDto;
import com.cargo.user.entity.DriverInformationEntity;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.mapper.OrgMapper;
import com.cargo.user.service.OrgService;
import com.cargo.user.vo.OrgVo;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.mysql.jdbc.StringUtils;
import com.sun.org.apache.bcel.internal.generic.I2F;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 组织表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Service
public class OrgUserServiceImpl extends ServiceImpl<OrgMapper, OrgEntity> implements OrgService {

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private ExamineLogMapper examineLogMapper;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private CarrierService carrierService;

    @Override
    public Page<OrgVo> queryForExamineList(OrgDto dto, Page<OrgVo> page) {
        ArrayList<Integer> integers = new ArrayList<>();
        if(dto.getAuditStatus() != null){
            integers.add(dto.getAuditStatus());
            dto.setListAuditStatus(integers);
        }else{
            integers.add(0);
            integers.add(1);
            integers.add(2);
            dto.setListAuditStatus(integers);
        }
        return page.setRecords(orgMapper.queryForExamineList(dto,page));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editStatus(OrgDto dto) {
        OrgEntity oldentity = orgMapper.selectById(dto.getOrgId());
        if(oldentity == null){
            throw new BussException("查不到企业数据 OrgId："+dto.getOrgId());
        }
        UpdateWrapper<OrgEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("org_id",dto.getOrgId());
        updateWrapper.set("audit_status",dto.getAuditStatus());
        if(!StringUtils.isNullOrEmpty(dto.getCheckRemark())){
            updateWrapper.set("check_remark",dto.getCheckRemark());
        }
        boolean update = this.update(updateWrapper);
        if(!update){
            throw new BussException("更新失败OrgEntity");
        }
        ExamineLogEntity logEntity = new ExamineLogEntity();
        logEntity.setCheckRemark(dto.getCheckRemark());
        logEntity.setType(ExamineLogEntity.TYPE_3);
        logEntity.setCreateUser(ShiroUtil.getUserId());
        logEntity.setOldStatus(oldentity.getAuditStatus()+ "");
        logEntity.setNewStatus(dto.getAuditStatus()+"");
        logEntity.setSourceId(dto.getOrgId());
        int insert = examineLogMapper.insert(logEntity);
        if(insert != 1){
            throw new BussException("新增日志失败");
        }

        //企业角色 0:承运商 1:货主 4平台
        if(oldentity.getOrgRole() == 0){
            UpdateWrapper<CarrierEntity> upCarr= new UpdateWrapper<>();
            upCarr.eq("org_id",dto.getOrgId());
            upCarr.set("audit_status",dto.getAuditStatus());
            upCarr.set("update_user", ShiroUtil.getUserId());
            if(!StringUtils.isNullOrEmpty(dto.getCheckRemark())){
                upCarr.set("check_remark",dto.getCheckRemark());
            }
            update = carrierService.update(upCarr);
            if(!update){
                throw new BussException("更新失败");
            }
            logEntity = new ExamineLogEntity();
            logEntity.setCheckRemark(dto.getCheckRemark());
            logEntity.setType(ExamineLogEntity.TYPE_5);
            logEntity.setCreateUser(ShiroUtil.getUserId());
            logEntity.setOldStatus(oldentity.getAuditStatus()+ "");
            logEntity.setNewStatus(dto.getAuditStatus()+"");
            logEntity.setSourceId(dto.getOrgId());
            insert = examineLogMapper.insert(logEntity);
            if(insert != 1){
                throw new BussException("新增日志失败");
            }
        }else if(oldentity.getOrgRole() == 1){
            UpdateWrapper<OwnerEntity> upOwner= new UpdateWrapper<>();
            upOwner.eq("org_id",dto.getOrgId());
            upOwner.set("audit_status",dto.getAuditStatus());
            upOwner.set("update_user", ShiroUtil.getUserId());
            if(!StringUtils.isNullOrEmpty(dto.getCheckRemark())){
                upOwner.set("check_remark",dto.getCheckRemark());
            }
            update = ownerService.update(upOwner);
            if(!update){
                throw new BussException("更新失败");
            }
            logEntity = new ExamineLogEntity();
            logEntity.setCheckRemark(dto.getCheckRemark());
            logEntity.setType(ExamineLogEntity.TYPE_3);
            logEntity.setCreateUser(ShiroUtil.getUserId());
            logEntity.setOldStatus(oldentity.getAuditStatus()+ "");
            logEntity.setNewStatus(dto.getAuditStatus()+"");
            logEntity.setSourceId(dto.getOrgId());
            insert = examineLogMapper.insert(logEntity);
            if(insert != 1){
                throw new BussException("新增日志失败");
            }
        }else{
            throw new BussException("未知企业角色 OrgRole:"+oldentity.getOrgRole());
        }




        return true;
    }
}
