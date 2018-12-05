package com.ccit.rest.service.request;

import java.util.LinkedList;
import java.util.List;

public class AdnServiceHttpReq {

    private long serviceId;

    private String controlIp;

    private String serviceType;

    private Integer sourcePort;

    private List<AdnServiceHttpDomain> httpDomainList;

    public AdnServiceHttpReq() {
    }

    public AdnServiceHttpReq(String controlIp,String serviceType, Integer sourcePort) {
        this.controlIp = controlIp;
        this.serviceType = serviceType;
        this.sourcePort = sourcePort;
        this.httpDomainList = new LinkedList<>();
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getControlIp() {
        return controlIp;
    }

    public void setControlIp(String controlIp) {
        this.controlIp = controlIp;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(Integer sourcePort) {
        this.sourcePort = sourcePort;
    }

    public List<AdnServiceHttpDomain> getHttpDomainList() {
        return httpDomainList;
    }

    public void setHttpDomainList(List<AdnServiceHttpDomain> httpDomainList) {
        this.httpDomainList = httpDomainList;
    }
}
