package com.cargo.unit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cargo.order.service.GoodsCategoryService;
import com.cargo.unit.dto.UnitDto;
import com.cargo.unit.entity.UnitEntity;
import com.cargo.unit.service.UnitService;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-10-26
 */
@Api(tags = "计量单位列表")
@RestController
@RequestMapping("/api/base/unit")
public class UnitController {
    @Autowired
    private UnitService unitService;

    @ApiOperation(value = "获取单位列表")
    @GetMapping("/list")
    public ResponseInfo tree(@ModelAttribute UnitDto dto)  {
        QueryWrapper<UnitEntity> qw = new QueryWrapper<>();
        if(dto.getUnitType()!=null && dto.getUnitType() !=0) {
            qw.eq("unit_type", dto.getUnitType());
        }
        return ResponseUtil.success(unitService.list(qw));
    }
}
