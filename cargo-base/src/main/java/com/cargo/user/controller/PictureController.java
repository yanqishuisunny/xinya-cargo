package com.cargo.user.controller;


import com.commom.supper.SuperController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 图片表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Api(tags = "图片表")
@RestController
@RequestMapping("/api/base/picture")
public class PictureController extends SuperController {

}

