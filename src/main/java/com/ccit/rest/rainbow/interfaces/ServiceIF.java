package com.ccit.rest.rainbow.interfaces;

import com.ccit.rest.rainbow.request.Service3LayerReq;
import com.ccit.rest.rainbow.request.Service4LayerReq;
import com.ccit.rest.rainbow.request.ServiceHttpReq;
import com.ccit.rest.rainbow.response.ServiceRes;
import org.springframework.stereotype.Component;

@Component("serviceIF")
public class ServiceIF extends AbstractRainbowIF {

    private final String SERVICE_3LAYER_URI = "adn/L3/services";

    private final String SERVICE_4LAYER_URI = "adn/L4/services";

    private final String SERVICE_HTTP_URI = "adn/http/services";

    public ServiceRes updateService3Layer(long serviceId, Service3LayerReq service3LayerReq) {
        return super.put(SERVICE_3LAYER_URI + "/" + serviceId, service3LayerReq, ServiceRes.class);
    }

    public ServiceRes updateService4Layer(long serviceId, Service4LayerReq service4LayerReq) {
        return super.put(SERVICE_4LAYER_URI + "/" + serviceId, service4LayerReq, ServiceRes.class);
    }

    public ServiceRes updateServiceHttp(long serviceId, ServiceHttpReq serviceHttpReq) {
        return super.put(SERVICE_HTTP_URI + "/" + serviceId, serviceHttpReq, ServiceRes.class);
    }

    public ServiceRes removeService3Layer(long serviceId) {
        return super.delete(SERVICE_3LAYER_URI + "/" + serviceId, ServiceRes.class);
    }

    public ServiceRes removeService4Layer(long serviceId) {
        return super.delete(SERVICE_4LAYER_URI + "/" + serviceId, ServiceRes.class);
    }

    public ServiceRes removeServiceHttp(long serviceId) {
        return super.delete(SERVICE_HTTP_URI + "/" + serviceId, ServiceRes.class);
    }
}
