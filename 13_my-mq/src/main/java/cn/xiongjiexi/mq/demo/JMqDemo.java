package cn.xiongjiexi.mq.demo;

import cn.xiongjiexi.mq.core.JBroker;
import cn.xiongjiexi.mq.core.JConsumer;
import cn.xiongjiexi.mq.core.JMessage;
import cn.xiongjiexi.mq.core.JProducer;
import lombok.SneakyThrows;

public class JMqDemo {

    @SneakyThrows
    public static void main(String[] args) {

        String topic = "jesse.test";
        JBroker broker = new JBroker();
        broker.createTopic(topic);

        JConsumer consumer = new JConsumer(broker);
        consumer.subscribe(topic);
        final boolean[] flag = new boolean[1];
        flag[0] = true;
        new Thread(() -> {
            while (flag[0]) {
                JMessage<Order> message = consumer.poll(100);
                if(null != message) {
                    System.out.println(message.getBody());
                }
            }
            System.out.println("程序退出。");
        }).start();

        JProducer producer = new JProducer(broker);
        for (int i = 0; i < 1000; i++) {
            Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            producer.send(topic, new JMessage<>(null, order));
        }
        Thread.sleep(500);
        System.out.println("点击任何键，发送一条消息；点击q或e，退出程序。");
        while (true) {
            char c = (char) System.in.read();
            if(c > 20) {
                System.out.println(c);
                producer.send(topic, new JMessage(null, new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.52d)));
            }

            if( c == 'q' || c == 'e') break;
        }

        flag[0] = false;
    }
}