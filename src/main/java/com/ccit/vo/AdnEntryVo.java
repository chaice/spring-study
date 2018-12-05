package com.ccit.vo;

import com.ccit.entity.AdnEntryEntity;

public class AdnEntryVo {

    private long id;

    private String entryName;

    private String entryType;

    private String controlIp;

    private Integer controlPort;

    private String serverIp;

    private AdnZoneVo adnZoneVo;

    public AdnEntryVo() {

    }

    public AdnEntryVo(AdnEntryEntity adnEntryEntity) {
        this.id = adnEntryEntity.getId();
        this.entryName = adnEntryEntity.getEntryName();
        this.entryType = adnEntryEntity.getEntryType();
        this.controlIp = adnEntryEntity.getControlIp();
        this.controlPort = adnEntryEntity.getControlPort();
        this.serverIp = adnEntryEntity.getServiceIp();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getControlIp() {
        return controlIp;
    }

    public void setControlIp(String controlIp) {
        this.controlIp = controlIp;
    }

    public Integer getControlPort() {
        return controlPort;
    }

    public void setControlPort(Integer controlPort) {
        this.controlPort = controlPort;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public AdnZoneVo getAdnZoneVo() {
        return adnZoneVo;
    }

    public void setAdnZoneVo(AdnZoneVo adnZoneVo) {
        this.adnZoneVo = adnZoneVo;
    }
}
