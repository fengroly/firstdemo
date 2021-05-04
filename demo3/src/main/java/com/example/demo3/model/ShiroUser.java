package com.example.demo3.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: 冯广
 * @Date: 2021/5/1 17:10
 * @Description:
 */
public class ShiroUser implements Serializable {
    private String username;
    private String password;
    private String salt;
    private Boolean available;
    private String role;
    private List<String> permissions;

    public ShiroUser(String username, String password, String salt, Boolean available, String role, List<String> permissions) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.available = available;
        this.role = role;
        this.permissions = permissions;
    }

    public ShiroUser(String username, String password, Boolean available, String role, List<String> permissions) {
        this.username = username;
        this.password = password;
        this.available = available;
        this.role = role;
        this.permissions = permissions;
    }

    public ShiroUser(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取MD5盐  这里用username+salt实现
     * @return
     */
    public String getSalt() {
        return username+salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "ShiroUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
//                ", salt='" + salt + '\'' +
                ", available=" + available +
                ", role='" + role + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
