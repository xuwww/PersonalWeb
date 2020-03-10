package com.personalweb.demo.controller;

import com.personalweb.demo.dto.NotificationDTO;
import com.personalweb.demo.enums.NotificationEnum;
import com.personalweb.demo.model.User;
import com.personalweb.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable(name = "id") Long id,
                               HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return "redirect:/signIn";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType()
                || NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}
