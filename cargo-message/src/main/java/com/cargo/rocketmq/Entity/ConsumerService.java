package com.cargo.rocketmq.Entity;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class ConsumerService {
    @Autowired
    private DefaultMQProducer defaultProducer;

    @Autowired
    private TransactionMQProducer transactionProducer;

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @PostConstruct
    public void rocketmqMsgListener() throws MQClientException {
        //创建一个消费者
        //consumerGroup:做同样事情的Consumer归为同一个Group，应用必须设置，并保证命名唯一
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(defaultProducer.getProducerGroup());
        //设置名称srv地址
        consumer.setNamesrvAddr(defaultProducer.getNamesrvAddr());
        //实例名称
        consumer.setInstanceName(defaultProducer.getInstanceName());
        //实现订阅
        try {
            consumer.subscribe(defaultProducer.getInstanceName(), "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        //注册消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("本次消费已经成功");
                    System.out.println(msg.getMsgId()+"---"+new String(msg.getBody()));

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();
    }

}
