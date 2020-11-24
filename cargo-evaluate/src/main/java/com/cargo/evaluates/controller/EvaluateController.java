package com.cargo.evaluates.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.complaint.entity.GeneralOrderEntity;
import com.cargo.complaint.entity.UserInfoEntity;
import com.cargo.complaint.vo.GeneralOrderDetailVo;
import com.cargo.evaluates.dto.EvaluateDto;
import com.cargo.evaluates.entity.Evaluate;
import com.cargo.evaluates.service.EvaluateService;
import com.cargo.evaluates.vo.EvaluateVo;
import com.cargo.feign.service.FeignOrderService;
import com.cargo.feign.service.FeignService;
import com.commom.core.BeanConverter;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论主表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/api/evaluate/evaluate")
@Api(tags ="评论")
public class EvaluateController extends SuperController {

    @Autowired
    private EvaluateService evaluateService;
     @Autowired
    private FeignService feignService;

    @Autowired
    private FeignOrderService feignOrderService;

    @PostMapping("/addByOwner")
    @ApiOperation(value = "新增评论从货主端")
    public ResponseInfo addByOwner(@RequestBody EvaluateDto evaluateDto) {
        evaluateDto.setEvaluateType(1);
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        UserInfoEntity byId = feignService.findById(s);
        byId.setOrgId(orgId);
        evaluateDto.setOrgId(orgId);
        evaluateDto.setCreatUserId(byId.getUserId());
        evaluateDto.setCreatUserName(byId.getUserName());
        evaluateService.save(BeanConverter.convert(Evaluate.class,evaluateDto));
        return ResponseUtil.success();
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情接口")
    public ResponseInfo<EvaluateVo> detail(@RequestParam String id) {
        Evaluate byId = evaluateService.getById(id);
        EvaluateVo convert = BeanConverter.convert(EvaluateVo.class, byId);
        //查找订单的数据
        if (!ObjectUtils.isEmpty(convert)) {
                List<String> stringList = new ArrayList<>();
                stringList.add(convert.getGeneralOrderId());
                if (!ObjectUtils.isEmpty(stringList)) {
                    List<GeneralOrderDetailVo> generalOrderEntities = feignOrderService.orderListbyOrderIds(stringList);
                    if (!ObjectUtils.isEmpty(generalOrderEntities)) {
                        convert.setSenderOrgName(generalOrderEntities.get(0).getSenderOrgName());
                        convert.setSenderUserName(generalOrderEntities.get(0).getSenderUserName());
                        convert.setSenderUserPhone(generalOrderEntities.get(0).getSenderUserPhone());
                        convert.setSenderAreaCityName(generalOrderEntities.get(0).getSenderAreaCityName());
                        convert.setSenderAreaProvinceName(generalOrderEntities.get(0).getSenderAreaProvinceName());
                        convert.setSenderAreaTownName(generalOrderEntities.get(0).getSenderAreaTownName());
                        convert.setSenderAreaDetail(generalOrderEntities.get(0).getSenderAreaDetail());
                        convert.setDeliveryAreaCityName(generalOrderEntities.get(0).getDeliveryAreaCityName());
                        convert.setDeliveryAreaDetail(generalOrderEntities.get(0).getDeliveryAreaDetail());
                        convert.setDeliveryAreaProvinceName(generalOrderEntities.get(0).getDeliveryAreaProvinceName());
                        convert.setDeliveryAreaTownName(generalOrderEntities.get(0).getDeliveryAreaTownName());
                        convert.setGoodsName(generalOrderEntities.get(0).getGoodsName());
                    }
            }
        }
        return ResponseUtil.success(convert);
    }


    @PostMapping("/addByCarrier")
    @ApiOperation(value = "新增评论从承运商端")
    public ResponseInfo addByCarrier(@RequestBody EvaluateDto evaluateDto) {
        evaluateDto.setEvaluateType(2);
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        UserInfoEntity byId = feignService.findById(s);
        byId.setOrgId(orgId);
        evaluateDto.setOrgId(byId.getOrgId());
        evaluateService.save(BeanConverter.convert(Evaluate.class,evaluateDto));
        return ResponseUtil.success();
    }


    @PostMapping("/list")
    @ApiOperation(value = "查找评论--订单")
    public ResponseInfo<IPage<EvaluateVo>> list(@RequestBody EvaluateDto evaluateDto) {
        Page<Evaluate> page = this.getPage(true);
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        UserInfoEntity byId = feignService.findById(s);
        byId.setOrgId(orgId);
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(evaluateDto.getGeneralOrderId())) {
            queryWrapper.eq("order_id",evaluateDto.getGeneralOrderId());
        }
        if (!ObjectUtils.isEmpty(evaluateDto.getGeneralOrderNo())) {
            queryWrapper.eq("order_no",evaluateDto.getGeneralOrderNo());
        }

        if (!ObjectUtils.isEmpty(evaluateDto.getEvaluateType())) {
            queryWrapper.eq("evaluate_type",evaluateDto.getEvaluateType());
        }
        if (!ObjectUtils.isEmpty(evaluateDto.getCreatUserId())) {
            queryWrapper.eq("creat_user_id",evaluateDto.getCreatUserId());
        }
        if (!ObjectUtils.isEmpty(byId.getOrgId())) {
            queryWrapper.eq("org_id",byId.getOrgId());
        }
        queryWrapper.orderByDesc("gmt_create");
        IPage<Evaluate> list = evaluateService.page(page, queryWrapper);
        IPage<EvaluateVo> convert = BeanConverter.convert(EvaluateVo.class, list);
        //查找订单的数据
        if (!ObjectUtils.isEmpty(convert)) {
            if (!ObjectUtils.isEmpty(convert.getRecords())) {
                List<String> stringList = new ArrayList<>();
                for (EvaluateVo record : convert.getRecords()) {
                    if (!ObjectUtils.isEmpty(record.getGeneralOrderId())) {
                        stringList.add(record.getGeneralOrderId());
                    }
                }
                if (!ObjectUtils.isEmpty(stringList)) {
                    List<GeneralOrderDetailVo> generalOrderEntities = feignOrderService.orderListbyOrderIds(stringList);
                    Map<String, List<GeneralOrderDetailVo>> collect = generalOrderEntities.stream().collect(Collectors.groupingBy(GeneralOrderDetailVo::getGeneralOrderId));
                    for (EvaluateVo evaluate:convert.getRecords()){
                        List<GeneralOrderDetailVo> generalOrderEntities1 = collect.get(evaluate.getGeneralOrderId());
                        if (!ObjectUtils.isEmpty(generalOrderEntities1)) {
                            evaluate.setSenderOrgName(generalOrderEntities1.get(0).getSenderOrgName());
                            evaluate.setSenderUserName(generalOrderEntities1.get(0).getSenderUserName());
                            evaluate.setSenderUserPhone(generalOrderEntities1.get(0).getSenderUserPhone());
                            evaluate.setSenderAreaCityName(generalOrderEntities1.get(0).getSenderAreaCityName());
                            evaluate.setSenderAreaProvinceName(generalOrderEntities1.get(0).getSenderAreaProvinceName());
                            evaluate.setSenderAreaTownName(generalOrderEntities1.get(0).getSenderAreaTownName());
                            evaluate.setSenderAreaDetail(generalOrderEntities1.get(0).getSenderAreaDetail());
                            evaluate.setDeliveryAreaCityName(generalOrderEntities1.get(0).getDeliveryAreaCityName());
                            evaluate.setDeliveryAreaDetail(generalOrderEntities1.get(0).getDeliveryAreaDetail());
                            evaluate.setDeliveryAreaProvinceName(generalOrderEntities1.get(0).getDeliveryAreaProvinceName());
                            evaluate.setDeliveryAreaTownName(generalOrderEntities1.get(0).getDeliveryAreaTownName());
                            evaluate.setGoodsName(generalOrderEntities1.get(0).getGoodsName());
                        }
                    }
                }
            }
        }
        return ResponseUtil.success(convert);
    }


    @PostMapping("/listWaybIll")
    @ApiOperation(value = "查找评论--运单")
    public ResponseInfo<IPage<EvaluateVo>> listWaybIll(@RequestBody EvaluateDto evaluateDto) {
        Page<Evaluate> page = this.getPage(true);
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(evaluateDto.getWaybillId())) {
            queryWrapper.eq("waybill_id",evaluateDto.getWaybillId());
        }
        if (!ObjectUtils.isEmpty(evaluateDto.getWaybillNo())) {
            queryWrapper.eq("waybill_no",evaluateDto.getWaybillNo());
        }
        if (!ObjectUtils.isEmpty(evaluateDto.getEvaluateType())) {
            queryWrapper.eq("evaluate_type",evaluateDto.getEvaluateType());
        }
        if (!ObjectUtils.isEmpty(evaluateDto.getCreatUserId())) {
            queryWrapper.eq("creat_user_id",evaluateDto.getCreatUserId());
        }
        queryWrapper.orderByDesc("gmt_create");
        IPage<Evaluate> list = evaluateService.page(page, queryWrapper);
        return ResponseUtil.success(BeanConverter.convert(EvaluateVo.class,list));
    }
}
