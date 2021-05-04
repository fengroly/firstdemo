package com.example.demo3.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by
 */
@Data
@ToString
public class JsonUser implements Serializable {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("pass_word")
    private String passWord;
    @JsonProperty("real_name")
    private String realName;
    @JsonProperty("create_time")
    private Date createTime;
}