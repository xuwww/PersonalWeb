package com.personalweb.demo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{


    QUESTION_NOT_FOUND(2001,"问题不存在"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"未登录"),
    SYS_ERROR(2004,"别骂了，别骂了，已经在修了"),
    TYPE_PARAM_WRONG(2005,"评论类型不存在"),
    COMMENT_NOT_FOUND(2006,"回复评论不存在"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    NOT_ALLOWED(2008,"没有权限"),
    NOTIFICATION_NOT_FOUND(2009,"消息不存在"),
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
