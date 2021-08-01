package cn.xiongjiexi.mq.core;

public class JConsumer {
    private JBroker broker;
    private JMq mq;

    public JConsumer(JBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        mq = broker.findMq(topic);
    }

    public JMessage poll(long m) {
        return mq.poll(m);
    }
}
