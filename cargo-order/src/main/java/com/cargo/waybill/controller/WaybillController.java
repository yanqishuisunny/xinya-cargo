package com.cargo.waybill.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.feign.dto.DriversLocationDto;
import com.cargo.feign.entity.OrgEntity;
import com.cargo.feign.service.BaseFeignService;
import com.cargo.feign.service.GPSFeignService;
import com.cargo.order.entity.GeneralOrderEntity;
import com.cargo.order.entity.GeneralOrderGoodsEntity;
import com.cargo.order.mapper.GeneralOrderGoodsMapper;
import com.cargo.order.service.GeneralOrderGoodsService;
import com.cargo.order.service.GeneralOrderService;
import com.cargo.order.vo.ConsignorReleaseVo;
import com.cargo.order.vo.GeneralOrderGoodsVo;
import com.cargo.order.vo.GeneralOrderVo;
import com.cargo.waybill.dto.WaybillDto;
import com.cargo.waybill.entity.FileEntity;
import com.cargo.waybill.entity.WaybillEntity;
import com.cargo.waybill.entity.WaybillStatusLogEntity;
import com.cargo.waybill.service.FileService;
import com.cargo.waybill.service.WaybillService;
import com.cargo.waybill.service.WaybillStatusLogService;
import com.cargo.waybill.vo.FileVo;
import com.cargo.waybill.vo.WaybillStatusLogVo;
import com.cargo.waybill.vo.WaybillVo;
import com.commom.cache.CachePre;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.BaseController;
import com.commom.supper.SuperController;
import com.commom.utils.RedisUtil;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 运单表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-10-30
 */
@Slf4j
@Api(tags = "运单操作")
@RestController
@RequestMapping("/api/order/waybill")
public class WaybillController extends SuperController {

    @Autowired
    private WaybillService waybillService;

    @Autowired
    private WaybillStatusLogService waybillStatusLogService;

    @Autowired
    private FileService fileService;

    @Autowired
    private GeneralOrderService generalOrderService;

    @Autowired
    private GPSFeignService gpsFeignService;

    @Autowired
    private BaseFeignService baseFeignService;

    @Autowired
    private GeneralOrderGoodsMapper generalOrderGoodsMapper;

    @PostMapping("/add")
    @ApiOperation(value = "新增运单")
    @Transactional
    public ResponseInfo add(@RequestBody WaybillDto t) {
        return ResponseUtil.result(waybillService.addBill(t));
    }

    @PostMapping("/edit")
    @ApiOperation(value = "编辑运单")
    @Transactional
    public ResponseInfo edit(@RequestBody WaybillDto dto) {
        WaybillEntity entity = waybillService.getById(dto.getWaybillId());
        if(entity == null){
            throw new BussException("查不到对应运单 WaybillId:"+dto.getWaybillId());
        }
        BeanUtils.copyProperties(dto, entity);
        return ResponseUtil.result(waybillService.updateById(entity));
    }




    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改运单状态")
    @Transactional
    public ResponseInfo updateStatus(@RequestBody WaybillDto t) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        WaybillEntity convert = BeanConverter.convert(WaybillEntity.class, t);
        waybillService.updateById(convert);
        if(StringUtils.isNotEmpty(convert.getWaybillId())){
            convert.setUpdateUser(ShiroUtil.getUserId());
        }else{
            convert.setCreateUser(ShiroUtil.getUserId());
        }
        //创建运单日志
        WaybillStatusLogEntity waybillStatusLogEntity = new WaybillStatusLogEntity();
        waybillStatusLogEntity.setWaybillId(convert.getWaybillId());
        waybillStatusLogEntity.setWaybillNo(convert.getWaybillNo());
        waybillStatusLogEntity.setStatus(convert.getWaybillStatus());
        waybillStatusLogEntity.setTrackingTime(format.format(new Date()));
        waybillStatusLogService.save(waybillStatusLogEntity);
        if (!ObjectUtils.isEmpty(t.getFileId())) {
            fileService.removeByIds(t.getFileId());
        }
        if (!ObjectUtils.isEmpty(t.getFiles())) {
            fileService.saveOrUpdateBatch(BeanConverter.convert(FileEntity.class,t.getFiles()));
        }


