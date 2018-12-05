package com.ccit.rest.rainbow.interfaces;

import com.ccit.service.GlobalDictionaryService;
import com.ccit.util.StringUtils;
import com.google.gson.Gson;
import com.ccit.rest.CommonRequest;
import com.ccit.rest.FailureBody;
import com.ccit.rest.RestClient;
import com.ccit.rest.RestReturn;
import com.ccit.rest.rainbow.response.AbstractRainbowRes;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

public abstract class AbstractRainbowIF {

    // axis访问地址
    public static final String RAINBOW_ADDR = "rainbow.addr";

    @Autowired
    private GlobalDictionaryService dictionaryService;

    @Autowired
    private Gson gson;

    @Autowired
    @Qualifier("rainbowOauthIF")
    private OauthIF oauthIF;

    protected String getPrefix() {
        return dictionaryService.fromKey(RAINBOW_ADDR);
    }

    protected <T extends AbstractRainbowRes> T get(String uri, Object body, Class<? extends AbstractRainbowRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), appendToken(uri), null, ContentType.APPLICATION_JSON);

        // 发送get请求
        RestReturn restReturn = RestClient.get(request, null, null);
        return getInstance(restReturn, classOfT);
    }


    protected <T extends AbstractRainbowRes> T post(String uri, Object body, Class<? extends AbstractRainbowRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), appendToken(uri), gson.toJson(body), ContentType.APPLICATION_JSON);

        // 发送put请求
        RestReturn restReturn = RestClient.post(request, null, null);
        return getInstance(restReturn, classOfT);
    }

    protected <T extends AbstractRainbowRes> T put(String uri, Object body, Class<? extends AbstractRainbowRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), appendToken(uri), gson.toJson(body), ContentType.APPLICATION_JSON);

        // 发送put请求
        RestReturn restReturn = RestClient.put(request, null, null);
        return getInstance(restReturn, classOfT);
    }


    protected <T extends AbstractRainbowRes> T patch(String uri, Object body, Class<? extends AbstractRainbowRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), appendToken(uri), gson.toJson(body), ContentType.APPLICATION_JSON);

        // 发送patch请求
        RestReturn restReturn = RestClient.patch(request, null, 60000);
        return getInstance(restReturn, classOfT);
    }


    protected <T extends AbstractRainbowRes> T delete(String uri, Class<? extends AbstractRainbowRes> classOfT) {
        CommonRequest request = new CommonRequest(getPrefix(), appendToken(uri), null, ContentType.APPLICATION_JSON);

        // 发送delete请求
        RestReturn restReturn = RestClient.delete(request, null, null);
        return getInstance(restReturn, classOfT);
    }


    private String appendToken(String uri) {
        String accessToken = oauthIF.getAccessToken();
        if (uri.contains("?")) {
            return uri + "&access_token=" + accessToken;
        } else {
            return uri + "?access_token=" + accessToken;
        }
    }


    private <T extends AbstractRainbowRes> T getInstance(RestReturn restReturn, Class<? extends AbstractRainbowRes> classOfT) {
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
                case UNAUTHORIZED:
                    instance = (T) classOfT.newInstance();
                    instance.setSuccess(false);
                    instance.setErrorBody(new FailureBody() {{
                        setErrorCode(String.valueOf(httpStatus.value()));
                        setMessage(httpStatus.getReasonPhrase());
                    }});
                    oauthIF.reload();
                    break;
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