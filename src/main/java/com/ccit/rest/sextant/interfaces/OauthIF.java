package com.ccit.rest.sextant.interfaces;

import com.ccit.rest.AbstractOauthIF;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("sextantOauthIF")
public class OauthIF extends AbstractOauthIF {

    private final String CACHE_KEY = "sextant";

    public synchronized void reload() {
        new Thread(() -> {
            refreshToken();
        }).start();
    }

    @PostConstruct
    private void init() {
        this.setPrefix(dictionaryService.fromKey(AbstractSextantIF.SEXTANT_ADDR));
        this.setCacheKey(CACHE_KEY);
        this.setOauthUsername(dictionaryService.fromKey("sextant.oauth.username"));
        this.setOauthPassword(dictionaryService.fromKey("sextant.oauth.password"));
        this.setOauthClientId("matrix");
        this.setOauthClientSecret(dictionaryService.fromKey("sextant.oauth.client_secret"));
    }
}