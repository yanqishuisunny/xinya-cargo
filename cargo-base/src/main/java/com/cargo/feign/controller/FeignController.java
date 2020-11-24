package com.cargo.feign.controller;


import com.alibaba.fastjson.JSONObject;
import com.cargo.area.entity.AreCodeEntry;
import com.cargo.area.service.AreaService;
import com.cargo.car.entity.*;
import com.cargo.car.mapper.*;
import com.cargo.car.service.CarService;
import com.cargo.car.service.CarTypeService;
import com.cargo.car.vo.CarVo;
import com.cargo.feign.vo.*;
import com.cargo.order.entity.GoodsCategoryEntity;
import com.cargo.order.entity.GoodsPriceEntity;
import com.cargo.order.entity.GoodsTypeEntity;
import com.cargo.order.service.GoodsCategoryService;
import com.cargo.order.service.GoodsPriceService;
import com.cargo.order.service.GoodsTypeService;
import com.cargo.trspottype.entity.TrspotTypeEntity;
import com.cargo.trspottype.service.TrspotTypeService;
import com.cargo.unit.entity.UnitEntity;
import com.cargo.unit.service.UnitService;
import com.cargo.user.entity.OrgEntity;
import com.cargo.user.entity.UserInfoEntity;
import com.cargo.user.service.DriverInformationService;
import com.cargo.user.service.OrgService;
import com.cargo.user.service.UserInfoService;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * feignService 调用中心
 *
 * @Author Carlos
 */
