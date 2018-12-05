package com.ccit.bean;


public class AccelerationIpsetEnterpriseBean {

    private Long id;

    private Long collectionId;

    private String ipsetName;

    private String transportProtocol;

    private String portTotal;

    private long sequence;

    private Boolean enable;

    private String accelerateMode;

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

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getPortTotal() {
        return portTotal;
    }

    public void setPortTotal(String portTotal) {
        this.portTotal = portTotal;
    }

    public String getIpsetName() {
        return ipsetName;
    }

    public void setIpsetName(String ipsetName) {
        this.ipsetName = ipsetName;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getAccelerateMode() {
        return accelerateMode;
    }

    public void setAccelerateMode(String accelerateMode) {
        this.accelerateMode = accelerateMode;
    }
}
