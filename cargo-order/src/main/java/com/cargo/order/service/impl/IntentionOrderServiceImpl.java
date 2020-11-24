package com.cargo.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.feign.entity.AreCodeEntry;
import com.cargo.feign.service.BaseFeignService;
import com.cargo.order.common.IntentionOrderEnum;
import com.cargo.order.dto.GoodsDto;
import com.cargo.order.dto.IntentionOrderDto;
import com.cargo.order.dto.GeneralOrderDto;
import com.cargo.order.entity.IntentionLogEntity;
import com.cargo.order.entity.IntentionOrderEntity;
import com.cargo.order.entity.IntentionOrderGoodsEntity;
import com.cargo.order.mapper.IntentionLogMapper;
import com.cargo.order.mapper.IntentionOrderGoodsMapper;
import com.cargo.order.mapper.IntentionOrderMapper;
import com.cargo.order.service.IntentionOrderGoodsService;
import com.cargo.order.service.IntentionOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.order.service.GeneralOrderService;
import com.cargo.order.vo.IntentionOrderGoodsVo;
import com.cargo.order.vo.IntentionOrderVo;
import com.commom.cache.Constant;
import com.commom.exception.BussException;
import com.commom.gpsUtils.StringUtil;
import com.commom.shiro.ShiroUtil;
import com.commom.utils.GaodeApi;
import com.commom.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-28
 */
@Service
public class IntentionOrderServiceImpl extends ServiceImpl<IntentionOrderMapper, IntentionOrderEntity> implements IntentionOrderService {
    @Autowired
    private IntentionOrderMapper intentionOrderMapper;
    @Autowired
    private IntentionOrderGoodsMapper goodsMapper;

    @Autowired
    private BaseFeignService baseFeignService;

    @Autowired
    private IntentionOrderGoodsService intentionOrderGoodsService;

    @Autowired
    private IntentionLogMapper intentionLogMapper;

    @Autowired
    private GeneralOrderService generalOrderService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    @Override
    public Page<IntentionOrderVo> queryForList(IntentionOrderDto dto, Page<IntentionOrderVo> page) {
        List<IntentionOrderVo> listVo = intentionOrderMapper.queryForList(dto);
        if(listVo.size() != 0){
            listVo.stream().forEach(item ->{
                QueryWrapper<IntentionOrderGoodsEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("intention_order_id",item.getIntentionOrderId());
                List<IntentionOrderGoodsEntity> intentionOrderGoodsEntities = goodsMapper.selectList(queryWrapper);
                ArrayList<IntentionOrderGoodsVo> listGoodsVo = new ArrayList<>();
                intentionOrderGoodsEntities.stream().forEach(intGood ->{
                    IntentionOrderGoodsVo intentionOrderGoodsVo = new IntentionOrderGoodsVo();
                    BeanUtils.copyProperties(intGood,intentionOrderGoodsVo);
                    listGoodsVo.add(intentionOrderGoodsVo);
                });
                item.setGoodsVoList(listGoodsVo);
            });
            listVo = baseFeignService.converIntentionOrder(listVo);
        }
        page.setRecords(listVo);
        return page;
    }

    @Override
    public IntentionOrderVo queryForOne(String intentionOrderId) {
        IntentionOrderEntity entity = intentionOrderMapper.selectById(intentionOrderId);
        IntentionOrderVo vo = new IntentionOrderVo();
        List<IntentionOrderVo> listVo = new ArrayList<>();
        BeanUtils.copyProperties(entity,vo);

        QueryWrapper<IntentionOrderGoodsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("intention_order_id",vo.getIntentionOrderId());
        List<IntentionOrderGoodsEntity> intentionOrderGoodsEntities = goodsMapper.selectList(queryWrapper);
        ArrayList<IntentionOrderGoodsVo> listGoodsVo = new ArrayList<>();
        intentionOrderGoodsEntities.stream().forEach(item ->{
            IntentionOrderGoodsVo intentionOrderGoodsVo = new IntentionOrderGoodsVo();
            BeanUtils.copyProperties(item,intentionOrderGoodsVo);
            listGoodsVo.add(intentionOrderGoodsVo);
        });
        vo.setGoodsVoList(listGoodsVo);
        listVo.add(vo);
        listVo = baseFeignService.converIntentionOrder(listVo);
        return listVo.get(0);
    }

