package com.ccit.rest.rainbow.interfaces;

import com.ccit.rest.rainbow.request.PerformanceTrafficReq;
import com.ccit.rest.rainbow.response.PerformanceRes;
import org.springframework.stereotype.Component;

@Component("performanceIF")
public class PerformanceIF extends AbstractRainbowIF {

    private final String PERFORMANCE_URI = "performance";

    public PerformanceRes syncAdnTraffic(PerformanceTrafficReq performanceTrafficReq) {
        return super.put(PERFORMANCE_URI + "/" + performanceTrafficReq.getId(), performanceTrafficReq, PerformanceRes.class);
    }

}
