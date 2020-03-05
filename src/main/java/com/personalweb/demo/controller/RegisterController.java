package com.personalweb.demo.controller;

import com.personalweb.demo.mapper.UserMapper;
import com.personalweb.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class RegisterController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam("accountId") String accountId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             Model model){
        model.addAttribute("accountId",accountId);
        model.addAttribute("password",password);
        model.addAttribute("name",name);
//        model.addAttribute("avatarUrl",avatarUrl);暂不支持
        if(null==accountId||accountId.equals("")){
            model.addAttribute("error","用户名不能为空");
            return "register";
        }
        if(null==password||password.equals("")) {
            model.addAttribute("error", "密码不能为空");
            return "register";
        }
        if(null==name||name.equals("")){
            model.addAttribute("error","昵称不能为空");
            return "register";
        }

        User user = new User();
        user.setAccountId(accountId);
        user.setName(name);
        user.setPassword(password);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setToken(UUID.randomUUID().toString());
        user.setAvatarUrl("/imgs/defaultUser.png");
        userMapper.insert(user);

        return "redirect:/signIn";
    }

}
