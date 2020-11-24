package com.cargo.order.controller;


import com.cargo.order.service.GoodsCategoryService;
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
 * 商品分类 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-10-23
 */
@Api(tags = "货物种类列表")
@RestController
@RequestMapping("/api/base/goodsCategory")
public class GoodsCategoryController {
    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @ApiOperation(value = "获取货物种类列表")
    @GetMapping("/tree")
    public ResponseInfo tree()  {
        return ResponseUtil.success(goodsCategoryService.tree());
    }

}
