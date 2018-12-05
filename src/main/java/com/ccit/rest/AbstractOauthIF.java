package com.ccit.rest;

import com.ccit.service.GlobalDictionaryService;
import com.ccit.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import java.util.Map;

public class AbstractOauthIF {

    private String cacheKey;

    protected final String oauth = "oauth";

    protected final String oauthUri = "oauth/token";

    protected final String oauthAccessToken = "oauth.access_token";

    protected final String oauthRefreshToken = "oauth.refresh_token";

    protected final String oauthExpires = "oauth.expires";

    private String oauthUsername;

    private String oauthPassword;

    private String oauthClientId;

    private String oauthClientSecret;

    private String prefix;

    @Autowired
    private CacheManager cacheManager;

    protected Integer timeout = 60000;

    @Autowired
    protected Gson gson;

    @Autowired
    protected GlobalDictionaryService dictionaryService;


    public String getAccessToken() {
        return cacheManager.getCache(cacheKey).get(oauthAccessToken, String.class);
    }

    protected void clearOauth() {
        cacheManager.getCache(cacheKey).clear();
    }


    protected void cacheOauth(String accessToken, String refreshToken, long expires) {
        cacheManager.getCache(cacheKey).put(oauthAccessToken, accessToken);
        cacheManager.getCache(cacheKey).put(oauthRefreshToken, refreshToken);
        cacheManager.getCache(cacheKey).put(oauthExpires, expires);
    }


    protected void generateToken() {
        clearOauth();
        StringBuilder builder = new StringBuilder();
        builder.append(oauthUri).append("?")
                .append("client_id=").append(oauthClientId).append("&")
                .append("client_secret=").append(oauthClientSecret).append("&")
                .append("grant_type=password").append("&")
                .append("username=").append(oauthUsername).append("&")
                .append("password=").append(oauthPassword);

        CommonRequest request = new CommonRequest(prefix, builder.toString(), "", ContentType.APPLICATION_JSON);
        RestReturn restReturn = RestClient.post(request, null, timeout);

        if (restReturn.getStatusCode() == HttpStatus.SC_OK) {
            Map<String, Object> oauthMap = gson.fromJson(restReturn.getBody(), new TypeToken<Map<String, Object>>() {
            }.getType());
            String access_token = (String) oauthMap.get("access_token");
            String refresh_token = (String) oauthMap.get("refresh_token");
            long expires = Math.round((double) oauthMap.get("expires_in"));
            cacheOauth(access_token, refresh_token, System.currentTimeMillis() + expires * 1000);
        }
    }


    protected void refreshToken() {
        String refresh_token = cacheManager.getCache(cacheKey).get(oauthRefreshToken, String.class);
        if (StringUtils.isBlank(refresh_token)) {
            generateToken();
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(oauthUri).append("?")
                .append("client_id=").append(oauthClientId).append("&")
                .append("client_secret=").append(oauthClientSecret).append("&")
                .append("grant_type=refresh_token").append("&")
                .append("refresh_token=").append(refresh_token);

        CommonRequest request = new CommonRequest(prefix, builder.toString(), "", ContentType.APPLICATION_JSON);
        RestReturn restReturn = RestClient.post(request, null, timeout);

        if (restReturn.getStatusCode() == HttpStatus.SC_OK) {
            Map<String, Object> oauthMap = gson.fromJson(restReturn.getBody(), new TypeToken<Map<String, Object>>() {
            }.getType());
            String access_token = (String) oauthMap.get("access_token");
            refresh_token = (String) oauthMap.get("refresh_token");
            long expires = Math.round((double) oauthMap.get("expires_in"));
            cacheOauth(access_token, refresh_token, System.currentTimeMillis() + expires * 1000);
        }else{
            generateToken();
        }
    }


    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }


    public void setOauthUsername(String oauthUsername) {
        this.oauthUsername = oauthUsername;
    }


    public void setOauthPassword(String oauthPassword) {
        this.oauthPassword = oauthPassword;
    }

    public void setOauthClientId(String oauthClientId) {
        this.oauthClientId = oauthClientId;
    }

    public void setOauthClientSecret(String oauthClientSecret) {
        this.oauthClientSecret = oauthClientSecret;
    }


    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}