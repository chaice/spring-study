package com.ccit.rest.sextant.interfaces;

import com.ccit.rest.sextant.request.UserReq;
import com.ccit.rest.sextant.response.UserRes;
import org.springframework.stereotype.Component;

@Component("sextantUserIF")
public class UserIF extends AbstractSextantIF {

    private final String userUri = "user";


    public UserRes updateUser(long id, UserReq userReq) {
        return super.put(userUri + "/" + id, userReq, UserRes.class);
    }


    public UserRes deleteUser(long id) {
        return super.delete(userUri + "/" + id, UserRes.class);
    }

}