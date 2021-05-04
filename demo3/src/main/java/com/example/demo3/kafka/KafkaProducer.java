package com.example.demo3.kafka;

import com.example.demo3.model.KafkaUser;
import com.google.gson.GsonBuilder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: 冯广
 * @Date: 2021/5/3 01:44
 * @Description:
 */
@Log
@Component
public class KafkaProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.user}")
    private String topicUser;//topic名称

    /**
     * 发送用户消息
     *
     * @param user 用户信息
     */
    public void sendUserMessage(KafkaUser user) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = builder.create().toJson(user);
        kafkaTemplate.send(topicUser, message);
        log.info("\n生产消息至Kafka\n" + message);
    }
}