        return ResponseUtil.success();
    }

    @GetMapping("/wayBilldetail/{waybillId}")
    @ApiOperation(value = "详情")
    @Transactional
    public ResponseInfo<WaybillVo> wayBilldetail(@PathVariable String waybillId) {
        WaybillVo waybillVo = new WaybillVo();
        if (ObjectUtils.isEmpty(waybillId)) {
            return ResponseUtil.success(waybillVo);
        }
        WaybillEntity byId = waybillService.getById(waybillId);
        waybillVo =  BeanConverter.convert(WaybillVo.class,byId);
        if (!ObjectUtils.isEmpty(byId)) {
            QueryWrapper<FileEntity> queryWrappers = new QueryWrapper<>();
            queryWrappers.eq("relat_id",byId.getWaybillId());
            queryWrappers.orderByDesc("gmt_create");
            List<FileEntity> entities = fileService.list(queryWrappers);
            waybillVo.setFiles(BeanConverter.convert(FileVo.class,entities));
        }
        if (!ObjectUtils.isEmpty(byId)) {
            QueryWrapper<WaybillStatusLogEntity> queryWrappers = new QueryWrapper<>();
            queryWrappers.eq("waybill_id",byId.getWaybillId());
            List<WaybillStatusLogEntity> entities = waybillStatusLogService.list(queryWrappers);
            waybillVo.setLogs(BeanConverter.convert(WaybillStatusLogVo.class,entities));
        }

        if (!ObjectUtils.isEmpty(byId)) {
            QueryWrapper<WaybillStatusLogEntity> queryWrappers = new QueryWrapper<>();
            queryWrappers.eq("waybill_id",byId.getWaybillId());
            OrgEntity orgEntity = baseFeignService.getOrgById(byId.getOrgId());
            waybillVo.setOrgName(orgEntity.getOrgName());
            waybillVo.setOrgPhone(orgEntity.getPhoneNo());
        }


        if (!ObjectUtils.isEmpty(byId)) {
            GeneralOrderEntity orderEntity = generalOrderService.getById(byId.getGeneralOrderId());
            List<GeneralOrderVo> list = new ArrayList<>();
            GeneralOrderVo generalOrderVo = new GeneralOrderVo();
            BeanUtils.copyProperties(orderEntity,generalOrderVo);

            QueryWrapper<GeneralOrderGoodsEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("general_order_id",byId.getGeneralOrderId());
            List<GeneralOrderGoodsEntity> intentionOrderGoodsEntities = generalOrderGoodsMapper.selectList(queryWrapper);
            ArrayList<GeneralOrderGoodsVo> listGoodsVo = new ArrayList<>();
            intentionOrderGoodsEntities.stream().forEach(intGood ->{
                GeneralOrderGoodsVo orderGoodsVo = new GeneralOrderGoodsVo();
                BeanUtils.copyProperties(intGood,orderGoodsVo);
                listGoodsVo.add(orderGoodsVo);
            });
            generalOrderVo.setGoodsVoList(listGoodsVo);
            list.add(generalOrderVo);
            List<GeneralOrderVo> generalOrderVos = baseFeignService.converOrder(list);
            generalOrderVo = generalOrderVos.get(0);
            Map<String,Object> carAndGoods = new HashMap<>();
            carAndGoods.put("carSizeName",generalOrderVo.getCarSizeName());
            carAndGoods.put("carTypeName",generalOrderVo.getCarTypeName());
            carAndGoods.put("trspotTypeName",generalOrderVo.getTrspotTypeName());
            carAndGoods.put("goodsTypeName",generalOrderVo.getGoodsVoList().get(0).getGoodsTypeName());
            carAndGoods.put("goodsName",generalOrderVo.getGoodsVoList().get(0).getGoodsName());
            carAndGoods.put("goodsWeightVal",generalOrderVo.getGoodsVoList().get(0).getGoodsWeightVal());
            carAndGoods.put("goodsWeightUnitName",generalOrderVo.getGoodsVoList().get(0).getGoodsWeightUnitName());

            waybillVo.setCarAndGoods(carAndGoods);
        }
        return ResponseUtil.success(waybillVo);
    }


    @PostMapping("/list")
    @ApiOperation(value = "运单集合")
    @Transactional
    public ResponseInfo<Page<WaybillVo>> list(@RequestBody WaybillDto dto) {
        Page<WaybillVo> page = this.getPage(false);
        return ResponseUtil.success(waybillService.queryForlist(dto,page));
    }

    @ApiOperation(value = "运单删除")
    @PostMapping("/delete")
    public ResponseInfo delete(@RequestBody List<String> waybillIds) {
        UpdateWrapper<WaybillEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("waybill_id",waybillIds);
        updateWrapper.set("is_able",0);
        return ResponseUtil.result(waybillService.update(updateWrapper));
    }



    @ApiOperation(value = "获得所有运单司机最后位置")
    @PostMapping("/getAllTrajectory")
    public ResponseInfo getAllTrajectory(@RequestBody DriversLocationDto dto) {
        QueryWrapper<WaybillEntity> queryWrapper = new QueryWrapper();
        if(StringUtils.isNotEmpty(dto.getGeneralOrderId())) {
            queryWrapper.eq("general_order_id", dto.getGeneralOrderId());
        }else{
            queryWrapper.in("waybill_status",dto.getListWaybillStatus());
        }
        List<WaybillEntity> list = waybillService.list();
        JSONArray array = new JSONArray();
        list.stream().forEach(item ->{
            JSONObject object = new JSONObject();
            WaybillVo waybillVo = new WaybillVo();
           // BeanUtils.copyProperties(item,waybillVo);
            String resultStr = (String)RedisUtil.hget(CachePre.DRIVERS_LOCATION_LAST, item.getDriverId());
            HashMap hashMap = JSONObject.parseObject(resultStr, HashMap.class);
            waybillVo.setDriverMap(hashMap);
            object.put("driverName",item.getDriverName());
            object.put("driverPhoneNo",item.getDriverPhoneNo());
            object.put("carNo",item.getCarNo());
            object.put("lat", "");
            object.put("lon", "");
            if(hashMap != null) {
                object.put("lat", hashMap.get("lat"));
                object.put("lon", hashMap.get("lon"));
            }
            array.add(object);
        });
        return ResponseUtil.result(array);
    }

    @ApiOperation(value = "根据运单获得运单")
    @PostMapping("/getTrajectory")
    public ResponseInfo getTrajectory(@RequestBody DriversLocationDto dto) {
        return ResponseUtil.result(gpsFeignService.queryLocationList(dto));
    }

}
