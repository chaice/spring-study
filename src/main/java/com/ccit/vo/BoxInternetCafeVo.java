package com.ccit.vo;

import java.util.List;

public class BoxInternetCafeVo {

    private Long id;

    //厂商
    private String manufacturer;

    //型号
    private String model;

    //序列号
    private String sn;

    private String alias;

    //wan口数
    private Integer wanCount;

    //硬件版本
    private String hardwareVersion;

    //软件版本
    private String softwareVersion;

    //状态
    private String status;

    private String wan1IP;

    private String wan1GW;

    private String lanIP;

    //盒子公网地址
    private String pubIP;

    private String lanNetmask;

    private String ipipBoxMaster;

    private String ipipEntryMaster;

    private String ipipBoxSlave;

    private String ipipEntrySlave;

    private Integer speedUp;

    private Integer speedDown;

    private CustomerInternetCafeVo customer;

    //正在使用的入口
    private EntryIPIPVo usedEntry;

    //活动的入口列表
    private List<EntryIPIPVo> activeEntryList;

    private Boolean enable;

    private String flowControlMode;

    public BoxInternetCafeVo() {
    }

    public BoxInternetCafeVo(Long id, String sn, String alias) {
        this.id = id;
        this.sn = sn;
        this.alias = alias;
    }

    public BoxInternetCafeVo(Long id, String sn, String alias, Integer speedUp, Integer speedDown, Boolean enable, String flowControlMode) {
        this(id, sn, alias);
        this.speedUp = speedUp;
        this.speedDown = speedDown;
        this.enable = enable;
        this.flowControlMode = flowControlMode;
    }

    public BoxInternetCafeVo(
            Long id,
            String manufacturer,
            String model,
            String sn,
            Integer wanCount,
            String hardwareVersion,
            String softwareVersion,
            String status,
            String lanIP,
            String lanNetmask,
            String ipipBoxMaster,
            String ipipEntryMaster,
            String ipipBoxSlave,
            String ipipEntrySlave,
            String flowControlMode
    ) {
        this();
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.sn = sn;
        this.wanCount = wanCount;
        this.hardwareVersion = hardwareVersion;
        this.softwareVersion = softwareVersion;
        this.status = status;
        this.lanIP = lanIP;
        this.lanNetmask = lanNetmask;
        this.ipipBoxMaster = ipipBoxMaster;
        this.ipipEntryMaster = ipipEntryMaster;
        this.ipipBoxSlave = ipipBoxSlave;
        this.ipipEntrySlave = ipipEntrySlave;
        this.flowControlMode = flowControlMode;
    }


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

    public CustomerInternetCafeVo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerInternetCafeVo customer) {
        this.customer = customer;
    }

    public EntryIPIPVo getUsedEntry() {
        return usedEntry;
    }

    public void setUsedEntry(EntryIPIPVo usedEntry) {
        this.usedEntry = usedEntry;
    }

    public List<EntryIPIPVo> getActiveEntryList() {
        return activeEntryList;
    }

    public void setActiveEntryList(List<EntryIPIPVo> activeEntryList) {
        this.activeEntryList = activeEntryList;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getFlowControlMode() {
        return flowControlMode;
    }

    public void setFlowControlMode(String flowControlMode) {
        this.flowControlMode = flowControlMode;
    }
}