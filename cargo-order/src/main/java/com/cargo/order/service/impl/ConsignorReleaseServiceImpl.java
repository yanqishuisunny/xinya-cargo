package com.cargo.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.feign.entity.AreCodeEntry;
import com.cargo.feign.service.BaseFeignService;
import com.cargo.order.dto.ConsignorReleaseDto;
import com.cargo.order.dto.GoodsDto;
import com.cargo.order.entity.ConsignorReleaseEntity;
import com.cargo.order.entity.GoodsEntity;
import com.cargo.order.mapper.ConsignorReleaseMapper;
import com.cargo.order.mapper.GoodsMapper;
import com.cargo.order.service.ConsignorReleaseService;
import com.cargo.order.service.GoodsService;
import com.cargo.order.vo.ConsignorReleaseVo;
import com.commom.cache.Constant;
import com.commom.exception.BussException;
import com.commom.gpsUtils.StringUtil;
import com.commom.shiro.ShiroUtil;
import com.commom.utils.GaodeApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Slf4j
@Service
public class ConsignorReleaseServiceImpl extends ServiceImpl<ConsignorReleaseMapper, ConsignorReleaseEntity> implements ConsignorReleaseService {

    @Autowired
    private BaseFeignService baseFeignService;

    @Autowired
    private ConsignorReleaseMapper consignorReleaseMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(ConsignorReleaseDto dto) {
        ConsignorReleaseEntity entity = new ConsignorReleaseEntity();
        BeanUtils.copyProperties(dto,entity);
        entity.setCreateUser(ShiroUtil.getUserId());
        entity.setCargoUserId(ShiroUtil.getUserId());
        StringBuffer originsAddress = new StringBuffer("");
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

        originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaProvinceName())?"":entity.getSenderAreaProvinceName());
        originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaCityName())?"":entity.getSenderAreaCityName());
        originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaTownName())?"":entity.getSenderAreaTownName());
        originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaDetail())?"":entity.getSenderAreaDetail());

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

        StringBuffer destinationAddress = new StringBuffer("");
        destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaProvinceName())?"":entity.getDeliveryAreaProvinceName());
        destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaCityName())?"":entity.getDeliveryAreaCityName());
        destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaTownName())?"":entity.getDeliveryAreaTownName());
        destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaDetail())?"":entity.getDeliveryAreaDetail());

        //发货地经纬度
        String sendGPS = GaodeApi.httpURLConectionGET(originsAddress.toString());
        entity.setSenderProcityName(sendGPS);
        //收货地经纬度
        String deliveGPS = GaodeApi.httpURLConectionGET(destinationAddress.toString());
        entity.setDeliveryProcityName(deliveGPS);
        Double distance = GaodeApi.getDistance(sendGPS, deliveGPS);
        entity.setGpsMileage(new BigDecimal(distance));

        int insert = consignorReleaseMapper.insert(entity);
        if(insert == 0){
            throw new BussException("货主信息发布 新增失败");
        }

        List<GoodsDto> listGoods = dto.getListGoods();
        listGoods.stream().forEach(item ->{
            GoodsEntity good = new GoodsEntity();
            BeanUtils.copyProperties(item,good);
            good.setCreateUser(ShiroUtil.getUserId());
            good.setConsignorReleaseId(entity.getConsignorReleaseId());
            int i = goodsMapper.insert(good);
            if(i == 0){
                throw new BussException("货主信息发布 货物新增失败");
            }
        });
        return true;
    }

    @Override
    public Page<ConsignorReleaseVo> queryForList(ConsignorReleaseDto dto, Page<ConsignorReleaseVo> page) {
        List<ConsignorReleaseVo> list = this.baseMapper.queryForList(dto,page);
        if(list.size() != 0){
            list = baseFeignService.converlist(list);
        }
        page.setRecords(list);
        return page;
    }

    @Override
    public ConsignorReleaseVo queryForOne(String consignorReleaseId) {
        ConsignorReleaseVo vo = consignorReleaseMapper.queryForOne(consignorReleaseId);
        if(vo == null){
            throw new BussException("找不到对应consignorReleaseId :"+consignorReleaseId +"  数据");
        }
        List<ConsignorReleaseVo> list = new ArrayList<>();
        list.add(vo);
        return baseFeignService.converlist(list).get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByListIds(List<String> consignorReleaseIds) {
        UpdateWrapper<ConsignorReleaseEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("consignor_release_id",consignorReleaseIds);
        updateWrapper.set("is_able", Constant.AbleEnum.NO.getValue());
        this.update(updateWrapper);

        UpdateWrapper<GoodsEntity> updateGoods = new UpdateWrapper<>();
        updateGoods.in("consignor_release_id",consignorReleaseIds);
        updateGoods.set("is_able", Constant.AbleEnum.NO.getValue());
        goodsService.update(updateGoods);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateConById(ConsignorReleaseDto dto)  {
        ConsignorReleaseEntity entity = new ConsignorReleaseEntity();
        BeanUtils.copyProperties(dto,entity);
        ConsignorReleaseEntity enbyId = consignorReleaseMapper.selectById(entity.getConsignorReleaseId());
        if(enbyId == null){
            return false;
        }
        List<GoodsDto> listGoods = dto.getListGoods();
        if(listGoods != null){
            listGoods.stream().forEach(item ->{
                int i = goodsMapper.updateById(item);
                if(i != 1){
                    throw new BussException("goods更新失败");
                }
            });
        }
        entity.setUpdateUser(ShiroUtil.getUserId());

        //根据区域编码 去获取发货相应的区域名称
        StringBuffer originsAddress = new StringBuffer("");
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

            originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaProvinceName())?"":entity.getSenderAreaProvinceName());
            originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaCityName())?"":entity.getSenderAreaCityName());
            originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaTownName())?"":entity.getSenderAreaTownName());
            originsAddress.append(StringUtil.isEmpty(entity.getSenderAreaDetail())?"":entity.getSenderAreaDetail());
        }



        //根据区域编码 去获取收货相应的区域名称
        StringBuffer destinationAddress = new StringBuffer("");
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

            destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaProvinceName())?"":entity.getDeliveryAreaProvinceName());
            destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaCityName())?"":entity.getDeliveryAreaCityName());
            destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaTownName())?"":entity.getDeliveryAreaTownName());
            destinationAddress.append(StringUtil.isEmpty(entity.getDeliveryAreaDetail())?"":entity.getDeliveryAreaDetail());
        }
        Double distance = 0.0;

        if(StringUtils.isNotEmpty(originsAddress.toString()) && StringUtils.isNotEmpty(destinationAddress.toString())) {
            //发货地经纬度
            String sendGPS = GaodeApi.httpURLConectionGET(originsAddress.toString());
            entity.setSenderProcityName(sendGPS);
            //收货地经纬度
            String deliveGPS = GaodeApi.httpURLConectionGET(destinationAddress.toString());
            entity.setDeliveryProcityName(deliveGPS);
            distance = GaodeApi.getDistance(sendGPS, deliveGPS);
        }
        //GPS预估距离
        entity.setGpsMileage(new BigDecimal(distance));
        int i = consignorReleaseMapper.updateById(entity);
        if(i != 1){
            throw new BussException("goods更新失败");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(ConsignorReleaseDto dto) {
        ConsignorReleaseEntity entity = new ConsignorReleaseEntity();
        BeanUtils.copyProperties(dto,entity);
        ConsignorReleaseEntity enbyId = consignorReleaseMapper.selectById(entity.getConsignorReleaseId());
        if(enbyId == null){
           throw new BussException("查不到对应货主发布信息ID："+entity.getConsignorReleaseId());
        }
        UpdateWrapper<ConsignorReleaseEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("consignor_release_id",entity.getConsignorReleaseId());
        updateWrapper.set("status",entity.getStatus());
        updateWrapper.set("update_user",ShiroUtil.getUserId());
        updateWrapper.set("check_remark",dto.getCheckRemark());
        if(entity.getExpiryDaysDate() != null){
            updateWrapper.set("expiry_days_date",entity.getExpiryDaysDate());
            Calendar c = Calendar.getInstance();
            try {
                Date expiryDaysDate = simpleDateFormat.parse(entity.getExpiryDaysDate());
                c.setTime(expiryDaysDate);
                c.add(Calendar.DAY_OF_MONTH, enbyId.getExpiryDays());
            } catch (ParseException e) {
                throw new BussException("出错");
            }
            updateWrapper.set("validity_date",c.getTime());
        }
        return this.update(updateWrapper);
    }


}
