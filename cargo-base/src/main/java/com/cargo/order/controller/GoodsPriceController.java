package com.cargo.order.controller;


import com.cargo.order.service.GoodsPriceService;
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
@Api(tags = "计费方式列表")
@RestController
@RequestMapping("/api/base/goodsPrice")
public class GoodsPriceController {
    @Autowired
    private GoodsPriceService goodsPriceService;

    @ApiOperation(value = "获取计费方式列表")
    @GetMapping("/list")
    public ResponseInfo tree() {
        return ResponseUtil.success(goodsPriceService.list());
    }
}
