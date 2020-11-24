package com.cargo.trspottype.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.trspottype.dto.TrspotTypeDto;
import com.cargo.trspottype.entity.TrspotTypeEntity;
import com.cargo.trspottype.service.TrspotTypeService;
import com.cargo.unit.dto.UnitDto;
import com.cargo.unit.entity.UnitEntity;
import com.cargo.unit.service.UnitService;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Api(tags = "运输类型列表")
@RestController
@RequestMapping("/api/base/trspotType")
public class TrspotTypeController {
    @Autowired
    private TrspotTypeService trspotTypeService;

    @ApiOperation(value = "获取运输类型列表")
    @GetMapping("/list")
    public ResponseInfo tree(@ModelAttribute TrspotTypeDto dto)  {
        QueryWrapper<TrspotTypeEntity> qw = new QueryWrapper<>();
        return ResponseUtil.success(trspotTypeService.list(qw));
    }
}
