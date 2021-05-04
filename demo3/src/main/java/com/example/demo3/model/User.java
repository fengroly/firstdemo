package com.example.demo3.model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * Created by
 */
@Data
@ToString
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "pass_word")
    private String passWord;
    @TableField(value = "real_name")
    private String realName;
    @TableField(value = "create_time")
    private Date createTime;

    public User(Integer id, String userName, String passWord, String realName) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.realName = realName;
    }

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public User(Integer id, String userName, String passWord, String realName, Date createTime) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.realName = realName;
        this.createTime = createTime;
    }

    public User() {
    }
}