package cn.jessexiong.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;

@RestController
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @Autowired
    private JmsMessagingTemplate jms;

    @GetMapping("/queueSend")
    public String send(String text) {
        jms.convertAndSend(queue, text);
        return "success, queue.";
    }

    @GetMapping("/topicSend")
    public String receive(String text) {
        jms.convertAndSend(topic, text);
        return "success, topic.";
    }

}
