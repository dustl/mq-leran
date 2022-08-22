# 工程简介

结合代码，实践mq常用场景

### RabbitMq

1TopicExchange交换机匹配路由键消息。

2结合FanoutExchange广播消息。

3结合死信队列，实现自定义延时任务。

4保证消息可靠：ConfirmCallback，ReturnCallback

ConfirmCallback：当消息成功或者失败投递到对应的exchange,成功的ack为true,否则为false,可通过correlationData拿到消息信息，也可以自定义扩展该类。

ReturnCallback：当消息路由到队列失败,回调这个接口

思路：

发送消息前本地先保存，发送的方法加上捕获异常。如果ConfirmCallback成功回调那么改状态为成功状态。如果ReturnCallback回调，那么更改状态为失败路由。

定时任务再把表里的失败的信息，以及没发到broker消息的，再重发。

消费端处理：

1 手动ack.

2 消费过程有异常，打印日志。可以重回队列一定次数。

3 如果重试还不能解决，那么告警，入库。

4 ack消费成功。

消费端幂等：

每次业务成功后保存消息，记录id,消费成功入库。

下次查这个id是否存在，存在就消费过。

手动ack模式：

```
#  spring
#    listener:
#      simple:
        #每次只推送一个消息,只有一个消费者，而且每次推送一个，保证消息消费顺序
#        prefetch: 1
        # 手动ack,性能比自动ack慢 10倍左右
#        acknowledge-mode: manual
```







# 延伸阅读

