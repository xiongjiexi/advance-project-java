package cn.xiongjiexi.mq.core;

import lombok.SneakyThrows;

import java.util.HashMap;

public class JMq {

    private final String topic;

    private final int capacity;

    private JMessage[] queue;

    /**
     * 插入数组的偏移量
     */
    private int offset;

    /**
     * 读取数据的指针
     */
    private int index;

    public JMq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        queue = new JMessage[capacity];
        offset = -1;
        index = -1;
    }

    public boolean send(Object msg) {
        if (offset+1 >= capacity) return false;
        queue[offset+1] = new JMessage(new HashMap<>(), msg);
        offset++;
        return true;
    }

    @SneakyThrows
    public JMessage poll(long m) {
        if (index >= offset) return null;
        JMessage res = queue[index+1];
        index++;
        return res;
    }
}
