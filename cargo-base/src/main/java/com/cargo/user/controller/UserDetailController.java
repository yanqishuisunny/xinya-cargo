package com.cargo.user.controller;


import com.commom.supper.SuperController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户详细情况表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-05
 */
@Api(tags = "用户详细情况表")
@RestController
@RequestMapping("/api/base/userDetail")
public class UserDetailController extends SuperController {

}

