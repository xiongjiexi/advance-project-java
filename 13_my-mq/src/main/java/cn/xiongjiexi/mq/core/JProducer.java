package cn.xiongjiexi.mq.core;

public class JProducer {
    private JBroker broker;
    private JMq mq;

    public JProducer(JBroker broker) {
        this.broker = broker;
    }

    public void send(String topic, Object msg) {
        JMq mq = broker.findMq(topic);
        mq.send(msg);
    }
}
