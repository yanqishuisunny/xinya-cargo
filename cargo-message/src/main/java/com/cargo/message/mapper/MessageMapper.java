package com.cargo.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cargo.message.entity.MessageEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 消息 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-11-16
 */
@Repository
public interface MessageMapper extends BaseMapper<MessageEntity> {

}
