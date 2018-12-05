package com.ccit.rest.sextant.interfaces;

import com.ccit.rest.sextant.response.BoxRes;
import com.ccit.rest.sextant.request.BoxReq;
import org.springframework.stereotype.Component;

@Component("sextantBoxIF")
public class BoxIF extends AbstractSextantIF {

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