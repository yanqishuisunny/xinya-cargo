package com.cargo.jpush.push.controller;

import com.cargo.jpush.push.model.dto.PushDto;
import com.cargo.jpush.push.sevice.PushService;
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
 * @Date: 2020/10/22 16:37
 */
@Api(tags = "极光推送_push")
@RestController
@RequestMapping("/api/message/push")
public class PushController {

    @Autowired
    PushService pushService;

    @ApiOperation(value = "推送消息")
    @PostMapping("/pushmsg")
    public ResponseInfo pushmsg(@RequestBody PushDto dto){

        pushService.sendPushMsg(dto);

        return ResponseUtil.success();

    }
}
