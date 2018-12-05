package com.ccit.bean;

public class EntryEnterpriseL2TPBean {

    private Long id;

    private String name;

    private String masterIP;

    private String slaveIP;

    private Boolean needSync;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMasterIP() {
        return masterIP;
    }

    public void setMasterIP(String masterIP) {
        this.masterIP = masterIP;
    }

    public String getSlaveIP() {
        return slaveIP;
    }

    public void setSlaveIP(String slaveIP) {
        this.slaveIP = slaveIP;
    }

    public Boolean getNeedSync() {
        return needSync;
    }

    public void setNeedSync(Boolean needSync) {
        this.needSync = needSync;
    }
}