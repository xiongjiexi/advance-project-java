package cn.jessexiong.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerQueue {

    @JmsListener(destination = "ActiveMQQueue",containerFactory = "jmsListenerContainerQueue")
    public void receiveQueue(String text) {
        System.out.println("queue点对点，收到的报文为:"+text);
    }

}
