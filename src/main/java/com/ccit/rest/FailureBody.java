package com.ccit.rest;

public class FailureBody {

    // 错误码
    private String errorCode;

    // 错误描述
    private String message;


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}