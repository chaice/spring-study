package com.ccit.bean;


public class AccelerationCidrEnterpriseBean {

    private Long id;

    private Long ipsetId;

    private Long collectionId;

    private String cidr;

    private String transportProtocol;

    private int portSingle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public int getPortSingle() {
        return portSingle;
    }

    public void setPortSingle(int portSingle) {
        this.portSingle = portSingle;
    }

    public Long getIpsetId() {
        return ipsetId;
    }

    public void setIpsetId(Long ipsetId) {
        this.ipsetId = ipsetId;
    }
}
