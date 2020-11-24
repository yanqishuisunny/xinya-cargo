package com.cargo.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.account.dto.AccountOrderDto;
import com.cargo.account.dto.AccountOrderListDto;
import com.cargo.account.entity.AccountOrderEntity;
import com.cargo.account.entity.AccountRunningEntity;
import com.cargo.account.mapper.AccountOrderMapper;
import com.cargo.account.mapper.AccountRunningMapper;
import com.cargo.account.service.AccountOrderService;
import com.cargo.account.vo.AccountOrderDetailVo;
import com.cargo.account.vo.AccountOrderVo;
import com.cargo.bill.entity.BillOrderEntity;
import com.cargo.bill.mapper.BillOrderMapper;
import com.cargo.bill.vo.BillOrderAccountDetailVo;
import com.cargo.feign.entity.UserInfoEntity;
import com.cargo.feign.service.BaseFeignService;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 帐目表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Service
public class AccountOrderServiceImpl extends ServiceImpl<AccountOrderMapper, AccountOrderEntity> implements AccountOrderService {

    @Autowired
    AccountOrderMapper accountOrderMapper;
    @Autowired
    AccountRunningMapper accountRunningMapper;
    @Autowired
    BillOrderMapper billOrderMapper;
    @Autowired
    BaseFeignService baseFeignService;



    @Override
    public Page<AccountOrderVo> accountOrderMapperList(AccountOrderListDto dto, Page<AccountOrderVo> page) {

        List<AccountOrderVo> accountOrderVos = accountOrderMapper.accountOrderMapperList(dto, page);

        page.setRecords(accountOrderVos);

        return page;
    }

    @Override
    public void updateAccountOrder(AccountOrderDto dto) {

        String orgId = ShiroUtil.getOrgId();
        List<String> accountOrderIds = dto.getAccountOrderIds();
        if(accountOrderIds==null||accountOrderIds.size()==0){
            throw new BussException("请选择需要锁账单的批次");
        }
        //账目状态（1、已锁；2、未锁)
        String accountStatus = dto.getAccountStatus();

        for (String accountOrderId : accountOrderIds) {
            //1、更新账目状态
            AccountOrderEntity accountOrderEntity = accountOrderMapper.selectById(accountOrderId);
            accountOrderEntity.setAccountStatus(accountStatus);
            accountOrderMapper.updateById(accountOrderEntity);


            //2、账目流水表新增记录
            //2.1获取最新的可开票额度
            BigDecimal invoiceAmount = BigDecimal.ZERO;
            QueryWrapper<AccountRunningEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("gmt_create");
            queryWrapper.eq("org_id",orgId);
            queryWrapper.last("limit 1");
            AccountRunningEntity lastAccountRunningEntity = accountRunningMapper.selectOne(queryWrapper);
            if(lastAccountRunningEntity!=null){
                invoiceAmount = lastAccountRunningEntity.getInvoiceAmount();
            }

            //2.2账目流水表新增记录
            AccountRunningEntity accountRunningEntity = new AccountRunningEntity();
            BeanUtils.copyProperties(accountOrderEntity,accountRunningEntity);
            accountRunningEntity.setAccountRunningNo(accountOrderEntity.getAccountNo());
            //账目状态（1、已锁；2、未锁)
            if(accountStatus.equals(1)){
//                流水类型：（1、入；2、出）
                accountRunningEntity.setAccountRunningType("1");
//                流水用途：（1、入账；2、出账；3、开票；4、撤回开票）
                accountRunningEntity.setAccountRunningPurpose("1");
//                流水状态（1、申请中；2、撤回；3、开票中；4、完成（出账入账直接是完成）；）
                accountRunningEntity.setRunningStatus("4");
//                流水金额
                accountRunningEntity.setRunningAmount(accountOrderEntity.getAmountOfBills());
//                可开票总金额
                accountRunningEntity.setInvoiceAmount(invoiceAmount.add(accountOrderEntity.getAmountOfBills()));
                accountRunningEntity.setOrgId(orgId);
            }

            accountRunningMapper.insert(accountRunningEntity);
        }
    }

    @Override
    public AccountOrderDetailVo accountOrderDetail(AccountOrderDto dto) {

        String accountOrderId = dto.getAccountOrderId();
        AccountOrderDetailVo accountOrderDetailVo = new AccountOrderDetailVo();

        //账目信息
        AccountOrderEntity accountOrderEntity = accountOrderMapper.selectById(accountOrderId);
        BeanUtils.copyProperties(accountOrderEntity,accountOrderDetailVo);

        //账单列表
        QueryWrapper<BillOrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("account_order_id",accountOrderId);
        List<BillOrderEntity> billOrderEntities = billOrderMapper.selectList(wrapper);
        List<BillOrderAccountDetailVo> convert = BeanConverter.convert(BillOrderAccountDetailVo.class, billOrderEntities);
        if(convert!=null&&convert.size()>0){
            for (BillOrderAccountDetailVo billOrderAccountDetailVo : convert) {
                String examineUserId = billOrderAccountDetailVo.getExamineUserId();
                UserInfoEntity entity = baseFeignService.findById(examineUserId);
                if(entity!=null){
                    billOrderAccountDetailVo.setUserName(entity.getUserName());
                }
            }
        }
        accountOrderDetailVo.setBillOrderEntities(convert);
        accountOrderDetailVo.setBillCount(billOrderEntities.size());

        return accountOrderDetailVo;
    }
}