@Slf4j
@ApiIgnore
@RestController
@RequestMapping("/ms/service")
public class FeignController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CarTypeService carTypeService;

    @Autowired
    private GoodsTypeService goodsTypeService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private GoodsPriceService goodsPriceService;

    @Autowired
    private TrspotTypeService trspotTypeService;

    @Autowired
    private AreaService areaService;
    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarService carService;


    @Autowired
    private DriverInformationService driverInformationService;

    @Autowired
    private CarTypeMapper carTypeMapper;
    @Autowired
    private CarSizeMapper carSizeMapper;
    @Autowired
    private CarEnergyTypeMapper carEnergyTypeMapper;
    @Autowired
    private CarCardTypeMapper carCardTypeMapper;
    @Autowired
    private OrgService orgService;

    @ApiOperation(value = "获取车辆")
    @GetMapping("/car/get/{carId}")
    public CarVo get(@PathVariable("carId") String carId)  {
        return carService.queryForOne(carId);
    }

    @ApiOperation(value = "根据ID获得用户")
    @GetMapping("/userInfo/get/{userId}")
    public UserInfoEntity findById(@ApiParam("用户ID") @PathVariable("userId") String userId){
        log.info("根据ID获得用户 userId:{}", userId);
        UserInfoEntity userInfoEntity = userInfoService.getById(userId);
        return userInfoEntity;
    }

    @ApiOperation(value = "获取企业信息")
    @PostMapping("/org/get/{orgId}")
    public OrgEntity getOrgById(@PathVariable String orgId) {
        return orgService.getById(orgId);
    }

    @ApiOperation(value = "根据公司id 更新车辆redis 数据")
    @GetMapping("/carMessageToRedis")
    public void carMessageToRedis(@RequestParam String orgId){
        carService.carMessageToRedis(orgId);
    }


    @ApiOperation(value = "根据公司id 更新司机redis 数据")
    @GetMapping("/driverToRedis")
    public void driverToRedis(@RequestParam String orgId){
        driverInformationService.driverToRedis(orgId);
    }



    @ApiOperation(value = "获取货主发布信息视图")
    @PostMapping("/consignorRelease/converlist")
    public List<ConsignorReleaseVo> converlist(@RequestBody List<ConsignorReleaseVo> list)  {
        log.info("获取货主发布信息视图 list:{}", JSONObject.toJSONString(list));
        list.forEach(conVo ->{
            //计费方式名称
            GoodsPriceEntity priceType = goodsPriceService.getById(conVo.getPriceTypeId());
            conVo.setPriceTypeName(priceType == null ? "":priceType.getGoodsPriceName());
            //运输类型
            TrspotTypeEntity trspotType = trspotTypeService.getById(conVo.getTrspotTypeId());
            conVo.setTrspotTypeName(trspotType == null ? "":trspotType.getTrspotTypeName());
            //车辆要求
            CarTypeEntity carType = carTypeService.getById(conVo.getCarTypeId());
            conVo.setCarTypeName(carType == null ? "":carType.getCarTypeName());
            //车类尺寸
            CarSizeEntity carSizeEntity = carSizeMapper.selectById(conVo.getCarSizeId());
            conVo.setCarSizeName(carSizeEntity == null ? "":carSizeEntity.getCarSizeName());
            conVo.getListGoods().forEach(goodsVo -> {
                converGood(goodsVo);
            });
        });
        return list;
    }
    @ApiOperation(value = "获取承运商发布信息视图")
    @PostMapping("/carrierRelease/converCarrierlist")
    public List<CarrierReleaseVo> converCarrierlist(@RequestBody List<CarrierReleaseVo> list)  {
        log.info("获取承运商发布信息视图 list:{}", JSONObject.toJSONString(list));
        list.forEach(conVo ->{
            GoodsPriceEntity price = goodsPriceService.getById(conVo.getPriceTypeId());
            if(price != null){
                conVo.setPriceTypeNanme(price.getGoodsPriceName());
            }
            conVo.setListCars(new ArrayList<CarVo>());
            conVo.getCarrierCarsVoList().forEach(item -> {
                CarEntity carEntity = carMapper.selectById(item.getCarId());
                CarVo vo = new CarVo();
                if(carEntity != null) {
                    BeanUtils.copyProperties(carEntity, vo);
                    conVo.getListCars().add(converCar(vo));
                }
            });
        });
        return list;
    }



    @ApiOperation(value = "获取意向单视图")
    @PostMapping("/intentionOrder/converList")
    public List<IntentionOrderVo> converIntentionOrder(@RequestBody List<IntentionOrderVo> list)  {
        log.info("获取意向单视图 list:{}", JSONObject.toJSONString(list));
        list.forEach(conVo ->{

            //计费方式名称
            GoodsPriceEntity priceType = goodsPriceService.getById(conVo.getPriceTypeId());
            conVo.setPriceTypeName(priceType == null ? "":priceType.getGoodsPriceName());
            //运输类型
            TrspotTypeEntity trspotType = trspotTypeService.getById(conVo.getTrspotTypeId());
            conVo.setTrspotTypeName(trspotType == null ? "":trspotType.getTrspotTypeName());
            //车辆要求
            CarTypeEntity carType = carTypeService.getById(conVo.getCarTypeId());
            conVo.setCarTypeName(carType == null ? "":carType.getCarTypeName());
            //车类尺寸
            CarSizeEntity carSizeEntity = carSizeMapper.selectById(conVo.getCarSizeId());
            conVo.setCarSizeName(carSizeEntity == null ? "":carSizeEntity.getCarSizeName());

            conVo.getGoodsVoList().forEach(item ->{
                converIntGood(item);
            });

        });
        return list;
    }

    @ApiOperation(value = "获取订单视图")
    @PostMapping("/order/converOrder")
    public List<GeneralOrderVo> converOrder(@RequestBody  List<GeneralOrderVo> list){
        log.info("获取订单视图 list:{}", JSONObject.toJSONString(list));
        list.forEach(conVo ->{
            //计费方式名称
            GoodsPriceEntity priceType = goodsPriceService.getById(conVo.getPriceTypeId());
            conVo.setPriceTypeName(priceType == null ? "":priceType.getGoodsPriceName());
            //运输类型
            TrspotTypeEntity trspotType = trspotTypeService.getById(conVo.getTrspotTypeId());
            conVo.setTrspotTypeName(trspotType == null ? "":trspotType.getTrspotTypeName());
            //车辆要求
            CarTypeEntity carType = carTypeService.getById(conVo.getCarTypeId());
            conVo.setCarTypeName(carType == null ? "":carType.getCarTypeName());
            //车类尺寸
            CarSizeEntity carSizeEntity = carSizeMapper.selectById(conVo.getCarSizeId());
            conVo.setCarSizeName(carSizeEntity == null ? "":carSizeEntity.getCarSizeName());
            conVo.getGoodsVoList().forEach(item ->{
                converOrder(item);
            });

        });
        return list;
    }


    @ApiOperation(value = "获得省市区根据AdCode")
    @GetMapping("/area/getByAdCode/{adCode}")
    public AreCodeEntry findByAdCode(@PathVariable("adCode") String adCode){
        log.info("获得省市区根据AdCode adCode:{}", adCode);
        //根据区域编码 去获取相应的区域名称
        return areaService.getByAreaId(adCode);
    }

    public GeneralOrderGoodsVo converOrder(GeneralOrderGoodsVo goodsVo){
        //发货类型
        GoodsTypeEntity goodsType = goodsTypeService.getById(goodsVo.getGoodsTypeId());
        goodsVo.setGoodsTypeName(goodsType ==null ? "":goodsType.getGoodsTypeName());
        //货物种类
        GoodsCategoryEntity goodsCategory = goodsCategoryService.getById(goodsVo.getGoodsCategoryId());
        goodsVo.setGoodsCategoryName(goodsCategory == null ? "":goodsCategory.getGoodsCategoryName());
        //体积单位名称
        UnitEntity goodsVolumeUnit = unitService.getById(goodsVo.getGoodsVolumeUnitId());
        goodsVo.setGoodsVolumeUnitName(goodsVolumeUnit == null ? "":goodsVolumeUnit.getUnitName());
        //重量值名称
        UnitEntity goodsWeightUnit = unitService.getById(goodsVo.getGoodsWeightUnitId());
        goodsVo.setGoodsWeightUnitName(goodsWeightUnit ==null ? "":goodsWeightUnit.getUnitName());
        return goodsVo;
    }

    public GoodsVo converGood(GoodsVo goodsVo){
        //发货类型
        GoodsTypeEntity goodsType = goodsTypeService.getById(goodsVo.getGoodsTypeId());
        goodsVo.setGoodsTypeName(goodsType ==null ? "":goodsType.getGoodsTypeName());
        //货物种类
        GoodsCategoryEntity goodsCategory = goodsCategoryService.getById(goodsVo.getGoodsCategoryId());
        goodsVo.setGoodsCategoryName(goodsCategory == null ? "":goodsCategory.getGoodsCategoryName());
        //体积单位名称
        UnitEntity goodsVolumeUnit = unitService.getById(goodsVo.getGoodsVolumeUnitId());
        goodsVo.setGoodsVolumeUnitName(goodsVolumeUnit == null ? "":goodsVolumeUnit.getUnitName());
        //重量值名称
        UnitEntity goodsWeightUnit = unitService.getById(goodsVo.getGoodsWeightUnitId());
        goodsVo.setGoodsWeightUnitName(goodsWeightUnit ==null ? "":goodsWeightUnit.getUnitName());
        return goodsVo;
    }

    public CarVo converCar(CarVo item){
        //车牌类型
        CarCardTypeEntity carCardTypeEntity = carCardTypeMapper.selectById(item.getCarCardTypeId());
        item.setCarCardTypeName(carCardTypeEntity==null ? "" : carCardTypeEntity.getCarCardTypeName());

        //能源类型
        CarEnergyTypeEntity carEnergyTypeEntity = carEnergyTypeMapper.selectById(item.getCarEnergyTypeId());
        item.setCarEnergyTypeName(carEnergyTypeEntity==null ? "" : carEnergyTypeEntity.getCarEnergyTypeName());

        //车辆尺寸
        CarSizeEntity carSizeEntity = carSizeMapper.selectById(item.getCarSizeId());
        item.setCarSizeName(carSizeEntity==null ? "" : carSizeEntity.getCarSizeName());

        //车辆类型
        CarTypeEntity carTypeEntity = carTypeMapper.selectById(item.getCarTypeId());
        item.setCarTypeName(carTypeEntity==null ? "" : carTypeEntity.getCarTypeName());
        return item;
    }

    public IntentionOrderGoodsVo converIntGood(IntentionOrderGoodsVo goodsVo){
        //发货类型
        GoodsTypeEntity goodsType = goodsTypeService.getById(goodsVo.getGoodsTypeId());
        goodsVo.setGoodsTypeName(goodsType ==null ? "":goodsType.getGoodsTypeName());
        //货物种类
        GoodsCategoryEntity goodsCategory = goodsCategoryService.getById(goodsVo.getGoodsCategoryId());
        goodsVo.setGoodsCategoryName(goodsCategory == null ? "":goodsCategory.getGoodsCategoryName());
        //体积单位名称
        UnitEntity goodsVolumeUnit = unitService.getById(goodsVo.getGoodsVolumeUnitId());
        goodsVo.setGoodsVolumeUnitName(goodsVolumeUnit == null ? "":goodsVolumeUnit.getUnitName());
        //重量值名称
        UnitEntity goodsWeightUnit = unitService.getById(goodsVo.getGoodsWeightUnitId());
        goodsVo.setGoodsWeightUnitName(goodsWeightUnit ==null ? "":goodsWeightUnit.getUnitName());
        return goodsVo;
    }
}
