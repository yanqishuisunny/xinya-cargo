package com.cargo.owner.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.examineLog.entity.ExamineLogEntity;
import com.cargo.examineLog.mapper.ExamineLogMapper;
import com.cargo.owner.dto.OwnerDto;
import com.cargo.owner.entity.OwnerEntity;
import com.cargo.owner.mapper.OwnerMapper;
import com.cargo.owner.service.OwnerService;
import com.cargo.owner.vo.OwnerVo;
import com.cargo.user.entity.DriverInformationEntity;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 组织扩展表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Service
public class OwnerServiceImpl extends ServiceImpl<OwnerMapper, OwnerEntity> implements OwnerService {
    @Autowired
    private OwnerMapper ownerMapper;
    @Autowired
    private ExamineLogMapper examineLogMapper;


    @Override
    public Page<OwnerVo> queryForExamineList(OwnerDto dto, Page<OwnerVo> page) {
        return page.setRecords(ownerMapper.queryForExamineList(dto,page));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editStatus(OwnerDto dto) {
        OwnerEntity oldentity = ownerMapper.selectById(dto.getOwnerId());
        if(oldentity == null){
            throw new BussException("查不到货主数据 OwnerId："+dto.getOwnerId());
        }
        UpdateWrapper<OwnerEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("owner_id",dto.getOwnerId());
        updateWrapper.set("audit_status",dto.getAuditStatus());
        updateWrapper.set("update_user", ShiroUtil.getUserId());
        if(StringUtils.isNotEmpty(dto.getCheckRemark())){
            updateWrapper.set("check_remark",dto.getCheckRemark());
        }
        boolean update = this.update(updateWrapper);
        if(!update){
            throw new BussException("更新失敗");
        }
        ExamineLogEntity logEntity = new ExamineLogEntity();
        logEntity.setCheckRemark(dto.getCheckRemark());
        logEntity.setType(ExamineLogEntity.TYPE_3);
        logEntity.setCreateUser(ShiroUtil.getUserId());
        logEntity.setOldStatus(oldentity.getAuditStatus()+ "");
        logEntity.setNewStatus(dto.getAuditStatus()+"");
        logEntity.setSourceId(dto.getOwnerId());
        int insert = examineLogMapper.insert(logEntity);
        if(insert != 1){
            throw new BussException("新增日志失敗");
        }
        return true;
    }
}
