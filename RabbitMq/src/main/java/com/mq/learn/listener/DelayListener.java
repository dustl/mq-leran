package com.mq.learn.listener;

import com.mq.learn.constant.RabbitConstant;
import com.mq.learn.util.RabbitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:liwy
 * @date: 22.8.22
 * @Version:1.0
 */
@Component
@Slf4j
public class DelayListener {

    @Autowired
    private RabbitUtil rabbitUtil;

    /**
     * 消费死信队列的任务
     * 当前【死信队列+ttl】实现延时消息，可以用作延时任务，如果自定一延时时间是动态增删队列的。
     * 如果是按固定等级延时，可以预先定义好队列
     * */
    @RabbitListener(queues = {RabbitConstant.DELAY_QUEUE_1})
    public void reciveDelay(Message message) {
        byte[] body = message.getBody();
        String s = new String(body);
        log.info("messageProperties: {}",message.getMessageProperties());
        String queue = (String) message.getMessageProperties().getXDeathHeader().get(0).get("queue");
        log.info("死信队列收到 {} 消息 : {}", RabbitConstant.DELAY_QUEUE_1,s);
        log.info("删除队列：{}", queue);
        rabbitUtil.deleteQueue(queue);

    }


}
