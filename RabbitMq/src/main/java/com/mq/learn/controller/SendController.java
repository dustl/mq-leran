package com.mq.learn.controller;

import com.mq.learn.config.RabbitAdminConfig;
import com.mq.learn.config.TopicConfig;
import com.mq.learn.fanout.FanoutSender;
import com.mq.learn.topic.TopicSender1;
import com.mq.learn.util.RabbitUtil;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:liwy
 * @date: 22.8.18
 * @Version:1.0
 */
@RestController
@RequestMapping("/send")
public class SendController {

    @Autowired
    private TopicSender1 topicSender1;

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private RabbitUtil rabbitUtil;

    @Autowired
    private TopicConfig topicConfig;

    @GetMapping("/sendTopic")
    public void sendTopic(String message) {
        topicSender1.send(message);
    }


    @GetMapping("/sendFanout")
    public void sendFanout(String message) {
        fanoutSender.fanoutSend(message);
    }

    @GetMapping("/addQueue")
    public void addBind(String queueName, int ttl) {
        Queue queue = rabbitUtil.addQueue(queueName, ttl);

        rabbitUtil.bindServiceA(queue,topicConfig.serviceExchange(),queueName);
    }

    @GetMapping("/sendDelayTask")
    public void sendDelay(String word,String queueName) {
        topicSender1.sendDelayTask(word,queueName);
    }

}
