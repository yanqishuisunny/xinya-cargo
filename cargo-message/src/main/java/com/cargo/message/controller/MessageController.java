package com.cargo.message.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.message.entity.MessageEntity;
import com.cargo.message.service.MessageService;
import com.cargo.message.vo.MessageVo;
import com.commom.supper.SuperController;
import com.commom.utils.ResponseInfo;
import com.commom.utils.ResponseUtil;
import com.cargo.message.dto.MessageDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 消息 前端控制器
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Api(tags = "消息中心")
@RestController
@RequestMapping("/api/message/message")
public class MessageController extends SuperController {

    @Autowired
    MessageService messageService;

    @ApiOperation(value = "消息列表")
    @PostMapping("/list")
    public ResponseInfo msgList(MessageDto dto){

        Page<MessageEntity> page = this.getPage(false);
        IPage<MessageVo> list = messageService.msgList(dto, page);

        return ResponseUtil.success(list);
    }
}

