package com.example.demo3.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo3.config.KaptchaConfig;
import com.example.demo3.mapper.UserMapper;
import com.example.demo3.model.ShiroUser;
import com.example.demo3.model.User;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo3.util.RedisUtil;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/")
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);


    /**
     * session中的验证码
     */
    private String SHIRO_VERIFY_SESSION = "verifySessionCode";
    /**
     * 错误后的跳转地址
     */
    private String ERROR_CODE_URL = "login";
    /**
     * 成功后的跳转地址
     */
    private String SUCCESS_CODE_URL = "/success";
    /**
     * 验证失败提示
     */
    private String ERROR_PASSWORD = "密码不正确";
    private String ERROR_ACCOUNT = "账户不存在";
    private String ERROR_STATUS = "状态不正常";
    private String ERROR_KAPTCHA = "验证码不正确";

//    @Autowired
//    private KaptchaConfig kaptchaConfig;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;


    //mybtis-plus相关start
    //http://localhost:8888/getUserList
    @GetMapping("getUserList")
    @ResponseBody
    public Object getUserList() {
        return userMapper.getUserList();
    }

    //http://localhost:8888/getUserListByName?userName=xiaoli
    @GetMapping("getUserListByName")
    @ResponseBody
    public List<User> getUserListByName(String userName) {
        Map map = new HashMap();
        map.put("user_name", userName);
        return userMapper.selectByMap(map);
    }

    //http://localhost:8888/saveUser?userName=xiaoli&userPassword=111
    @GetMapping("saveUser")
    @ResponseBody
    public String saveUser(String userName, String passWord) {
        User user = new User(userName, passWord);
        Integer index = userMapper.insert(user);
        if (index > 0) {
            return "新增用户成功。";
        } else {
            return "新增用户失败。";
        }
    }

    //http://localhost:8888/updateUser?id=5&userName=xiaoli&userPassword=111
    @GetMapping("updateUser")
    @ResponseBody
    public String updateUser(Integer id, String userName, String passWord, String realName) {
        User user = new User(id, userName, passWord, realName);
        Integer index = userMapper.updateById(user);
        if (index > 0) {
            return "修改用户成功，影响行数" + index + "行。";
        } else {
            return "修改用户失败，影响行数" + index + "行。";
        }
    }

    //http://localhost:8888/getUserById?userId=1
    @GetMapping("getUserById")
    @ResponseBody
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    //http://localhost:8888/getUserListByPage?pageNumber=1&pageSize=2
    @GetMapping("getUserListByPage")
    @ResponseBody
    public List<User> getUserListByPage(Integer pageNo, Integer pageSize) {
        Page<User> page = new Page<>(pageNo, pageSize);
        EntityWrapper<User> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", "冯广");
        return userMapper.selectPage(page, entityWrapper);
    }
    //mybtis-plus相关end


    //thymeleaf静态资源相关start
//    @GetMapping("login")
//    public String login() {
//        return "login";
//    }
    //thymeleaf静态资源相关end

    //shiro 认证start
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/success"})
    public String success(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        ShiroUser user = (ShiroUser) currentUser.getPrincipal();
        model.addAttribute("username", user.getUsername());
        return "success";
    }

    @PostMapping("login")
    public String login(String username, String password, String verifyCode, boolean rememberMe, Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
        // 获取session中的验证码
        String verCode = (String) currentUser.getSession().getAttribute(SHIRO_VERIFY_SESSION);
        if ("".equals(verifyCode) || (!verCode.equals(verifyCode))) {
            model.addAttribute("msg", ERROR_KAPTCHA);
            return ERROR_CODE_URL;
        }
        try {
            //主体提交登录请求到SecurityManager
           //token.setRememberMe(rememberMe);
            currentUser.login(token);
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", "密码不正确");
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", "账号不存在");
        } catch (AuthenticationException ae) {
            model.addAttribute("msg", "状态不正常");
        }
        if (currentUser.isAuthenticated()) {
            System.out.println("认证成功");
            model.addAttribute("username", username);
            return "success";
        } else {
            token.clear();
            return "login";
        }
    }
    //shiro 认证end

    //shiro 权限start
//    @GetMapping("/dog")
//    @ResponseBody
//    public String dog(){
//        Subject subject = SecurityUtils.getSubject();
//        if(subject.hasRole("dog")){
//            return "dog√";
//        }
//        else {
//            return  "dog×";
//        }
//    }
//
//    @GetMapping("/cat")
//    @ResponseBody
//    public String cat(){
//        Subject subject = SecurityUtils.getSubject();
//        if(subject.hasRole("cat")){
//            return "cat√";
//        }
//        else {
//            return  "cat×";
//        }
//    }
//
//    @GetMapping("/sing")
//    @RequiresRoles("cat")
//    @ResponseBody
//    public String sing(){
//        return "sing";
//    }
//    @GetMapping("/jump")
//    @RequiresPermissions("jump")
//    public String jump(){
//        return "jump";
//    }
//    @GetMapping("/rap")
//    @ResponseBody
//    public String rap(){
//        Subject subject = SecurityUtils.getSubject();
//        if(subject.isPermitted("rap")){
//            return "rap";
//        }else{
//            return "没权限你Rap个锤子啊!";
//        }
//
//    }
//    @GetMapping("/basketball")
//    @ResponseBody
//    public String basketball(){
//        Subject subject = SecurityUtils.getSubject();
//        if(subject.isPermitted("basketball")){
//            return "basketball";
//        }else{
//            return "你会打个粑粑球!";
//        }
//    }
    @GetMapping("/cat")
    @ResponseBody
    public String cat() {
        return "cat";
    }

    @GetMapping("/dog")
    @ResponseBody
    public String dog() {
        return "dog";
    }

    @GetMapping("/sing")
    @ResponseBody
    public String sing() {
        return "sing";
    }

    @GetMapping("/jump")
    @ResponseBody
    public String jump() {
        return "jump";
    }

    @GetMapping("/rap")
    @ResponseBody
    public String rap() {
        return "rap";
    }

    @GetMapping("/basketball")
    @ResponseBody
    public String basketball() {
        return "basketball";
    }

    @GetMapping("/403")
    public String page_403() {
        return "403";
    }

    /**
     * 获取验证码
     *
     * @param response
     */
    @GetMapping("/getCode")
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        byte[] verByte = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = KaptchaConfig.defaultKaptcha().createText();
            request.getSession().setAttribute(SHIRO_VERIFY_SESSION, createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = KaptchaConfig.defaultKaptcha().createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        verByte = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(verByte);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    //shiro 权限end

    //redis start
//    @RequestMapping(value = "/hello/{id}")
//    public String hello(@PathVariable(value = "id") String id) {
//        //查询缓存中是否存在
//        boolean hasKey = redisUtil.exists(id);
//        String str = "";
//        if (hasKey) {
//            //获取缓存
//            Object object = redisUtil.get(id);
//            log.info("从缓存获取的数据" + object);
//            str = object.toString();
//        } else {
//            //从数据库中获取信息
//            log.info("从数据库中获取数据");
//            str = JSONObject.toJSONString(userMapper.selectById(id));
//            //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
//            redisUtil.set(id, str, 10L, TimeUnit.MINUTES);
//            log.info("数据插入缓存" + str);
//        }
//        return str;
//    }
    //redis end
}
