package com.personalweb.demo.mapper;

import com.personalweb.demo.model.Question;

public interface QuestionExtMapper {

    int incView(Question record);
    int incCommentCount(Question record);

}