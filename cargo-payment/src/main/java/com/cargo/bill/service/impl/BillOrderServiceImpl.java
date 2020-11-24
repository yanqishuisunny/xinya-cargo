package com.cargo.bill.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.account.entity.AccountOrderEntity;
import com.cargo.account.mapper.AccountOrderMapper;
import com.cargo.alipay.model.dto.TradePrecreateDto;
import com.cargo.alipay.service.AliPayService;
import com.cargo.bill.common.BillOrderEnum;
import com.cargo.bill.dto.BillOrderDto;
import com.cargo.bill.dto.BillOrderListDto;
import com.cargo.bill.dto.BillOrderListOwnerDto;
import com.cargo.bill.entity.BillOrderEntity;
import com.cargo.bill.entity.WaybillOrderEntity;
import com.cargo.bill.mapper.BillOrderMapper;
import com.cargo.bill.mapper.WaybillOrderMapper;
import com.cargo.bill.service.BillOrderService;
import com.cargo.bill.vo.AliPayBillOrderVo;
import com.cargo.bill.vo.BillOrderAnalyseVo;
import com.cargo.bill.vo.BillOrderVo;
import com.cargo.bill.vo.BillOrderSummaryVo;
import com.cargo.feign.dto.WaybillDto;
import com.cargo.feign.entity.OrgEntity;
import com.cargo.feign.service.BaseFeignService;
import com.cargo.feign.vo.CarrierdetailVo;
import com.cargo.feign.vo.WaybillVo;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.utils.RedisUtil;
import com.commom.utils.ResponseInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 帐单表 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Service
public class BillOrderServiceImpl extends ServiceImpl<BillOrderMapper, BillOrderEntity> implements BillOrderService {

