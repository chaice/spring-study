package com.ccit.rest.service.request;

public class AdnServiceHttpDomain {

    private String secondLevelDomain;

    private String sourceIp;

    public AdnServiceHttpDomain() {
    }

    public AdnServiceHttpDomain(String sourceIp, String secondLevelDomain) {
        this.sourceIp = sourceIp;
        this.secondLevelDomain = secondLevelDomain;
    }

    public String getSecondLevelDomain() {
        return secondLevelDomain;
    }

    public void setSecondLevelDomain(String secondLevelDomain) {
        this.secondLevelDomain = secondLevelDomain;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }
}
