package com.ccit.rest.zion.interfaces;

import com.ccit.rest.zion.response.BoxRes;
import com.ccit.rest.zion.request.BoxReq;
import org.springframework.stereotype.Component;

@Component("zionBoxIF")
public class BoxIF extends AbstractZionIF {

    private final String BOX_URI = "user/boxes";

    public BoxRes updateBox(long id, BoxReq boxReq) {
        return super.put(BOX_URI + "/" + id, boxReq, BoxRes.class);
    }

    public BoxRes removeBox(long id) {
        return super.delete(BOX_URI + "/" + id, BoxRes.class);
    }


    public BoxRes updateAccelerate(long id, BoxReq boxReq) {
        return super.patch(BOX_URI + "/" + id, boxReq, BoxRes.class);
    }
}