package com.personalweb.demo.controller;

import com.personalweb.demo.dto.NotificationDTO;
import com.personalweb.demo.dto.PaginationDTO;
import com.personalweb.demo.dto.QuestionDTO;
import com.personalweb.demo.model.User;
import com.personalweb.demo.service.NotificationService;
import com.personalweb.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size,
                          Model model,
                          HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if(null == user){
            return "redirect:/signIn";
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

/*    @GetMapping("/profile/question/{categoryId}")
    public String category(@PathVariable(name ="categoryId") int category,
                           @RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "size", defaultValue = "5") Integer size,
                           Model model,
                           HttpServletRequest request){
        User user= (User)request.getSession().getAttribute("user");
        if(null == user){
            return "redirect:/signIn";
        }

        PaginationDTO<QuestionDTO> paginationDTO = questionService.listByCategory(user.getId(), category,page, size);
        model.addAttribute("pagination",paginationDTO);
        model.addAttribute("section","category");
        return "profile";
    }*/
}
