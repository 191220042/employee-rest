package org.springframework.hateoas.examples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.examples.projo.User;
import org.springframework.hateoas.examples.result.Result;
import org.springframework.hateoas.examples.result.ResultFactory;
import org.springframework.hateoas.examples.service.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class LoginController {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @CrossOrigin
    @PostMapping( "/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        User user = userService.get(username, requestUser.getPassword());
        if (null == user) {
            return ResultFactory.buildFailResult(new String("登录失败"));
        } else {
            return ResultFactory.buildSuccessResult(user);
        }
    }

    @CrossOrigin
    @PostMapping( "/register/send")
    @ResponseBody
    public void registerSend(@RequestBody User requestUser, HttpSession session) {
        Random random = new Random(47);
        int a= random.nextInt(10);
        int b= random.nextInt(10);
        int c= random.nextInt(10);
        int d= random.nextInt(10);
        String code=""+a+b+c+d;

        String email=requestUser.getEmail();
        session.setAttribute("email",email);
        session.setAttribute("code",code);

        String text="验证码： "+code;
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(requestUser.getEmail());
        message.setSubject(new String("嘿嘿嘿"));
        message.setText(text);
        javaMailSender.send(message);
    }

    @CrossOrigin
    @PostMapping( "/register")
    @ResponseBody
    public Result register(@RequestBody User requestUser,HttpSession session) {
        int status = userService.register(requestUser,session);
        switch (status) {
            case 0:
                return ResultFactory.buildFailResult("用户名和密码不能为空");
            case 1:
                return ResultFactory.buildSuccessResult("注册成功");
            case 2:
                return ResultFactory.buildFailResult("用户已存在");
            case 3:
                return ResultFactory.buildFailResult("请输入正确格式的邮箱");
            case 4:
                return ResultFactory.buildFailResult("邮箱验证码错误");
        }
        return ResultFactory.buildFailResult("未知错误");
    }
}

