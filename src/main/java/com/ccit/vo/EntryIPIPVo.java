package com.ccit.vo;

public class EntryIPIPVo {

    private Long id;

    private String name;

    private String masterIP;

    private String slaveIP;

    public EntryIPIPVo() {
    }


    public EntryIPIPVo(Long id, String name, String masterIP, String slaveIP) {
        this();
        this.id = id;
        this.name = name;
        this.masterIP = masterIP;
        this.slaveIP = slaveIP;
    }


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
}