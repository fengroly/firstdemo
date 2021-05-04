package com.example.demo3.model;

/**
 * @Auther: 冯广
 * @Date: 2021/5/2 19:09
 * @Description:
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class KafkaUser {
    private Integer id;
    private String name;
    private Integer age;
}
