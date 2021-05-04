package com.example.demo3.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 冯广
 * @Date: 2021/5/3 21:12
 * @Description:
 */
@RestController
@RequestMapping("/hystrix")
@DefaultProperties(defaultFallback = "defaultFail")
public class HystrixController {

    @HystrixCommand(fallbackMethod = "fail1")
    @GetMapping("/test1")
    public String test1() {
        throw new RuntimeException();
    }

    private String fail1() {
        System.out.println("fail1");
        return "fail1";
    }

    @HystrixCommand(fallbackMethod = "fail2")
    @GetMapping("/test2")
    public String test2() {
        throw new RuntimeException();
    }

    @HystrixCommand(fallbackMethod = "fail3")
    private String fail2() {
        System.out.println("fail2");
        throw new RuntimeException();
    }

    @HystrixCommand
    private String fail3() {
        System.out.println("fail3");
        throw new RuntimeException();
    }

    private String defaultFail() {
        System.out.println("default fail");
        return "default fail";
    }
}
