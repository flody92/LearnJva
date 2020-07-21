package com.learn.test.learn.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"问题不存在，请重试！"),
    TARGET_PARAM_NOT_FOUND(2002,"选中内容已不存在！"),
    NO_LOGIN(2003,"未登录，无法评论"),
    SYSTEM_ERROR(2004,"系统错误"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！"),
    COMMENT_NOT_FOUND(2006,"评论类型错误或不存在！"),
    CONTENT_IS_EMPTY(2007,"评论内容为空！");




    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
