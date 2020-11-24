package com.cargo.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cargo.message.entity.MessageEntity;
import com.cargo.message.vo.MessageVo;
import com.cargo.message.dto.MessageDto;

/**
 * <p>
 * 消息 服务类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
public interface MessageService extends IService<MessageEntity> {

    IPage<MessageVo> msgList(MessageDto dto, Page<MessageEntity> page);
}
