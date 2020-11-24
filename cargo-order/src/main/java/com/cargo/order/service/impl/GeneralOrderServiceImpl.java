package com.cargo.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.feign.service.BaseFeignService;
import com.cargo.order.dto.GeneralOrderDto;
import com.cargo.order.entity.*;
import com.cargo.order.mapper.*;
import com.cargo.order.service.GeneralOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.order.vo.CarVo;
import com.cargo.order.vo.GeneralOrderDetailVo;
import com.cargo.order.vo.GeneralOrderGoodsVo;
import com.cargo.order.vo.GeneralOrderVo;
import com.cargo.waybill.entity.WaybillEntity;
import com.cargo.waybill.mapper.WaybillMapper;
import com.cargo.waybill.service.WaybillService;
import com.cargo.waybill.vo.WaybillVo;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.utils.RedisUtil;
import com.commom.utils.ResponseInfo;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-11-09
 */
@Service
public class GeneralOrderServiceImpl extends ServiceImpl<GeneralOrderMapper, GeneralOrderEntity> implements GeneralOrderService {
    @Autowired
    private GeneralOrderMapper generalOrderMapper;
    @Autowired
    private GeneralOrderGoodsMapper generalOrderGoodsMapper;
    @Autowired
    private IntentionOrderMapper intentionOrderMapper;
    @Autowired
    private IntentionLogMapper intentionLogMapper;
    @Autowired
    private IntentionOrderGoodsMapper intentionOrderGoodsMapper;
    @Autowired
    private BaseFeignService baseFeignService;
    @Autowired
    private WaybillService waybillService;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");


