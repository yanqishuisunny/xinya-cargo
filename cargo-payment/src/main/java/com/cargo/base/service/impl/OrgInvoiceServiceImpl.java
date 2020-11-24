package com.cargo.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.base.dto.OrgInvoiceDto;
import com.cargo.base.entity.OrgInvoiceEntity;
import com.cargo.base.mapper.OrgInvoiceMapper;
import com.cargo.base.service.OrgInvoiceService;
import com.cargo.base.vo.OrgInvoiceVo;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: xinzs
 * @Date: 2020/11/16 17:31
 */
@Service
public class OrgInvoiceServiceImpl extends ServiceImpl<OrgInvoiceMapper, OrgInvoiceEntity> implements OrgInvoiceService {

    @Autowired
    OrgInvoiceMapper orgInvoiceMapper;

    @Override
    public void addOrgInvoice(OrgInvoiceDto dto) {

        String orgId = ShiroUtil.getOrgId();
        String userId = ShiroUtil.currentUserId();
        OrgInvoiceEntity orgInvoiceEntity = new OrgInvoiceEntity();
        BeanUtils.copyProperties(dto,orgInvoiceEntity);

        orgInvoiceEntity.setOrgId(orgId);
        orgInvoiceEntity.setCreateUser(userId);

//        1、校验

        orgInvoiceMapper.insert(orgInvoiceEntity);
    }

    @Override
    public void updateOrgInvoice(OrgInvoiceDto dto) {
        String orgId = ShiroUtil.getOrgId();
        String orgInvoiceId = dto.getOrgInvoiceId();
        if(orgInvoiceId==null||orgInvoiceId.isEmpty()){
            throw new BussException("发票信息不得为空");
        }

        OrgInvoiceEntity orgInvoiceEntity = orgInvoiceMapper.selectById(orgInvoiceId);
        if(orgInvoiceEntity==null){
            throw new BussException("发票信息为空");
        }
        BeanUtils.copyProperties(dto,orgInvoiceEntity);
        orgInvoiceEntity.setOrgId(orgId);

        orgInvoiceMapper.updateById(orgInvoiceEntity);
    }

    @Override
    public OrgInvoiceVo orgInvoiceDetail(OrgInvoiceDto dto) {
        String orgInvoiceId = dto.getOrgInvoiceId();
        if(orgInvoiceId==null||orgInvoiceId.isEmpty()){
            throw new BussException("发票信息不得为空");
        }

        OrgInvoiceEntity orgInvoiceEntity = orgInvoiceMapper.selectById(orgInvoiceId);
        OrgInvoiceVo convert = BeanConverter.convert(OrgInvoiceVo.class, orgInvoiceEntity);

        return convert;
    }

    @Override
    public IPage<OrgInvoiceVo> orgInvoiceList(OrgInvoiceDto dto, Page<OrgInvoiceEntity> page) {

        String orgId = ShiroUtil.getOrgId();
        QueryWrapper<OrgInvoiceEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id", orgId);
//        if(dto.getBankName()!=null && !dto.getBankName().isEmpty()){
//            wrapper.eq("bank_name",dto.getBankName());
//        }
//        ......
        wrapper.eq("is_able", 1);

        IPage<OrgInvoiceEntity> orgInvoiceEntityIPage = orgInvoiceMapper.selectPage(page, wrapper);
        IPage<OrgInvoiceVo> convert = BeanConverter.convert(OrgInvoiceVo.class, orgInvoiceEntityIPage);
        return convert;
    }
}
