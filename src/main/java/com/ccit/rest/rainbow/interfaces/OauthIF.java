package com.ccit.rest.rainbow.interfaces;

import com.ccit.rest.AbstractOauthIF;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("rainbowOauthIF")
public class OauthIF extends AbstractOauthIF {

    private final String CACHE_KEY = "rainbow";

    public synchronized void reload() {
        new Thread(() -> {
            refreshToken();
        }).start();
    }

    @PostConstruct
    private void init() {
        this.setPrefix(dictionaryService.fromKey(AbstractRainbowIF.RAINBOW_ADDR));
        this.setCacheKey(CACHE_KEY);
        this.setOauthUsername(dictionaryService.fromKey("rainbow.oauth.username"));
        this.setOauthPassword(dictionaryService.fromKey("rainbow.oauth.password"));
        this.setOauthClientId("matrix");
        this.setOauthClientSecret(dictionaryService.fromKey("rainbow.oauth.client_secret"));
    }
}