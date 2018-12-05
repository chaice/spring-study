package com.ccit.rest.axis.interfaces;

import com.ccit.rest.axis.response.BoxRes;
import com.ccit.rest.axis.request.BoxReq;
import org.springframework.stereotype.Component;

@Component("axisBoxIF")
public class BoxIF extends AbstractAxisIF {

    private final String BOX_URI = "user/boxes";

    public BoxRes updateBox(long id, BoxReq boxReq) {
        return super.put(BOX_URI + "/" + id, boxReq, BoxRes.class);
    }

    public BoxRes removeBox(long id) {
        return super.delete(BOX_URI + "/" + id, BoxRes.class);
    }

    public BoxRes updateBusinessBox(long id, BoxReq boxReq) {
        return super.patch(BOX_URI + "/" + id, boxReq, BoxRes.class);
    }
}