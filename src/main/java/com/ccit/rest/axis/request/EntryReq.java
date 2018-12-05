package com.ccit.rest.axis.request;

public class EntryReq {

    private Long entryId;

    private String entryName;

    private String masterIP;

    private String slaveIP;

    private String operators1;

    private String operators2;

    private Boolean needSync;

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
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

    public String getOperators1() {
        return operators1;
    }

    public void setOperators1(String operators1) {
        this.operators1 = operators1;
    }

    public String getOperators2() {
        return operators2;
    }

    public void setOperators2(String operators2) {
        this.operators2 = operators2;
    }

    public Boolean getNeedSync() {
        return needSync;
    }

    public void setNeedSync(Boolean needSync) {
        this.needSync = needSync;
    }
}