    @Override
    public Page<GeneralOrderVo> queryForList(GeneralOrderDto dto, Page<GeneralOrderVo> page) {
        dto.setLoginUserId(ShiroUtil.getUserId());
        List<GeneralOrderVo> listVo = generalOrderMapper.queryForList(dto);
        if(listVo.size() != 0){
            listVo.stream().forEach(item ->{
                QueryWrapper<GeneralOrderGoodsEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("general_order_id",item.getGeneralOrderId());
                List<GeneralOrderGoodsEntity> intentionOrderGoodsEntities = generalOrderGoodsMapper.selectList(queryWrapper);
                ArrayList<GeneralOrderGoodsVo> listGoodsVo = new ArrayList<>();
                intentionOrderGoodsEntities.stream().forEach(intGood ->{
                    GeneralOrderGoodsVo orderGoodsVo = new GeneralOrderGoodsVo();
                    BeanUtils.copyProperties(intGood,orderGoodsVo);
                    listGoodsVo.add(orderGoodsVo);
                });
                item.setGoodsVoList(listGoodsVo);

                QueryWrapper<WaybillEntity> queryWr = new QueryWrapper<>();
                queryWr.eq("general_order_id",item.getGeneralOrderId());
                List<WaybillEntity> list = waybillService.list(queryWr);
                List<WaybillVo> listBillVo = new ArrayList<>();
                list.stream().forEach(s ->{
                    WaybillVo waybillVo = new WaybillVo();
                    BeanUtils.copyProperties(s,waybillVo);
                    CarVo car = baseFeignService.getCar(waybillVo.getCarId());
                    if(car != null){
                        waybillVo.setCarTypeName(car.getCarTypeName());
                    }
                    listBillVo.add(waybillVo);
                });
                item.setListBillVo(listBillVo);
            });
            listVo = baseFeignService.converOrder(listVo);
        }
        page.setRecords(listVo);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(GeneralOrderDto dto) {
        IntentionOrderEntity intentionOrderEntity = intentionOrderMapper.selectById(dto.getIntentionOrderId());
        if(intentionOrderEntity == null){
            throw  new BussException("查不到意向单 IntentionOrderId："+dto.getIntentionOrderId());
        }
        GeneralOrderEntity entity = new GeneralOrderEntity();
        BeanUtils.copyProperties(intentionOrderEntity,entity);
        entity.setCreateUser(ShiroUtil.getUserId());
        entity.setGeneralOrderStatus("0");
        entity.setGeneralOrderNo(getGeneralOrderNo());
        generalOrderMapper.insert(entity);


        QueryWrapper<IntentionOrderGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("intention_order_id",dto.getIntentionOrderId());

        List<IntentionOrderGoodsEntity> intentionOrderGoodsEntities = intentionOrderGoodsMapper.selectList(queryWrapper);
        intentionOrderGoodsEntities.stream().forEach(item ->{
            GeneralOrderGoodsEntity orderGoodsEntity = new GeneralOrderGoodsEntity();
            BeanUtils.copyProperties(item,orderGoodsEntity);
            orderGoodsEntity.setCreateUser(ShiroUtil.getUserId());
            orderGoodsEntity.setGeneralOrderId(entity.getGeneralOrderId());
            generalOrderGoodsMapper.insert(orderGoodsEntity);
        });

        return true;
    }
    public String getGeneralOrderNo(){
        String getGeneralOrderIdlock = "generalOrderId:add";
        String time = dateFormat.format(new Date());
        String generalOrderIdKey = "generalOrderId:"+time;
        RedisUtil.tryLock(getGeneralOrderIdlock,"1",100);
        String id = time+"0001";

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
    @Override
    public GeneralOrderVo queryForOne(String generalOrderId) {
        GeneralOrderVo generalOrderVo = generalOrderMapper.queryForOne(generalOrderId);
        if(generalOrderVo == null){
            throw  new BussException("查不到订单 GeneralOrderId："+generalOrderId);
        }
        QueryWrapper<GeneralOrderGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("general_order_id",generalOrderId);
        List<GeneralOrderGoodsEntity> intentionOrderGoodsEntities = generalOrderGoodsMapper.selectList(queryWrapper);
        ArrayList<GeneralOrderGoodsVo> listGoodsVo = new ArrayList<>();
        intentionOrderGoodsEntities.stream().forEach(intGood ->{
            GeneralOrderGoodsVo orderGoodsVo = new GeneralOrderGoodsVo();
            BeanUtils.copyProperties(intGood,orderGoodsVo);
            listGoodsVo.add(orderGoodsVo);
        });
        generalOrderVo.setGoodsVoList(listGoodsVo);
        ArrayList<GeneralOrderVo> listVo = new ArrayList<>();
        listVo.add(generalOrderVo);
        generalOrderVo = baseFeignService.converOrder(listVo).get(0);
        QueryWrapper<WaybillEntity> queryWr = new QueryWrapper<>();
        queryWr.eq("general_order_id",generalOrderVo.getGeneralOrderId());
        List<WaybillEntity> list = waybillService.list(queryWr);
        List<WaybillVo> listBillVo = new ArrayList<>();
        list.stream().forEach(item ->{
            WaybillVo waybillVo = new WaybillVo();
            BeanUtils.copyProperties(item,waybillVo);
            CarVo car = baseFeignService.getCar(waybillVo.getCarId());
            if(car != null){
                waybillVo.setCarTypeName(car.getCarTypeName());
            }
            listBillVo.add(waybillVo);
        });
        generalOrderVo.setListBillVo(listBillVo);
        return generalOrderVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(GeneralOrderDto dto) {
        GeneralOrderEntity oldEntity = generalOrderMapper.selectById(dto.getGeneralOrderId());
        if(oldEntity == null){
            throw new BussException("查不到订单 GeneralOrderId："+dto.getGeneralOrderId());
        }


        UpdateWrapper<GeneralOrderEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("general_order_id",dto.getGeneralOrderId());
        updateWrapper.set("general_order_status",dto.getGeneralOrderStatus());
        updateWrapper.set("update_user",ShiroUtil.getUserId());
        if(dto.getRefuseRemark() != null){
            updateWrapper.set("refuse_remark",dto.getRefuseRemark());
        }
        boolean update = this.update(updateWrapper);
        if(!update){
            throw new BussException("更新订单状态失败");
        }
        String context = "";
        if(dto.getType().equals(GeneralOrderEntity.TYPE_1)){
            context = "货主 "+ShiroUtil.getUserId()+" 操作";
        }else if(dto.getType().equals(GeneralOrderEntity.TYPE_2)){
            context = "承运商 "+ShiroUtil.getUserId()+" 操作";
        }


        IntentionLogEntity intEntity = new IntentionLogEntity();
        intEntity.setOldStatus(oldEntity.getGeneralOrderStatus());
        intEntity.setNewStatus(dto.getGeneralOrderStatus());
        intEntity.setCreateUser(ShiroUtil.getUserId());
        intEntity.setContext(context);
        intEntity.setIntentionType(dto.getType());
        intEntity.setRemark(dto.getRefuseRemark());
        intEntity.setType(IntentionLogEntity.TYPE_20);
        int insert = intentionLogMapper.insert(intEntity);
        if(insert != 1){
            throw new BussException("新增意向单状态日志失败");
        }

        return true;
    }

    /**
     * 根据订单id 查询订单详情
     *
     * @param t
     * @return
     */
    @Override
    public List<GeneralOrderDetailVo> selectDetails(List<String> t) {
        if (ObjectUtils.isEmpty(t)) {
            return null;
        }
        List<GeneralOrderDetailVo> list =    this.baseMapper.selectDetails(t);
        return list;
    }

    @Override
    public boolean getWayBillByGenId(GeneralOrderDto dto) {
        generalOrderMapper.getWayBillByGenId(dto);
        return false;
    }

    @Override
    public JSONObject orderCensus(GeneralOrderDto dto) {
        JSONObject result = new JSONObject();
        QueryWrapper<GeneralOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("general_order_status",0);
        if(dto.getStartTime() != null && dto.getEndTime() != null) {
            queryWrapper.ge("gmt_create", dto.getStartTime()).le("gmt_create", dto.getEndTime());
        }
        result.put("orderCreate",this.count(queryWrapper));
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("general_order_status",1);
        if(dto.getStartTime() != null && dto.getEndTime() != null) {
            queryWrapper.ge("gmt_create", dto.getStartTime()).le("gmt_create", dto.getEndTime());
        }
        result.put("orderCancel",this.count(queryWrapper));
        //generalOrderMapper.orderCensus(dto);
        return null;
    }
}
