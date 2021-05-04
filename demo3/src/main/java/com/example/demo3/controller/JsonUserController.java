package com.example.demo3.controller;

import com.example.demo3.mapper.UserMapper;
import com.example.demo3.model.JsonUser;
import com.example.demo3.model.Response;
import com.example.demo3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/")
public class JsonUserController {
    @Autowired
    private UserMapper userMapper;


    //http://localhost:8888/getUserList
    @GetMapping("getJsonUserList")
    @ResponseBody
    public Response getJsonUserList() {
        Response response = new Response();
        return Response.success(userMapper.getJsonUserList());
    }


}
