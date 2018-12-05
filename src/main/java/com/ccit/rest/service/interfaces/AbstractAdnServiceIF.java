package com.ccit.rest.service.interfaces;

import com.ccit.util.StringUtils;
import com.google.gson.Gson;
import com.ccit.rest.CommonRequest;
import com.ccit.rest.FailureBody;
import com.ccit.rest.RestClient;
import com.ccit.rest.RestReturn;
import com.ccit.rest.service.response.AbstractAdnServiceRes;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public abstract class AbstractAdnServiceIF {

    @Autowired
    private Gson gson;

    protected String getPrefix() {
        return "";
    }

    protected <T extends AbstractAdnServiceRes> T get(String uri, Object body, Class<? extends AbstractAdnServiceRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), uri, null, ContentType.APPLICATION_JSON);

        // 发送get请求
        RestReturn restReturn = RestClient.get(request, null, null);
        return getInstance(restReturn, classOfT);
    }

    protected <T extends AbstractAdnServiceRes> T post(String uri, Object body, Class<? extends AbstractAdnServiceRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), uri, gson.toJson(body), ContentType.APPLICATION_JSON);

        // 发送put请求
        RestReturn restReturn = RestClient.post(request, null, null);
        return getInstance(restReturn, classOfT);
    }

    protected <T extends AbstractAdnServiceRes> T put(String uri, Object body, Class<? extends AbstractAdnServiceRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), uri, gson.toJson(body), ContentType.APPLICATION_JSON);

        // 发送put请求
        RestReturn restReturn = RestClient.put(request, null, null);
        return getInstance(restReturn, classOfT);
    }

    protected <T extends AbstractAdnServiceRes> T patch(String uri, Object body, Class<? extends AbstractAdnServiceRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), uri, gson.toJson(body), ContentType.APPLICATION_JSON);

        // 发送patch请求
        RestReturn restReturn = RestClient.patch(request, null, 60000);
        return getInstance(restReturn, classOfT);
    }

    protected <T extends AbstractAdnServiceRes> T delete(String uri, Class<? extends AbstractAdnServiceRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), uri, null, ContentType.APPLICATION_JSON);

        // 发送delete请求
        RestReturn restReturn = RestClient.delete(request, null, null);
        return getInstance(restReturn, classOfT);
    }

    private <T extends AbstractAdnServiceRes> T getInstance(RestReturn restReturn, Class<? extends AbstractAdnServiceRes> classOfT) {
        T instance = null;

        try {
            HttpStatus httpStatus = HttpStatus.valueOf(restReturn.getStatusCode());
            switch (httpStatus) {
                case OK:
                    instance = (StringUtils.isNotBlank(restReturn.getBody()))
                            ? (T) gson.fromJson(restReturn.getBody(), classOfT)
                            : (T) classOfT.newInstance();
                    instance.setSuccess(true);
                    break;
                case NOT_FOUND:
                case BAD_GATEWAY:
                default:
                    instance = (T) classOfT.newInstance();
                    instance.setSuccess(false);
                    instance.setErrorBody(gson.fromJson(restReturn.getBody(), FailureBody.class));
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return instance;
    }
}