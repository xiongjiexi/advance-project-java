package cn.jessexiong.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerTopic {

    @JmsListener(destination = "ActiveMQTopic", containerFactory = "jmsListenerContainerTopic")
    public void receiveQueue(String text) {
        System.out.println("topic消息, 收到的报文为:" + text);
    }

}
