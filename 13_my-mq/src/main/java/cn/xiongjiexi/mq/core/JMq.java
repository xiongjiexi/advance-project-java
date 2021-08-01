package cn.xiongjiexi.mq.core;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class JMq {

    private final String topic;

    private final int capacity;

    private LinkedBlockingQueue<JMessage<String>> queue;

    public JMq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue<>(capacity);
    }

    public boolean send(Object msg) {
        return queue.offer(new JMessage(new HashMap<>(), msg));
    }

    @SneakyThrows
    public JMessage<String> poll(long m) {
        return queue.poll(m, TimeUnit.MILLISECONDS);
    }
}
