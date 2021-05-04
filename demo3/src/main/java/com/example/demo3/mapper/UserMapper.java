package com.example.demo3.mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo3.model.JsonUser;
import com.example.demo3.model.User;
import java.util.List;

/**
 * @Auther: 冯广
 * @Date: 2021/4/28 23:25
 * @Description:
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> getUserList();
    List<JsonUser> getJsonUserList();
}
