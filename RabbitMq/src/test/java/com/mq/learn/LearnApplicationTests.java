package com.mq.learn;

import com.mq.learn.fanout.FanoutSender;
import com.mq.learn.topic.TopicSender1;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
class LearnApplicationTests {

    @Autowired
    private FanoutSender fanoutSender;

    @Autowired
    private TopicSender1 topicSender1;
    @Test
    void contextLoads() {
        for (int i = 0; i < 10000000; i++) {
            fanoutSender.fanoutSend("你好，我来了" + i);

        }
    }

    @Test
    public void sendTopic() {
        topicSender1.send("业务消息1....");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
