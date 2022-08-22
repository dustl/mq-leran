package com.mq.learn.fanout;

import com.mq.learn.callback.RabbitmqConfirmCallback;
import com.mq.learn.config.FanoutConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * @Author:liwy
 * @date: 22.8.15
 * @Version:1.0
 */
@Component
public class FanoutSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitmqConfirmCallback rabbitmqConfirmCallback;

    @PostConstruct
    public void  init() {
        rabbitTemplate.setConfirmCallback(rabbitmqConfirmCallback);
        rabbitTemplate.setReturnCallback(rabbitmqConfirmCallback);
    }


    public void fanoutSend(String message) {
        System.out.println("准备发送 :" +message);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend(FanoutConfig.EXCHANGE_1, "", message,correlationData);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
