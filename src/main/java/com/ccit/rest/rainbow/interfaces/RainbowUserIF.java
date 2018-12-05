package com.ccit.rest.rainbow.interfaces;

import com.ccit.rest.rainbow.request.RainbowUserReq;
import com.ccit.rest.rainbow.response.RainbowUserRes;
import org.springframework.stereotype.Component;

@Component("rainbowUserIF")
public class RainbowUserIF extends AbstractRainbowIF {

    private final String userUri = "user";

    public RainbowUserRes updateUser(long id, RainbowUserReq rainbowUserReq) {
        return super.put(userUri + "/" + id, rainbowUserReq, RainbowUserRes.class);
    }

    public RainbowUserRes deleteUser(long id) {
        return super.delete(userUri + "/" + id, RainbowUserRes.class);
    }

}