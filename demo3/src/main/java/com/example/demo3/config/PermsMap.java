package com.example.demo3.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 冯广
 * @Date: 2021/5/1 18:40
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "permission-config")
public class PermsMap {

    private List<Map<String,String>> perms;

    public List<Map<String, String>> getPerms() {
        return perms;
    }

    public void setPerms(List<Map<String, String>> perms) {
        this.perms = perms;
    }
}