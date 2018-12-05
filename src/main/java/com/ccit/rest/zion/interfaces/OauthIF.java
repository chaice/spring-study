package com.ccit.rest.zion.interfaces;

import com.ccit.rest.AbstractOauthIF;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("zionOauthIF")
public class OauthIF extends AbstractOauthIF {

    private final String CACHE_KEY = "zion";

    public synchronized void reload() {
        new Thread(() -> {
            refreshToken();
        }).start();
    }

    @PostConstruct
    private void init() {
        this.setPrefix(dictionaryService.fromKey(AbstractZionIF.ZION_ADDR));
        this.setCacheKey(CACHE_KEY);
        this.setOauthUsername(dictionaryService.fromKey("zion.oauth.username"));
        this.setOauthPassword(dictionaryService.fromKey("zion.oauth.password"));
        this.setOauthClientId("matrix");
        this.setOauthClientSecret(dictionaryService.fromKey("zion.oauth.client_secret"));
    }
}