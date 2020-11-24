package com.cargo.order.controller;


import com.cargo.order.service.GoodsTypeService;
import com.commom.exception.BussException;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(tags = "货物类型列表")
@RestController
@RequestMapping("/api/base/goodsType")
public class GoodsTypeController {
    @Autowired
    private GoodsTypeService goodsTypeService;

    @ApiOperation(value = "获取货物类型列表")
    @GetMapping("/list")
    public ResponseInfo tree() {
        return ResponseUtil.success(goodsTypeService.list());
    }
}
