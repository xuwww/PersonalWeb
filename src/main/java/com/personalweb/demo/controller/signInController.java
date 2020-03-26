package com.personalweb.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.personalweb.demo.dto.ResultDTO;
import com.personalweb.demo.dto.UserSignInDTO;
import com.personalweb.demo.exception.CustomizeErrorCode;
import com.personalweb.demo.mapper.UserMapper;
import com.personalweb.demo.model.User;
import com.personalweb.demo.model.UserExample;
import com.personalweb.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.List;

@Controller
public class signInController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public Object SignIn(@RequestBody JSONObject jsonObject,
                           HttpServletResponse response) {
        UserSignInDTO userSignInDTO=userService.signIn(jsonObject);

        if(userSignInDTO.getProperty()==0){
            return ResultDTO.errorOf(userSignInDTO.getCustomizeErrorCode());
        }else if(userSignInDTO.getProperty()==1){
            response.addCookie(new Cookie("token", userSignInDTO.getToken()));
            return ResultDTO.successOf();
        }
        return ResultDTO.errorOf(CustomizeErrorCode.UNKNOWN_ERROR);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
