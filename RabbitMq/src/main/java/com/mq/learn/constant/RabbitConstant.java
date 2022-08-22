package com.mq.learn.constant;

/**
 * @Author:liwy
 * @date: 22.8.18
 * @Version:1.0
 */
public class RabbitConstant {
    /**
     * 业务队列
     * */
    public static final String SERVICE_QUEUE_1 = "service.queue.1";
    public static final String SERVICE_QUEUE_2 = "service.queue.2";
    public static final String SERVICE_QUEUE_5 = "service.queue.5";

    /**
     * 注意 匹配规则！！：
     * # ： 匹配0个或多个单词,注意前面要加一个. 隔开，否则无法识别出,
     * 反例 :
     * 1 service.route.key.1#
     *
     * * ： 匹配一个单词  注意前面要加一个. 隔开，否则无法识别出
     * 反例 : service.route.key.1*
     * # 示例:
     * service.route.key.1.# ->可以匹配：
     * 1 service.route.key.1
     * 2 service.route.key.1.apple
     * 3 service.route.key.1.apple.server.server1
     * ....
     *
     * * 示例:
     * service.route.key.1.*
     * 1 service.route.key.1.apple
     * 2 service.route.key.1.ba
     * 3 service.route.key.1.c
     * 反例：
     * 1 service.route.key.1.abc.def
     *
     * */
    public static final String SERVICE_ROUTE_KEY_1 = "service.route.key.1.2.*";
    public static final String SERVICE_ROUTE_KEY_2 = "service.route.key.1.2.#";
    public static final String SERVICE_ROUTE_KEY_5 = "service.route.key.5.2.1";
    public static final String SERVICE_ROUTE_KEY_3 = "service.route.key.1.3.81234.1";

    public static final String SERVICE_EXANGE_1 = "service.exange.1";

    /**
     * 死信队列
     */

    public static final String DELAY_QUEUE_1 = "delay.queue.1";

    public static final String DEAD_EXANGE_1 = "dead.exange.1";

    public static final String DEAD_ROUTE_KEY = "dead.route.key.1";










}
