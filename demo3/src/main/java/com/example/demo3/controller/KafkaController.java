package com.example.demo3.controller;

import com.example.demo3.kafka.KafkaProducer;
import com.example.demo3.model.KafkaUser;
import com.example.demo3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 冯广
 * @Date: 2021/5/3 01:48
 * @Description:
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaUser user;

    @Autowired
    private KafkaProducer kafkaProducer;

    @RequestMapping("/createMsg")
    public void createMsg() {
        kafkaProducer.sendUserMessage(user);
    }
}
