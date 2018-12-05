package com.ccit.bean;

import java.util.List;

public class BoxEnterpriseBean {

    private Long id;

    private String manufacturer;

    private String model;

    private String sn;

    private String alias;

    private Integer wanCount;

    private Long orderId;

    private Long customerId;

    private Long entryId;

    private String hardwareVersion;

    private String softwareVersion;

    private String status;

    private String wan1IP;

    private String wan1GW;

    private String lanIP;

    private String lanNetmask;

    private String pubIP;


    // 最大上行速率（M bps）
    private Integer speedUp;

    // 最大下行速率（M bps）
    private Integer speedDown;

    // 服务器地址(域名或ip)
    private String serverAddr;

    // 服务器端口
    private Integer serverPort;

    private String accessUserName;

    private String accessPassword;

    private String accelerateMode;

    private List<EntryEnterpriseSSGroupBean> advancedEntryList;

    private List<EntryEnterpriseL2TPBean> simpleEntryList;

    private List<BoxCollectionBean> activeCollectionList;

    private Long createUid;

    private Long lastUid;

    private Boolean enable;

    private Long boxAuth;

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


    public List<BoxCollectionBean> getActiveCollectionList() {
        return activeCollectionList;
    }

    public void setActiveCollectionList(List<BoxCollectionBean> activeCollectionList) {
        this.activeCollectionList = activeCollectionList;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAccessUserName() {
        return accessUserName;
    }

    public void setAccessUserName(String accessUserName) {
        this.accessUserName = accessUserName;
    }

    public String getAccessPassword() {
        return accessPassword;
    }

    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }

    public List<EntryEnterpriseSSGroupBean> getAdvancedEntryList() {
        return advancedEntryList;
    }

    public void setAdvancedEntryList(List<EntryEnterpriseSSGroupBean> advancedEntryList) {
        this.advancedEntryList = advancedEntryList;
    }

    public List<EntryEnterpriseL2TPBean> getSimpleEntryList() {
        return simpleEntryList;
    }

    public void setSimpleEntryList(List<EntryEnterpriseL2TPBean> simpleEntryList) {
        this.simpleEntryList = simpleEntryList;
    }

    public String getAccelerateMode() {
        return accelerateMode;
    }

    public void setAccelerateMode(String accelerateMode) {
        this.accelerateMode = accelerateMode;
    }

    public Long getBoxAuth() {
        return boxAuth;
    }

    public void setBoxAuth(Long boxAuth) {
        this.boxAuth = boxAuth;
    }
}