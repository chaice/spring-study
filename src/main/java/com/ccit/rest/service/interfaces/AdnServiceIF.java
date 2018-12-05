package com.ccit.rest.service.interfaces;

import com.ccit.rest.service.request.AdnService3LayerReq;
import com.ccit.rest.service.request.AdnService4LayerReq;
import com.ccit.rest.service.request.AdnServiceHttpReq;
import com.ccit.rest.service.response.AdnServiceRes;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("adnServiceIF")
public class AdnServiceIF extends AbstractAdnServiceIF {

    public AdnServiceRes syncService3Layer(AdnService3LayerReq adnService3LayerReq) {
        String url = "http://" + adnService3LayerReq.getControlIp() + ":60001" + "/l3/services/" + adnService3LayerReq.getServiceId();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("serviceIp", adnService3LayerReq.getServiceIp());
        requestBody.put("sourceIp", adnService3LayerReq.getSourceIp());

        return super.put(url, requestBody, AdnServiceRes.class);
    }

    public AdnServiceRes removeService3Layer(AdnService3LayerReq adnService3LayerReq) {
        String url = "http://" + adnService3LayerReq.getControlIp() + ":60001" + "/l3/services/" + adnService3LayerReq.getServiceId();
        return super.delete(url, AdnServiceRes.class);
    }

    public AdnServiceRes syncService4Layer(AdnService4LayerReq adnService4LayerReq) {
        String url = "http://" + adnService4LayerReq.getControlIp() + ":60001" + "/l4/services/" + adnService4LayerReq.getServiceId();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("sourceIp", adnService4LayerReq.getSourceIp());
        requestBody.put("sourcePort", adnService4LayerReq.getSourcePort());
        requestBody.put("transportProtocol", adnService4LayerReq.getTransportProtocol());

        return super.put(url, requestBody, AdnServiceRes.class);
    }

    public AdnServiceRes removeService4Layer(AdnService4LayerReq adnService4LayerReq) {
        String url = "http://" + adnService4LayerReq.getControlIp() + ":60001" + "/l4/services/" + adnService4LayerReq.getServiceId();
        return super.delete(url, AdnServiceRes.class);
    }

    public AdnServiceRes addServiceHttp(AdnServiceHttpReq adnServiceHttpReq) {
        String url = "http://" + adnServiceHttpReq.getControlIp() + ":60001" + "/" + adnServiceHttpReq.getServiceType() + "/services/" + adnServiceHttpReq.getServiceId();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("sourcePort", adnServiceHttpReq.getSourcePort());
        requestBody.put("httpDomainList", adnServiceHttpReq.getHttpDomainList());

        return super.put(url, requestBody, AdnServiceRes.class);
    }

    public AdnServiceRes removeServiceHttp(AdnServiceHttpReq adnServiceHttpReq) {
        String url = "http://" + adnServiceHttpReq.getControlIp() + ":60001" + "/" + adnServiceHttpReq.getServiceType() + "/services/" + adnServiceHttpReq.getServiceId();
        return super.delete(url, AdnServiceRes.class);
    }
}
