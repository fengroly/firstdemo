package com.example.demo3.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo3.config.KaptchaConfig;
import com.example.demo3.mapper.UserMapper;
import com.example.demo3.model.ShiroUser;
import com.example.demo3.model.User;
import com.example.demo3.util.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/")
public class HelloController {

    public static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    //redis start
    @RequestMapping(value = "/hello/{id}")
    public String hello(@PathVariable(value = "id") String id) {
        //查询缓存中是否存在
        boolean hasKey = redisUtil.exists(id);
        String str = "";
        if (hasKey) {
            //获取缓存
            Object object = redisUtil.get(id);
            log.info("从缓存获取的数据" + object);
            str = object.toString();
        } else {
            //从数据库中获取信息
            log.info("从数据库中获取数据");
            str = JSONObject.toJSONString(userMapper.selectById(id));
            //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
            redisUtil.set(id, str, 1000L*1000, TimeUnit.MINUTES);
            log.info("数据插入缓存" + str);
        }
        return str;
    }
    //redis end
}
