package com.mq.learn.config;

import com.mq.learn.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sun.security.provider.PolicySpiFile;

/**
 * @Author:liwy
 * @date: 22.8.18
 * @Version:1.0
 */
@Component
public class TopicConfig {

    /**
     *
     * 配置业务队列对应死信队列
     * 注意 ：
     * 当修改延时时间后，队列注册了就无法修改，需要删除原队列，再注册上去
     * */

    @Bean
    public Queue serviceQueue() {
        return QueueBuilder.durable(RabbitConstant.SERVICE_QUEUE_1)
                // 设置 死信队列的交换机
                .deadLetterExchange(RabbitConstant.DEAD_EXANGE_1)
                // 设置死信队列的路由规则
                .deadLetterRoutingKey(RabbitConstant.DEAD_ROUTE_KEY)
                // 设置延时时间，ms
                .ttl(7000).build();
    }
    @Bean
    public Queue serviceQueue2() {
        return QueueBuilder.durable(RabbitConstant.SERVICE_QUEUE_2)
                // 设置 死信队列的交换机
                .deadLetterExchange(RabbitConstant.DEAD_EXANGE_1)
                // 设置死信队列的路由规则
                .deadLetterRoutingKey(RabbitConstant.DEAD_ROUTE_KEY)
                // 设置延时时间，ms
                .ttl(3000).build();
    }
    @Bean
    public Queue serviceQueue5() {
        return QueueBuilder.durable(RabbitConstant.SERVICE_QUEUE_5)
                // 设置 死信队列的交换机
                .deadLetterExchange(RabbitConstant.DEAD_EXANGE_1)
                // 设置死信队列的路由规则
                .deadLetterRoutingKey(RabbitConstant.DEAD_ROUTE_KEY)
                // 设置延时时间，ms
                .ttl(3000).build();
    }


    /**
     * 死信队列
     * */
    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(RabbitConstant.DELAY_QUEUE_1).build();
    }

    /**
     * 业务交换机
     * */
    @Bean
    public TopicExchange serviceExchange() {
        return ExchangeBuilder.topicExchange(RabbitConstant.SERVICE_EXANGE_1).durable(true).build();
    }
    /**
     * 死信交换机
     * */
    @Bean
    public TopicExchange delayExchange() {
        return ExchangeBuilder.topicExchange(RabbitConstant.DEAD_EXANGE_1).durable(true).build();
    }


    /**
     * 交换机绑定路由规则
     * */
    @Bean
    public Binding serviceBinding() {
        return  BindingBuilder.bind(serviceQueue()).to(serviceExchange()).with(RabbitConstant.SERVICE_ROUTE_KEY_1);
    }

    /**
     * 交换机绑定路由规则
     * */
    @Bean
    public Binding service2Binding() {
        return  BindingBuilder.bind(serviceQueue2()).to(serviceExchange()).with(RabbitConstant.SERVICE_ROUTE_KEY_2);
    }

    /**
     * 交换机绑定路由规则
     * */
    @Bean
    public Binding service5Binding() {
        return  BindingBuilder.bind(serviceQueue5()).to(serviceExchange()).with(RabbitConstant.SERVICE_ROUTE_KEY_5);
    }
    /**
    * 死信队列交换机绑定路由规则
    * */
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(deadQueue()).to(delayExchange()).with(RabbitConstant.DEAD_ROUTE_KEY);
    }














}
