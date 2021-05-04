package com.example.demo3.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Auther: 冯广
 * @Date: 2021/5/3 01:35
 * @Description:
 */
@Api(value = "desc of class")
@RestController
public class SwaggerController {

    /* 方法注解 */
    @ApiOperation(value = "desc of method", notes = "")
    @GetMapping(value="/swagger")
    public Object hello( /* 参数注解 */ @ApiParam(value = "desc of param" , required=true ) @RequestParam String name) {
        return "Hello " + name + "!";
    }
}
