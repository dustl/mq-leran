package com.mq.learn.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @Author:liwy
 * @date: 22.8.15
 * @Version:1.0
 * rabbitmq 三种数据放丢失 :
 * 1 消息持久化
 * 2 生产者 confirm机制 确认成功投递到exchange,以及失败投递都会回调 com.mq.learn.callback.RabbitmqConfirmCallback#confirm(org.springframework.amqp.rabbit.connection.CorrelationData, boolean, java.lang.String)
 *   returnedMessage 负责失败理由到队列的信息处理
 * 3 消费者 确认消费机制
 */
@Component
public class RabbitmqConfirmCallback  implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private Logger logger = LoggerFactory.getLogger(RabbitmqConfirmCallback.class);

    /**
     * 监听消息是否到达Exchange
     *
     * @param correlationData 包含消息的唯一标识的对象，可以通过扩展该类，丰富消息
     * @param ack             true 标识 ack，false 标识 nack
     * @param cause           nack 投递失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            logger.info("消息投递成功~消息Id：{}", correlationData.getId());
        } else {
            logger.error("消息投递失败，Id：{}，错误提示：{}", correlationData.getId(), cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("消息没有路由到队列，获得返回的消息");
        logger.info("body :{}", new String(message.getBody()));
        String correlationId = message.getMessageProperties().getCorrelationId();
        logger.info("messageid :{}" ,correlationId);
        logger.info("replyCode: {}", replyCode);
        logger.info("replyText: {}", replyText);
        logger.info("exchange: {}", exchange);
        logger.info("routingKey: {}", exchange);
        logger.info("------------> end <------------");
    }

    @SuppressWarnings("unchecked")
    private <T> T byteToObject(byte[] bytes, Class<T> clazz) {
        T t;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            t = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

}