    public String getIntentionOrderNo(){
        String getGeneralOrderIdlock = "IntentionOrderNo:add";
        String time = dateFormat.format(new Date());
        String generalOrderIdKey = "IntentionOrderNo:"+time;
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
    @Transactional(rollbackFor = Exception.class)
    public boolean saveIntentionOrder(IntentionOrderDto dto) {
        IntentionOrderEntity entity = new IntentionOrderEntity();
        String currentUserId = ShiroUtil.getUserId();
        BeanUtils.copyProperties(dto,entity);
        entity.setIntentionOrderNo(getIntentionOrderNo());
        entity.setCreateUser(currentUserId);
        entity.setFromUserId(currentUserId);


        if(dto.getType().equals(IntentionOrderEntity.TYPE_1)){
            //1:意向发起人是货主
            //货主
            entity.setCargoUserId(currentUserId);
            //承运商
            entity.setCarrierUserId(dto.getToUserId());

        }else if(dto.getType().equals(IntentionOrderEntity.TYPE_2)){
            //2：意向发起人是承运商
            //货主
            entity.setCargoUserId(dto.getToUserId());
            //承运商
            entity.setCarrierUserId(currentUserId);

        }else{
            throw new BussException("type未知类型");
        }

        //根据区域编码 去获取发货相应的区域名称
        AreCodeEntry senderByAdCode = baseFeignService.findByAdCode(dto.getSenderAdCode());
        if (!ObjectUtils.isEmpty(senderByAdCode)) {
            //市
            entity.setSenderAreaCityId(senderByAdCode.getCityCode());
            entity.setSenderAreaCityName(senderByAdCode.getCity());
            //区
            entity.setSenderAreaTownId(senderByAdCode.getAreaCode());
            entity.setSenderAreaTownName(senderByAdCode.getArea());
            //省
            entity.setSenderAreaProvinceId(senderByAdCode.getProvinceCode());
            entity.setSenderAreaProvinceName(senderByAdCode.getProvince());
        }
        //根据区域编码 去获取收货相应的区域名称
        AreCodeEntry deliveryByAdCode = baseFeignService.findByAdCode(dto.getDeliveryAdCode());
        if (!ObjectUtils.isEmpty(deliveryByAdCode)) {
            //市
            entity.setDeliveryAreaCityId(deliveryByAdCode.getCityCode());
            entity.setDeliveryAreaCityName(deliveryByAdCode.getCity());
            //区
            entity.setDeliveryAreaTownId(deliveryByAdCode.getAreaCode());
            entity.setDeliveryAreaTownName(deliveryByAdCode.getArea());
            //省
            entity.setDeliveryAreaProvinceId(deliveryByAdCode.getProvinceCode());
            entity.setDeliveryAreaProvinceName(deliveryByAdCode.getProvince());
        }
        StringBuffer originsAddress = new StringBuffer("");
        originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaProvinceName())?"":entity.getSenderAreaProvinceName());
        originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaCityName())?"":entity.getSenderAreaCityName());
        originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaTownName())?"":entity.getSenderAreaTownName());
        originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaDetail())?"":entity.getSenderAreaDetail());
        //发货地经纬度
        String sendGPS = GaodeApi.httpURLConectionGET(originsAddress.toString());
        entity.setSenderProcityName(sendGPS);

        StringBuffer destinationAddress = new StringBuffer("");
        destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaProvinceName())?"":entity.getDeliveryAreaProvinceName());
        destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaCityName())?"":entity.getDeliveryAreaCityName());
        destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaTownName())?"":entity.getDeliveryAreaTownName());
        destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaDetail())?"":entity.getDeliveryAreaDetail());
        //收货地经纬度
        String deliveGPS = GaodeApi.httpURLConectionGET(destinationAddress.toString());
        entity.setDeliveryProcityName(deliveGPS);
        Double distance = GaodeApi.getDistance(sendGPS, deliveGPS);
        entity.setGpsMileage(new BigDecimal(distance));
        int insert = intentionOrderMapper.insert(entity);
        if(insert == 0){
            throw new BussException("意向单添加失败");
        }
        List<GoodsDto> listGoods = dto.getListGoods();
        listGoods.stream().forEach(item ->{
            IntentionOrderGoodsEntity good = new IntentionOrderGoodsEntity();
            BeanUtils.copyProperties(item,good);
            good.setCreateUser(ShiroUtil.getUserId());
            good.setIntentionOrderId(entity.getIntentionOrderId());
            int i = goodsMapper.insert(good);
            if(i == 0){
                throw new BussException("货主信息发布 货物新增失败");
            }
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByIds(List<String> ids) {
        UpdateWrapper<IntentionOrderEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("intention_order_id",ids);
        updateWrapper.set("is_able", Constant.AbleEnum.NO.getValue());
        this.update(updateWrapper);

        UpdateWrapper<IntentionOrderGoodsEntity> uw = new UpdateWrapper<>();
        uw.in("intention_order_id",ids);
        uw.set("is_able",Constant.AbleEnum.NO.getValue());
        intentionOrderGoodsService.update(uw);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(IntentionOrderDto dto) {
        IntentionOrderEntity oldOrder = intentionOrderMapper.selectById(dto.getIntentionOrderId());
        if(oldOrder == null){
            throw new BussException("查不到意向单 IntentionOrderId："+dto.getIntentionOrderId());
        }
        UpdateWrapper<IntentionOrderEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("intention_order_id",dto.getIntentionOrderId());
        updateWrapper.set("intention_status",dto.getIntentionStatus());
        updateWrapper.set("update_user",ShiroUtil.getUserId());
        if(StringUtils.isNotEmpty(dto.getRefuseRemark())){
            updateWrapper.set("refuse_remark",dto.getRefuseRemark());
        }
        boolean update = this.update(updateWrapper);
        if(!update){
            throw new BussException("意向单状态更新失败");
        }

        //1:意向发起人是货主    2：意向发起人是承运商
        StringBuffer stringBuffer = new StringBuffer("");
        if(dto.getType().equals(IntentionOrderEntity.TYPE_1)){
            stringBuffer.append("货主"+oldOrder.getFromUserId() + IntentionOrderEnum.getEnum(dto.getIntentionStatus(),IntentionOrderEntity.TYPE_1).getExplain() + "承运商"+oldOrder.getToUserId());
        }else if(dto.getType().equals(IntentionOrderEntity.TYPE_2)){
            stringBuffer.append("承运商"+oldOrder.getFromUserId() + IntentionOrderEnum.getEnum(dto.getIntentionStatus(),IntentionOrderEntity.TYPE_2).getExplain() + "货主A"+oldOrder.getToUserId());
        }
        IntentionLogEntity intEntity = new IntentionLogEntity();
        intEntity.setOldStatus(oldOrder.getIntentionStatus());
        intEntity.setNewStatus(dto.getIntentionStatus());
        intEntity.setCreateUser(ShiroUtil.getUserId());
        intEntity.setContext(stringBuffer.toString());
        intEntity.setIntentionType(dto.getType());
        intEntity.setRemark(dto.getRefuseRemark());
        intEntity.setType(IntentionLogEntity.TYPE_10);
        int insert = intentionLogMapper.insert(intEntity);
        if(insert != 1){
            throw new BussException("新增意向单状态日志失败");
        }

        if(dto.getIntentionStatus().equals(IntentionOrderEnum.CONSIGNOR_ENUM_ELEVEN.getStatus())){
            GeneralOrderDto orderDto = new GeneralOrderDto();
            orderDto.setIntentionOrderId(oldOrder.getIntentionOrderId());

            boolean add = generalOrderService.add(orderDto);
            if(!add){
                throw new BussException("新增订单失败");
            }

        }
        return true;
    }


}
