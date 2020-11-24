package com.commom.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title： RabbitConfig </p>
 * <p>Description： </p>
 * <p>Company：ail.tech </p>
 *
 * @author sujunxuan
 * @version V1.0
 * @date 2020/3/27 16:54
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

//    @Value("${abs.queue.default}")
//    private String defaultQueue;
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
//                log.info("消息发送成功:correlationData({}),ack({}),cause({})",
//                        correlationData, ack, cause));
//        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
//                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
//                        exchange, routingKey, replyCode, replyText, message));
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public Queue queueDefault() {
//        return new Queue(defaultQueue);
//    }

}
