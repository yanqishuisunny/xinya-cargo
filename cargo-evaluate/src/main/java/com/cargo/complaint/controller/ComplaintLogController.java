package com.cargo.complaint.controller;


import com.commom.supper.SuperController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 * 投诉log表 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-06
 */
@Api(tags = "投诉log表")
@RestController
@RequestMapping("/complaint-log")
public class ComplaintLogController extends SuperController {

}

