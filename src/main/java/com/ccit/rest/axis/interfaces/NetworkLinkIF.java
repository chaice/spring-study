package com.ccit.rest.axis.interfaces;

import com.ccit.rest.axis.request.NetworkLinkReq;
import com.ccit.rest.axis.response.NetworkLinkRes;
import org.springframework.stereotype.Component;

@Component("networkLinkIF")
public class NetworkLinkIF extends AbstractAxisIF {

    private final String NETWORK_LINK_URI = "user/business/links";

    public NetworkLinkRes updateBox(long id, NetworkLinkReq linkReq) {
        return super.put(NETWORK_LINK_URI + "/" + id, linkReq, NetworkLinkRes.class);
    }

    public NetworkLinkRes removeBox(long id) {
        return super.delete(NETWORK_LINK_URI + "/" + id, NetworkLinkRes.class);
    }
}