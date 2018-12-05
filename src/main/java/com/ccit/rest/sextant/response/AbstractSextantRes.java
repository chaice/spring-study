package com.ccit.rest.sextant.response;

import com.ccit.rest.FailureBody;

public abstract class AbstractSextantRes {

    // 请求是否成功
    private boolean success;

    // 错误返回
    private FailureBody errorBody;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public FailureBody getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(FailureBody errorBody) {
        this.errorBody = errorBody;
    }

}