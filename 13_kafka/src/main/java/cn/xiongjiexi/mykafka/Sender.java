package cn.xiongjiexi.mykafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);//原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/kafka/spring-kafka-and-spring-boot-configuration.html#

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.topic.t1}")
    private String topic;

    public void send(String msg) {
        LOG.info("sending message='{}' to topic='{}'", msg, topic);
        kafkaTemplate.send(topic, msg);
    }


}
