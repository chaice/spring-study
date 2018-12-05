package com.ccit.task;

import com.ccit.rest.sextant.interfaces.OauthIF;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenTask {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    com.ccit.rest.axis.interfaces.OauthIF axisOauthIF;

    @Autowired
    OauthIF sextantOauthIF;

    @Autowired
    com.ccit.rest.zion.interfaces.OauthIF zionOauthIF;

    @Autowired
    com.ccit.rest.rainbow.interfaces.OauthIF rainbowOauthIF;

//    @Scheduled(fixedRate = 300000)
//    public void reloadAxisToken() {
//        try {
//            Thread.sleep(30000);
//            axisOauthIF.reload();
//        } catch (Exception e) {
//            logger.error("Axis Token Reload Error", e);
//        }
//    }
//
//    @Scheduled(fixedRate = 300000)
//    public void reloadSextantToken() {
//        try {
//            Thread.sleep(30000);
//            sextantOauthIF.reload();
//        } catch (Exception e) {
//            logger.error("Sextant Token Reload Error", e);
//        }
//    }
//
//    @Scheduled(fixedRate = 300000)
//    public void reloadZionToken() {
//        try {
//            Thread.sleep(30000);
//            zionOauthIF.reload();
//        } catch (Exception e) {
//            logger.error("Zion Token Reload Error", e);
//        }
//    }
//
//    @Scheduled(fixedRate = 300000)
//    public void reloadRainbowToken() {
//        try {
//            Thread.sleep(30000);
//            rainbowOauthIF.reload();
//        } catch (Exception e) {
//            logger.error("Rainbow Token Reload Error", e);
//        }
//    }

}
