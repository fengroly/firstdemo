package com.example.demo3.mapper;
import com.alibaba.fastjson.JSON;
import com.example.demo3.model.ExcelUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: 冯广
 * @Date: 2021/4/28 23:25
 * @Description:
 */
@Component
public class ExcelUserMapper {
    public void save(List<ExcelUser> list) {
        System.out.println(JSON.toJSONString(list));
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
    }
}
