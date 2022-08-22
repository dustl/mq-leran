package com.mq.learn.listener;

import com.mq.learn.constant.RabbitConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:liwy
 * @date: 22.8.18
 * @Version:1.0
 */
@Slf4j
@Component
public class ServiceTopicListener {

    public enum Action {
        // 成功
        SUCCESS,

        // 重试
        RETRY,

        // 拒绝
        REJECT,
        ;


    }

    private ConcurrentHashMap<String, Integer> maxTryMap = new ConcurrentHashMap<>(1024);

/**
 *注意：
 * 延时消费的时候，不要设置监听业务的队列，否则无法推送到死信队列。
 *
 * */

 @RabbitListener(queues = {RabbitConstant.SERVICE_QUEUE_1})
    public void recive(Message message) {
        byte[] body = message.getBody();
        String s = new String(body);
        log.info("收到 {} 消息 : {}", RabbitConstant.SERVICE_QUEUE_1,s);
    }

    @RabbitListener(queues = {RabbitConstant.SERVICE_QUEUE_2})
    public void recive2(Message message) {
        byte[] body = message.getBody();
        String s = new String(body);
        log.info("收到 {} 消息 : {}", RabbitConstant.SERVICE_QUEUE_2,s);
    }


    @RabbitListener(queues = {RabbitConstant.SERVICE_QUEUE_5})
    public void recive3(Message message , Channel channel) {
     //  消费位置
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String id =(String) message.getMessageProperties().getHeader("spring_returned_message_correlation");
        log.info("消息id: {}",id);
        byte[] body = message.getBody();
        String s = new String(body);
        Action action = Action.SUCCESS;
        log.info("收到 {} 消息 : {}", RabbitConstant.SERVICE_QUEUE_5,s);
        try {
            if (s.contains("error")) {
                throw new IllegalArgumentException("测试无需重回队列");
            }
            if (s.contains("bad")) {
                throw new Exception("测试可重回队列异常");
            }
        } catch (IllegalArgumentException e) {
            action = Action.REJECT;

        } catch (Exception e) {
            action = Action.RETRY;
        }finally {
            try {
                if (action == Action.SUCCESS) {
                    //multiple 表示是否批量处理。true表示批量ack处理小于tag的所有消息。false则处理当前消息
                    log.info("成功ack");
                    channel.basicAck(deliveryTag,false);
                }
                if (action == Action.REJECT) {
                       //拒绝策略，并且从队列中删除
                    log.error("进入拒绝");
                    channel.basicNack(deliveryTag,false,false);
                }
                if (action == Action.RETRY) {
                    log.error("进入重试");
                    // 考虑睡眠再重试

                    Integer merge = maxTryMap.merge(id, 1, (oldV, newV) -> oldV + newV);
                    if (merge > 2) {
                        log.info("单条信息重试次数超过最大数2,不重试");
                        channel.basicNack(deliveryTag,false,false);
                        return;
                    }
                    log.error("message : {}",message);
                    channel.basicNack(deliveryTag,false,true);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }







}