    @Autowired
    BillOrderMapper billOrderMapper;
    @Autowired
    AccountOrderMapper accountOrderMapper;
    @Autowired
    WaybillOrderMapper waybillOrderMapper;
    @Autowired
    AliPayService aliPayService;
    @Autowired
    BaseFeignService baseFeignService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    @Override
    public void addBillOrder(BillOrderDto dto) {

        //1.订单系统这里的是角色ID不是所属组织的ID,通过角色ID找到orgID
        String orgId = dto.getOrgId();
        String ownerId = dto.getOwnerOrgId();
        String carrierOrgId = null;
        String ownerOrgId = null;
        try {
            //调订单角色相关接口

            //通过承运商角色ID，获取承运商企业ID
            ResponseInfo<CarrierdetailVo> carrier = baseFeignService.selectCarrierByRoleId(orgId);
            if(carrier!=null){
                CarrierdetailVo carrierdetailVo = carrier.getData();
                carrierOrgId = carrierdetailVo.getOrgId();
            }

            //通过货主角色ID，获取货主企业ID
            OrgEntity ownerEntity = baseFeignService.getOrgById(ownerId);
            if(ownerEntity!=null){
                ownerOrgId = ownerEntity.getOrgId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BussException("获取角色所属组织ID失败");
        }



        //2.创建账单信息
        BillOrderEntity billOrderEntity = new BillOrderEntity();
        BeanUtils.copyProperties(dto,billOrderEntity);
//        设置角色的组织ID
        billOrderEntity.setOrderId(carrierOrgId);
        billOrderEntity.setOwnerOrgId(ownerOrgId);
        billOrderMapper.insert(billOrderEntity);



        //3.通过订单ID找到运单相关信息
        String orderId = billOrderEntity.getOrderId();
        List<WaybillVo> records = null;
        try {
            //通过订单ID找到运单相关信息
            WaybillDto waybillDto = new WaybillDto();
            waybillDto.setGeneralOrderId(orderId);
            ResponseInfo<Page<WaybillVo>> list = baseFeignService.list(waybillDto);
            Page<WaybillVo> data = list.getData();
            records = data.getRecords();

        } catch (Exception e) {
            e.printStackTrace();
            throw new BussException("获取订单:"+billOrderEntity.getOrderNo()+"_的运单信息失败");
        }


        //4.新增账单对应的运单的详细记录
        String billOrderId = billOrderEntity.getBillOrderId();

        if(records!=null && records.size()>0){

            for (WaybillVo record : records) {

                WaybillOrderEntity waybillOrderEntity = new WaybillOrderEntity();
                BeanUtils.copyProperties(record,waybillOrderEntity);

                waybillOrderEntity.setBillOrderId(billOrderId);
//          车牌信息.司机信息......

                waybillOrderMapper.insert(waybillOrderEntity);
            }
        }

    }

    @Override
    public IPage<BillOrderVo> billOrderList(BillOrderListDto dto, Page<BillOrderEntity> page) {
        String orgId = ShiroUtil.getOrgId();
        QueryWrapper<BillOrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("owner_org_id",orgId);
        if(dto.getOrderNo()!=null&&!dto.getOrderNo().isEmpty()){
            wrapper.eq("order_no",dto.getOrderNo());
        }
        if(dto.getBillStatus()!=null&&!dto.getBillStatus().isEmpty()){
            wrapper.eq("bill_status",dto.getBillStatus());
        }
        if(dto.getStartTime()!=null&&!dto.getStartTime().isEmpty()){
            wrapper.ge("transport_finish_time",dto.getStartTime());
        }
        if(dto.getEndTime()!=null&&!dto.getEndTime().isEmpty()){
            wrapper.le("transport_finish_time",dto.getEndTime());
        }
        wrapper.eq("is_able",1);
        IPage<BillOrderEntity> billOrderEntityIPage = billOrderMapper.selectPage(page, wrapper);

        IPage<BillOrderVo> convert = BeanConverter.convert(BillOrderVo.class, billOrderEntityIPage);

        return convert;
    }

    @Override
    public void examineBillOrder(BillOrderDto dto) {
        String billOrderId = dto.getBillOrderId();
        BillOrderEntity billOrderEntity = billOrderMapper.selectById(billOrderId);
        billOrderEntity.setBillStatus(BillOrderEnum.STATUS_TO_BE_PAID.getType().toString());
        billOrderMapper.updateById(billOrderEntity);
    }

    @Override
    public AliPayBillOrderVo payBillOrder(BillOrderDto dto) throws Exception {

        BillOrderEntity billOrderEntity = billOrderMapper.selectById(dto.getBillOrderId());

        if(billOrderEntity==null){
           throw new BussException("未找到该账单！");
        }
        String orderNo = billOrderEntity.getOrderNo();
        String orgName = billOrderEntity.getOrgName();
        String subject = orderNo+"_"+orgName;

        TradePrecreateDto tradePrecreateDto = new TradePrecreateDto();

        tradePrecreateDto.setOutTradeNo(orderNo);
        tradePrecreateDto.setTotalAmount(billOrderEntity.getAmountToBePaid().toString());
        tradePrecreateDto.setSubject(subject);

        String qrCode = null;
        try {
            AlipayTradePrecreateResponse alipayTradePrecreateResponse = aliPayService.tradePrecreate(tradePrecreateDto);
            qrCode = alipayTradePrecreateResponse.getQrCode();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BussException("获取收款二维码失败");
        }

        AliPayBillOrderVo aliPayBillOrderVo = new AliPayBillOrderVo();
        aliPayBillOrderVo.setQrCode(qrCode);
        aliPayBillOrderVo.setCollectionOrg("新雅网络货运平台");
        aliPayBillOrderVo.setCollectionCode("这是新雅收款账号");

        return aliPayBillOrderVo;
    }

    @Override
    public void mergeBillOrder(List<String> billOrderIds) {

        String userId = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();

        if(billOrderIds==null||billOrderIds.size()==0){
            throw new BussException("请选中需要合并的账单");
        }

        //1、校验是否曼珠合并账单条件
        BigDecimal amountOfBills = BigDecimal.ZERO;//合并账单总金额
        for (String billOrderId : billOrderIds) {
            BillOrderEntity billOrderEntity = billOrderMapper.selectById(billOrderId);

            //获取参数判断是否可以合并
            String orderNo = billOrderEntity.getOrderNo();
            BigDecimal amountToBePaid = billOrderEntity.getAmountToBePaid();
            BigDecimal amountPaid = billOrderEntity.getAmountPaid();
            String accounType = billOrderEntity.getAccountType();
            String billStatus = billOrderEntity.getBillStatus();

            if(amountPaid==null||!amountToBePaid.equals(amountPaid)){
                throw new BussException("账单:"+orderNo+"已付金额不正确!");
            }
            if(billStatus!=null&&!billStatus.equals("3")){
                throw new BussException("账单:"+orderNo+"未付款!");
            }
            if(accounType==null||accounType.equals("1")){
                throw new BussException("账单:"+orderNo+"已合账!");
            }

            //……

            //计算账目数值
            amountOfBills = amountOfBills.add(amountPaid);

        }

        //2、合并账单
        AccountOrderEntity accountOrderEntity = new AccountOrderEntity();
        accountOrderEntity.setAmountOfBills(amountOfBills);
        accountOrderEntity.setAccountStatus("2");
        accountOrderEntity.setOrgId(orgId);
        accountOrderEntity.setAccountNo(getAccountOrderNo());
        accountOrderEntity.setBillCount(billOrderIds.size());
        //……
        accountOrderMapper.insert(accountOrderEntity);
        String accountOrderId = accountOrderEntity.getAccountOrderId();

        //3、账单表关联账目ID
        for (String billOrderId : billOrderIds) {
            BillOrderEntity billOrderEntity = billOrderMapper.selectById(billOrderId);

            billOrderEntity.setAccountOrderId(accountOrderId);
            billOrderEntity.setAccountType("1");
            billOrderMapper.updateById(billOrderEntity);
        }

    }

    @Override
    public IPage<BillOrderVo> billOrderListOwner(BillOrderListOwnerDto dto,Page<BillOrderEntity> page) {
        String orgId = ShiroUtil.getOrgId();
        QueryWrapper<BillOrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id",orgId);
        if(dto.getOrderNo()!=null&&!dto.getOrderNo().isEmpty()){
            wrapper.eq("order_no",dto.getOrderNo());
        }
        if(dto.getBillStatus()!=null&&!dto.getBillStatus().isEmpty()){
            wrapper.ge("bill_status",dto.getBillStatus());
        }
        if(dto.getEndTime()!=null&&!dto.getEndTime().isEmpty()){
            wrapper.le("transport_finish_time",dto.getEndTime());
        }
        wrapper.eq("is_able",1);
        IPage<BillOrderEntity> billOrderEntityIPage = billOrderMapper.selectPage(page,wrapper);
        IPage<BillOrderVo> convert = BeanConverter.convert(BillOrderVo.class, billOrderEntityIPage);
        return convert;
    }

    @Override
    public void pressBillOrder(BillOrderDto dto) {
        String orgId = ShiroUtil.getOrgId();

        //1.推送催款消息

        //2.更新账单信息
        String billOrderId = dto.getBillOrderId();
        BillOrderEntity billOrderEntity = billOrderMapper.selectById(billOrderId);
        Integer pressCount = billOrderEntity.getPressCount();

        billOrderEntity.setPressCount(pressCount+1);
        billOrderEntity.setPressTime(new Date().toString());
        billOrderMapper.updateById(billOrderEntity);

    }

    @Override
    public BillOrderAnalyseVo  analysisBillOrder(BillOrderListDto dto) throws ParseException {

        String orgId = ShiroUtil.getOrgId();//承运商
        dto.setOrgId(orgId);
        String ownerOrgId = dto.getOwnerOrgId();//货主

        String startTime = dto.getStartTime();
        if(startTime==null || startTime.isEmpty()){
            Calendar c = Calendar.getInstance();

            c.setTime(new Date());
            int day1 = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day1 - 15);

            startTime = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
            dto.setStartTime(startTime);
        }
        String endTime = dto.getEndTime();
        if(endTime==null || endTime.isEmpty()){
            endTime = new Date().toString();
            dto.setEndTime(endTime);
        }

        //按条件查记录
        QueryWrapper<BillOrderEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id",orgId);
        if(ownerOrgId!=null&&!ownerOrgId.equals("")){
            wrapper.eq("owner_org_id",ownerOrgId);
        }
        wrapper.ge("transport_finish_time",startTime);
        wrapper.le("transport_finish_time",endTime);
        List<BillOrderEntity> billOrderEntities = billOrderMapper.selectList(wrapper);

        //已收货款/代售货款
        BillOrderSummaryVo billOrderWay = new BillOrderSummaryVo();
        BigDecimal amountToBePaids = BigDecimal.ZERO;
        BigDecimal amountPaids = BigDecimal.ZERO;

        for (BillOrderEntity billOrderEntity : billOrderEntities) {
            BigDecimal amountToBePaid = billOrderEntity.getAmountToBePaid();
            amountToBePaids = amountToBePaids.add(amountToBePaid);
            billOrderWay.setAmountToBePaids(amountToBePaids);

            BigDecimal amountPaid = billOrderEntity.getAmountPaid();
            amountPaids = amountPaids.add(amountPaid);
            billOrderWay.setAmountPaids(amountPaids);
        }


        //货主分组
        List<BillOrderSummaryVo> billOrderOwners = billOrderMapper.billOrderOwnerList(dto);


        //路线分账
        List<BillOrderSummaryVo> billOrderWays = billOrderMapper.billOrderWayList(dto);
        for (BillOrderSummaryVo billOrderWayVo : billOrderWays) {
            BigDecimal totalAmount = billOrderWayVo.getTotalAmount();
            Integer num = billOrderWayVo.getNum();

            if(totalAmount!=null&&num!=null){
                BigDecimal price = totalAmount.divide(new BigDecimal(num), 0, BigDecimal.ROUND_HALF_UP);
                billOrderWayVo.setPrice(price);
            }
        }

        BillOrderAnalyseVo billOrderAnalyseVo = new BillOrderAnalyseVo();
        billOrderAnalyseVo.setPayAmount(billOrderWay);
        billOrderAnalyseVo.setOwner(billOrderOwners);
        billOrderAnalyseVo.setWay(billOrderWays);

        return billOrderAnalyseVo;
    }


    public String getAccountOrderNo(){
        String getGeneralOrderIdlock = "AccountOrder:add";
        String time = dateFormat.format(new Date());
        String generalOrderIdKey = "AccountOrder:"+time;
        RedisUtil.tryLock(getGeneralOrderIdlock,"1",100);
        String id = "ZM"+time+"0001";

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
