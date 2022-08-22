package com.mq.learn.topic;

import com.mq.learn.constant.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author:liwy
 * @date: 22.8.18
 * @Version:1.0
 */
@Slf4j
@Component
public class TopicSender1 {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送的时候，发送的是业务的交换机，以及路由
     * */
    public void send(String message) {
        log.info("发送 : {} ", message);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConstant.SERVICE_EXANGE_1,
                RabbitConstant.SERVICE_ROUTE_KEY_3,
                message,correlationData);
    }


    /**
     * 自定义延时任务，自定义延时时间
     * */
    public void sendDelayTask(String message,String queueName) {
        log.info("发送延时任务 : {} ", message);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConstant.SERVICE_EXANGE_1,
                queueName,message,correlationData);
    }


}
