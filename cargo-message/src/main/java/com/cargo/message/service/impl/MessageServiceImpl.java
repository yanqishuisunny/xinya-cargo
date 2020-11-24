package com.cargo.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cargo.message.entity.MessageEntity;

import com.cargo.message.mapper.MessageMapper;
import com.cargo.message.service.MessageService;
import com.cargo.message.vo.MessageVo;
import com.commom.core.BeanConverter;
import com.cargo.message.dto.MessageDto;
import com.commom.shiro.ShiroUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageEntity> implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public IPage<MessageVo> msgList(MessageDto dto, Page<MessageEntity> page) {

        String orgId = ShiroUtil.getOrgId();
        QueryWrapper<MessageEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("type",dto.getType());
        wrapper.eq("gmt_create",dto.getGmtCreate());
        wrapper.eq("org_id",orgId);
        IPage<MessageEntity> convert = page(page , wrapper);

        IPage<MessageVo> result = BeanConverter.convert(MessageVo.class, convert);
        return result;
    }
}
