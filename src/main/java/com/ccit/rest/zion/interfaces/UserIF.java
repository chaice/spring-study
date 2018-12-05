package com.ccit.rest.zion.interfaces;

import com.ccit.rest.zion.request.UserReq;
import com.ccit.rest.zion.response.UserRes;
import org.springframework.stereotype.Component;

@Component("zionUserIF")
public class UserIF extends AbstractZionIF {

    private final String userUri = "user";


    public UserRes updateUser(long id, UserReq userReq) {
        return super.put(userUri + "/" + id, userReq, UserRes.class);
    }


    public UserRes deleteUser(long id) {
        return super.delete(userUri + "/" + id, UserRes.class);
    }

}