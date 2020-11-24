package com.cargo.invoice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.account.entity.AccountRunningEntity;
import com.cargo.account.mapper.AccountRunningMapper;
import com.cargo.account.service.AccountRunningService;
import com.cargo.invoice.dto.InvoiceDto;
import com.cargo.invoice.entity.InvoiceEntity;
import com.cargo.invoice.mapper.InvoiceMapper;
import com.cargo.invoice.service.InvoiceService;
import com.cargo.invoice.vo.InvoiceVo;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.utils.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 税票表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, InvoiceEntity> implements InvoiceService {

    @Autowired
    InvoiceMapper invoiceMapper;
    @Autowired
    AccountRunningService accountRunningService;
    @Autowired
    AccountRunningMapper accountRunningMapper;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    @Override
    public Page<InvoiceVo> invoiceList(InvoiceDto dto, Page<InvoiceVo> pag) {
        List<InvoiceVo> invoiceVos = invoiceMapper.invoiceList(dto, pag);
        pag.setRecords(invoiceVos);
        return pag;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createInvoice(InvoiceDto dto) {
        //1、校验
        BigDecimal invoiceAmount = dto.getInvoiceAmount();

        BigDecimal invoiceAmountLast = accountRunningService.getInvoiceAmount();
        int i = invoiceAmountLast.compareTo(invoiceAmount);
        if(i<0){
            throw new BussException("可开票金额不足，请重新填写。");
        }


        //2、新增发票记录
        String orgId = ShiroUtil.getOrgId();
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        try {
            BeanUtils.copyProperties(dto,invoiceEntity);

            invoiceEntity.setInvoiceOrgId(orgId);
            //设置流水号
            invoiceEntity.setInvoiceNo(getAccountOrderNo());
            //流水状态（1、申请中；2、撤回；3、开票中；4、完成)
            invoiceEntity.setInvoiceStatus("1");

            invoiceMapper.insert(invoiceEntity);
        } catch (BeansException e) {
            e.printStackTrace();
            throw new BussException("新增发票记录失败。");
        }


        //3、新增账目流水表
        try {
            AccountRunningEntity accountRunningEntity = new AccountRunningEntity();
            accountRunningEntity.setOrgId(orgId);
            accountRunningEntity.setAccountRunningNo(invoiceEntity.getInvoiceNo());
//        流水类型：（1、入；2、出）
            accountRunningEntity.setAccountRunningType("2");
//        流水用途：（1、入账；2、出账；3、开票；4、撤回开票）
            accountRunningEntity.setAccountRunningPurpose("3");
//        流水状态（1、申请中；2、撤回；3、开票中；4、完成（出账入账直接是完成）；）
            accountRunningEntity.setRunningStatus("1");
//        流水金额
            accountRunningEntity.setRunningAmount(invoiceAmount);
//        可开票总金额
            invoiceAmountLast = invoiceAmountLast.divide(invoiceAmount);
            accountRunningEntity.setInvoiceAmount(invoiceAmountLast);
            accountRunningMapper.insert(accountRunningEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussException("新增账目流水表失败。");
        }


    }

    @Override
    public InvoiceEntity invoiceDetail(InvoiceDto dto) {
        String invoiceId = dto.getInvoiceId();
        QueryWrapper<InvoiceEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("invoice_id",invoiceId);
        wrapper.eq("is_able",1);
        InvoiceEntity invoiceEntity = invoiceMapper.selectOne(wrapper);

        return invoiceEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recallInvoice(InvoiceDto dto) {
        InvoiceEntity invoiceEntity = invoiceDetail(dto);
        if(invoiceEntity==null){
            throw new BussException("请稍后再试");
        }
//        流水状态（1、申请中；2、撤回；3、开票中；4、完成)
        String invoiceStatus = invoiceEntity.getInvoiceStatus();
        if(invoiceStatus==null||!invoiceStatus.equals(1)){
            throw new BussException("当前开票状态不可撤回");
        }

        //更新开票状态
        try {
            invoiceEntity.setInvoiceStatus("2");
            invoiceMapper.updateById(invoiceEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussException("更新发票记录失败。");
        }

        //新增账目流水表记录
        try {
            String orgId = ShiroUtil.getOrgId();
            BigDecimal invoiceAmount = invoiceEntity.getInvoiceAmount();
            BigDecimal invoiceAmountLast = accountRunningService.getInvoiceAmount();

            AccountRunningEntity accountRunningEntity = new AccountRunningEntity();
            accountRunningEntity.setOrgId(orgId);
            accountRunningEntity.setAccountRunningNo(invoiceEntity.getInvoiceNo());
//        流水类型：（1、入；2、出）
            accountRunningEntity.setAccountRunningType("1");
//        流水用途：（1、入账；2、出账；3、开票；4、撤回开票）
            accountRunningEntity.setAccountRunningPurpose("4");
//        流水状态（1、申请中；2、撤回；3、开票中；4、完成（出账入账直接是完成）；）
            accountRunningEntity.setRunningStatus("2");
//        流水金额
            accountRunningEntity.setRunningAmount(invoiceAmount);
//        可开票总金额
            invoiceAmountLast = invoiceAmountLast.add(invoiceAmount);
            accountRunningEntity.setInvoiceAmount(invoiceAmountLast);
            accountRunningMapper.insert(accountRunningEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussException("新增账目流水表失败。");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInvoice(InvoiceDto dto) {
        InvoiceEntity invoiceEntity = invoiceDetail(dto);
        if(invoiceEntity==null){
            throw new BussException("请稍后再试");
        }
//        流水状态（1、申请中；2、撤回；3、开票中；4、完成；)
        String invoiceStatus = invoiceEntity.getInvoiceStatus();
        if(invoiceStatus==null||!invoiceStatus.equals(2)){
            throw new BussException("当前开票状态不可删除");
        }

        //更新开票状态
        try {
            invoiceMapper.deleteById(invoiceEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussException("更新发票记录失败。");
        }
    }

    @Override
    public void acceptInvoice(InvoiceDto dto) {

        InvoiceEntity invoiceEntity = invoiceDetail(dto);
        if(invoiceEntity==null){
            throw new BussException("请稍后再试");
        }
//        流水状态（1、申请中；2、撤回；3、开票中；4、完成)
        String invoiceStatus = invoiceEntity.getInvoiceStatus();
        if(invoiceStatus==null||!invoiceStatus.equals(1)){
            throw new BussException("当前开票状态不可撤回");
        }

        //更新开票状态
        try {
            invoiceEntity.setInvoiceStatus("3");
            invoiceMapper.updateById(invoiceEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussException("更新发票记录失败。");
        }

    }

    @Override
    public void uploadInvoice(InvoiceDto dto) {
        InvoiceEntity invoiceEntity = invoiceDetail(dto);
        if(invoiceEntity==null){
            throw new BussException("请稍后再试");
        }
//        流水状态（1、申请中；2、撤回；3、开票中；4、完成)
        String invoiceStatus = invoiceEntity.getInvoiceStatus();
        if(invoiceStatus==null||!invoiceStatus.equals(3)){
            throw new BussException("当前状态不可上传发票");
        }

        //更新开票状态
        try {
            BeanUtils.copyProperties(dto,invoiceEntity);
            invoiceEntity.setInvoiceStatus("4");
            invoiceMapper.updateById(invoiceEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussException("更新发票记录失败。");
        }
    }

    public String getAccountOrderNo(){
        String getGeneralOrderIdlock = "AccountOrder:add";
        String time = dateFormat.format(new Date());
        String generalOrderIdKey = "AccountOrder:"+time;
        RedisUtil.tryLock(getGeneralOrderIdlock,"1",100);
        String id = "IN"+time+"0001";

        boolean b = RedisUtil.hasKey(generalOrderIdKey);
        if(b){
            Long lon = Long.parseLong(RedisUtil.get(generalOrderIdKey).toString())+1;
            RedisUtil.set(generalOrderIdKey,lon);
            id=lon.toString();
        }else{
            RedisUtil.set(generalOrderIdKey,id,60);
        }


        RedisUtil.unlock(getGeneralOrderIdlock);
        return id;
    }
}
