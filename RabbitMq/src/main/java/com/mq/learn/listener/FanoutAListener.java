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
@Slf4j
@Component
public class FanoutAListener {

    @RabbitListener(queues = {FanoutConfig.QUEUE_A})
    public void process(String message) {
       log.info("{}接受到message: {} " ,FanoutConfig.QUEUE_A, message);
    }

    @RabbitListener(queues = {FanoutConfig.QUEUE_D})
    public void processB(String message) {
        log.info("{}接受到message:{} " ,FanoutConfig.QUEUE_D, message);
    }


}
