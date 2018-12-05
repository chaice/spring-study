package com.ccit.rest.rainbow.interfaces;

import com.ccit.rest.rainbow.request.CustomerZoneReq;
import com.ccit.rest.rainbow.request.ZoneReq;
import com.ccit.rest.rainbow.response.CustomerZoneRes;
import com.ccit.rest.rainbow.response.ZoneRes;
import org.springframework.stereotype.Component;

@Component("rainbowZoneIF")
public class ZoneIF extends AbstractRainbowIF {

    private final String ZONE_URI = "zones";

    private final String CUSTOMER_ZONE_URI = "user";

    public ZoneRes updateZone(ZoneReq zoneReq) {
        return super.put(ZONE_URI + "/" +zoneReq.getId(), zoneReq, ZoneRes.class);
    }

    public ZoneRes removeZone(long zoneId) {
        return super.delete(ZONE_URI + "/" + zoneId, ZoneRes.class);
    }

    public CustomerZoneRes updateCustomerZone(CustomerZoneReq customerZoneReq) {
        return super.put(CUSTOMER_ZONE_URI + "/" + customerZoneReq.getCustomerId() + "/zones" , customerZoneReq, CustomerZoneRes.class);
    }

}