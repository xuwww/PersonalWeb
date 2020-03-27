package com.personalweb.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.personalweb.demo.dto.*;
import com.personalweb.demo.exception.CustomizeErrorCode;
import com.personalweb.demo.model.User;
import com.personalweb.demo.service.NotificationService;
import com.personalweb.demo.service.QuestionService;
import com.personalweb.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          Model model,
                          HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(null == user){
            return "redirect:/";
        }
        if ("question".equals(action)) {
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO<QuestionDTO> paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".equals(action)) {
            PaginationDTO<NotificationDTO> paginationDTO = notificationService.list(user.getId(), page, size);

            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }else if("category".equals(action.substring(0,8))){
            int category=Integer.parseInt(action.substring(9));
            PaginationDTO<QuestionDTO> paginationDTO = questionService.listByCategory(user.getId(), category,page, size);
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section",action);
        }
        return "profile";
    }

    @ResponseBody
    @RequestMapping(value="/profile/category", method=RequestMethod.POST)
    public Object categoryAdd(@RequestBody JSONObject jsonObject,
                              HttpServletRequest request){
        User user=(User)request.getSession().getAttribute("user");
        if(null == user){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        CustomizeErrorCode customizeErrorCode = userService.categoryAdd(user, jsonObject);
        if (customizeErrorCode != null) {
            return ResultDTO.errorOf(customizeErrorCode);
        }else{
            return ResultDTO.successOf();
        }

    }
}
