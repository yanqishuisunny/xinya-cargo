package com.cargo.alipay.controller;

import com.cargo.dto.DispatchDto;
import com.cargo.entity.UserInfoEntity;
import com.cargo.feign.service.FeignService;
import com.cargo.alipay.service.DispatchService;
import com.commom.exception.BussException;
import com.commom.shiro.ShiroUtil;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 调度
 */
@RestController
@Api(tags = "调度")
@RequestMapping("/api/dispatch")
public class DispatchController  extends SuperController {

    @Autowired
    private DispatchService dispatchService;

    @Autowired
    private FeignService feignService;





    @PostMapping("/addWayBillByApp")
    @ApiOperation(value = "app端调度")
    public ResponseInfo addWayBillByApp(@RequestBody DispatchDto dispatchDto) {
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
//        if (ObjectUtils.isEmpty(orgId)) {
//            throw new BussException("请选择公司！");
//        }
        UserInfoEntity userById = feignService.findById(s);
        userById.setOrgId(orgId);
        dispatchService.addWayBillByApp(dispatchDto,userById);
        return ResponseUtil.success();
    }

    @PostMapping("/addWayBillByPC")
    @ApiOperation(value = "pc端调度")
    public ResponseInfo addWayBillByPC(@RequestBody DispatchDto dispatchDto) {
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        UserInfoEntity userById = feignService.findById(s);
        userById.setOrgId(orgId);
        dispatchService.addWayBillByPC(dispatchDto,userById);
        return ResponseUtil.success();
    }

}
