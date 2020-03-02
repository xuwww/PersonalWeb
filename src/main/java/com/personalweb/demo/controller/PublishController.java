package com.personalweb.demo.controller;

import com.personalweb.demo.mapper.QuestionMapper;
import com.personalweb.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag){
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(title);
        question.setTag(tag);
        question.setDescription(description);
        question.setCreator(1);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified((long)0);
        questionMapper.create(question);
        return "redirect:/";
    }
}
