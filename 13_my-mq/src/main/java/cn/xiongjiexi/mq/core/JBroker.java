package cn.xiongjiexi.mq.core;

import java.util.HashMap;
import java.util.Map;

public class JBroker {
    private final int DEFAULT_CAPACITY = 10000;
    private String topic;
    private Map<String, JMq> map;

    public JBroker() {
        map = new HashMap<>();
    }

    public void createTopic(String topic) {
        map.putIfAbsent(topic, new JMq(topic, DEFAULT_CAPACITY));
    }

    public JMq findMq(String topic) {
        return map.get(topic);
    }
}
