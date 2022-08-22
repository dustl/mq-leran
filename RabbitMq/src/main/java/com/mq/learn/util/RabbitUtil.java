package com.mq.learn.util;

import com.mq.learn.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:liwy
 * @date: 22.8.22
 * @Version:1.0
 */
@Component
public class RabbitUtil {

    @Autowired
    private RabbitAdmin rabbitAdmin;


    /**
     * @param queueName 队列名
     * @param ttl 如果在业务队列ttl【毫秒】之后没有被消费那么转移到死信队列
     * */
    public Queue addQueue(String queueName, int ttl) {
        Queue queue = QueueBuilder.durable(queueName)
                .deadLetterExchange(RabbitConstant.DEAD_EXANGE_1)
                .deadLetterRoutingKey(RabbitConstant.DEAD_ROUTE_KEY)
                .ttl(ttl).build();

        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    /**
     * 业务交换机绑定指定业务【任务】的队列。
     *
     * */
    public void bindServiceA(Queue queue, TopicExchange topicExchange, String routeKey) {
        Binding bind = BindingBuilder.bind(queue).to(topicExchange).with(routeKey);
        rabbitAdmin.declareBinding(bind);
    }

    /**
     * 删除业务队列(延时任务的队列)
     * */
    public void deleteQueue(String queueName) {
        rabbitAdmin.deleteQueue(queueName,true,true);
    }

}
