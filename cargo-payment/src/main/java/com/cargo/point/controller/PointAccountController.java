package com.cargo.point.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.point.dto.PointAccountDto;
import com.cargo.point.entity.PointAccountEntity;
import com.cargo.point.service.PointAccountService;
import com.cargo.point.vo.PointAccountVo;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: xinzs
 * @Date: 2020/11/16 17:45
 */
@Api(tags = "积分表")
@RestController
@RequestMapping("/api/payment/point")
public class PointAccountController extends SuperController {

    @Autowired
    PointAccountService pointAccountService;


    @ApiOperation(value = "积分明细")
    @PostMapping
    public ResponseInfo pointRunningList(@RequestBody PointAccountDto dto){

        Page<PointAccountEntity> page = this.getPage(false);

        IPage<PointAccountVo> list = pointAccountService.pointRunningList(dto, page);

        return ResponseUtil.success(list);
    }
}
