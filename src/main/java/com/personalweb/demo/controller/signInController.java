package com.personalweb.demo.controller;

import com.personalweb.demo.mapper.UserMapper;
import com.personalweb.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class signInController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/signIn")
    public String signIn() {
        return "signIn";
    }

    @PostMapping("/signIn")
    public String doSignIn(@RequestParam("userId") String userId,
                           @RequestParam("password") String password,
                           HttpServletResponse response,
                           Model model) {

        model.addAttribute("userId", userId);
        model.addAttribute("password", password);

        if (userId == null || userId.equals("")) {
            model.addAttribute("error", "用户名不能为空");
            return "signIn";
        }
        if (password == null || password.equals("")) {
            model.addAttribute("error", "密码不能为空");
            return "signIn";
        }
        User user = userMapper.findByAccountId(userId);
        if (null == user) {
            model.addAttribute("error", "用户不存在");
            return "signIn";
        } else if (!password.equals(user.getPassword())) {
            model.addAttribute("error", "密码错误");
            return "signIn";
        } else {
            response.addCookie(new Cookie("token", user.getToken()));
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
