package com.example.demo3.model;

/**
 * @Auther: 冯广
 * @Date: 2021/5/2 19:09
 * @Description:
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ESUser {
    private String name;
    private Integer age;
}
