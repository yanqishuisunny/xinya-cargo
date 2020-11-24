package com.cargo.user.controller;


import com.commom.supper.SuperController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 企业用户关联表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-10-29
 */
@Api(tags = "企业用户关联表")
@RestController
@RequestMapping("/api/base/association")
public class OrgUserAssociationController extends SuperController {

}

