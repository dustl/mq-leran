package com.mq.learn.listener;

import com.mq.learn.config.FanoutConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author:liwy
 * @date: 22.8.15
 * @Version:1.0
 * 广播
 */
@Component
@Slf4j
public class FanoutBListener {

    @RabbitListener(queues = FanoutConfig.QUEUE_B)
    public void receive(String message) {
        log.info("{}接收到 :{}" ,FanoutConfig.QUEUE_B,message);
    }
}
