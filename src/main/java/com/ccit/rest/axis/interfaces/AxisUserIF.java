package com.ccit.rest.axis.interfaces;

import com.ccit.rest.axis.request.AxisUserReq;
import com.ccit.rest.axis.response.AxisUserRes;
import org.springframework.stereotype.Component;

@Component("axisUserIF")
public class AxisUserIF extends AbstractAxisIF {

    private final String userUri = "user";

    public AxisUserRes updateUser(long id, AxisUserReq axisUserReq) {
        return super.put(userUri + "/" + id, axisUserReq, AxisUserRes.class);
    }

    public AxisUserRes deleteUser(long id) {
        return super.delete(userUri + "/" + id, AxisUserRes.class);
    }

}