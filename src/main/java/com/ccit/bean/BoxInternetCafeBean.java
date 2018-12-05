package com.ccit.bean;

import java.util.List;

public class BoxInternetCafeBean {

    private Long id;

    private String manufacturer;

    private String model;

    private String sn;

    private String alias;

    private Integer wanCount;

    private Long customerId;

    private Long entryId;

    private String hardwareVersion;

    private String softwareVersion;

    private String status;

    private String wan1IP;

    private String wan1GW;

    private String lanIP;

    private String lanNetmask;

    private String ipipBoxMaster;

    private String ipipEntryMaster;

    private String ipipBoxSlave;

    private String ipipEntrySlave;

    private String pubIP;


    // 最大上行速率（M bps）
    private Integer speedUp;

    // 最大下行速率（M bps）
    private Integer speedDown;

    // 服务器地址(域名或ip)
    private String serverAddr;

    // 服务器端口
    private Integer serverPort;

    private List<EntryIPIPBean> activeEntryList;

    private Long createUid;

    private Long lastUid;

    private Boolean enable;

    private String flowControlMode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getWanCount() {
        return wanCount;
    }

    public void setWanCount(Integer wanCount) {
        this.wanCount = wanCount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWan1IP() {
        return wan1IP;
    }

    public void setWan1IP(String wan1IP) {
        this.wan1IP = wan1IP;
    }

    public String getWan1GW() {
        return wan1GW;
    }

    public void setWan1GW(String wan1GW) {
        this.wan1GW = wan1GW;
    }

    public String getLanIP() {
        return lanIP;
    }

    public void setLanIP(String lanIP) {
        this.lanIP = lanIP;
    }

    public String getPubIP() {
        return pubIP;
    }

    public void setPubIP(String pubIP) {
        this.pubIP = pubIP;
    }

    public Integer getSpeedUp() {
        return speedUp;
    }

    public void setSpeedUp(Integer speedUp) {
        this.speedUp = speedUp;
    }

    public Integer getSpeedDown() {
        return speedDown;
    }

    public void setSpeedDown(Integer speedDown) {
        this.speedDown = speedDown;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public List<EntryIPIPBean> getActiveEntryList() {
        return activeEntryList;
    }

    public void setActiveEntryList(List<EntryIPIPBean> activeEntryList) {
        this.activeEntryList = activeEntryList;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Long getLastUid() {
        return lastUid;
    }

    public void setLastUid(Long lastUid) {
        this.lastUid = lastUid;
    }

    public String getLanNetmask() {
        return lanNetmask;
    }

    public void setLanNetmask(String lanNetmask) {
        this.lanNetmask = lanNetmask;
    }

    public String getIpipBoxMaster() {
        return ipipBoxMaster;
    }

    public void setIpipBoxMaster(String ipipBoxMaster) {
        this.ipipBoxMaster = ipipBoxMaster;
    }

    public String getIpipEntryMaster() {
        return ipipEntryMaster;
    }

    public void setIpipEntryMaster(String ipipEntryMaster) {
        this.ipipEntryMaster = ipipEntryMaster;
    }

    public String getIpipBoxSlave() {
        return ipipBoxSlave;
    }

    public void setIpipBoxSlave(String ipipBoxSlave) {
        this.ipipBoxSlave = ipipBoxSlave;
    }

    public String getIpipEntrySlave() {
        return ipipEntrySlave;
    }

    public void setIpipEntrySlave(String ipipEntrySlave) {
        this.ipipEntrySlave = ipipEntrySlave;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getFlowControlMode() {
        return flowControlMode;
    }

    public void setFlowControlMode(String flowControlMode) {
        this.flowControlMode = flowControlMode;
    }
}