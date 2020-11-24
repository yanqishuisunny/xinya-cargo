package com.cargo.alipay.controller;


import com.cargo.dto.DispatchLogDto;
import com.cargo.entity.DispatchLog;
import com.cargo.alipay.service.DispatchLogService;
import com.cargo.entity.UserInfoEntity;
import com.cargo.vo.BannerCarVo;
import com.commom.core.BeanConverter;
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

import java.util.List;

/**
 * <p>
 * 调度单表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-10-27
 */
@RestController
@Api(tags = "调度历史-甘蔗图")
@RequestMapping("/api/dispatch/dispatchLog")
public class DispatchLogController extends SuperController {

    @Autowired
    private DispatchLogService dispatchLogService;

    @PostMapping("/add")
    @ApiOperation(value = "新增历史记录")
    public ResponseInfo addByOwner(@RequestBody DispatchLogDto dis) {
        dispatchLogService.save(BeanConverter.convert(DispatchLog.class,dis));
        return ResponseUtil.success();
    }


    @PostMapping("/carBannerByDriver")
    @ApiOperation(value = "根据司机的甘蔗图")
    public ResponseInfo<List<BannerCarVo>> carBannerByDriver(@RequestBody DispatchLogDto dispatchLogDto) {
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        List<BannerCarVo> list =   dispatchLogService.carBannerByDriver(dispatchLogDto,orgId);
        return ResponseUtil.success(list);
    }


    @PostMapping("/carBannerByCar")
    @ApiOperation(value = "根据车辆的甘蔗图")
    public ResponseInfo<List<BannerCarVo>> carBannerByCar(@RequestBody DispatchLogDto dispatchLogDto) {
        String s = ShiroUtil.currentUserId();
        String orgId = ShiroUtil.getOrgId();
        if (ObjectUtils.isEmpty(orgId)) {
            throw new BussException("请选择公司！");
        }
        List<BannerCarVo> list =   dispatchLogService.carBannerByCar(dispatchLogDto,orgId);
        return ResponseUtil.success(list);
    }


}
