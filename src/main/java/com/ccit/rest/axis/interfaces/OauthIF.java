package com.ccit.rest.axis.interfaces;

import com.ccit.rest.AbstractOauthIF;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("axisOauthIF")
public class OauthIF extends AbstractOauthIF {

    private final String CACHE_KEY = "axis";

    public synchronized void reload() {
        new Thread(() -> {
            refreshToken();
        }).start();
    }

    @PostConstruct
    private void init() {
        this.setPrefix(dictionaryService.fromKey(AbstractAxisIF.AXIS_ADDR));
        this.setCacheKey(CACHE_KEY);
        this.setOauthUsername(dictionaryService.fromKey("axis.oauth.username"));
        this.setOauthPassword(dictionaryService.fromKey("axis.oauth.password"));
        this.setOauthClientId("matrix");
        this.setOauthClientSecret(dictionaryService.fromKey("axis.oauth.client_secret"));
    }
}