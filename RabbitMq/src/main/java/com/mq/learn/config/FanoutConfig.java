package com.mq.learn.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:liwy
 * @date: 22.8.15
 * @Version:1.0
 */
@Configuration
public class FanoutConfig {

    public static final String EXCHANGE_1 = "fanoutExchange1";

    public static final String QUEUE_A = "fanout.A";

    public static final String QUEUE_B = "fanout.B";

    public static final String QUEUE_D = "fanout.D";


    /**定义队列*/
    @Bean
    public Queue fanoutA() {
        return new Queue(QUEUE_A);
    }

    @Bean
    public Queue fanoutB() {
        return new Queue(QUEUE_B);
    }

    @Bean
    public Queue fanoutC() {
        return new Queue(QUEUE_D);
    }

    /**
     * 定义交换机
     */

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_1);
    }

    /**
     * 定义绑定规则
     */
    @Bean
    public Binding bindingQueueA() {
        return BindingBuilder.bind(fanoutA()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingQueueB() {
        return BindingBuilder.bind(fanoutB()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingQueueC() {
        return BindingBuilder.bind(fanoutC()).to(fanoutExchange());
    }







}
