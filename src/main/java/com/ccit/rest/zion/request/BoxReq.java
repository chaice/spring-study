package com.ccit.rest.zion.request;

import java.util.List;

public class BoxReq {

    // 序列号
    private String sn;

    // 别名
    private String alias;

    // 硬件version
    private String hardwareVersion;

    // 软件version
    private String softwareVersion;

    // wan口数
    private Integer wanCount;

    // 上行速率(M bps)
    private Integer speedUp;

    // 下行速率(M bps)
    private Integer speedDown;

    // 服务器地址
    private String serverAddr;

    // 服务器端口
    private Integer serverPort;

    private String lanIP;

    private String lanNetmask;

    private String ipipBoxMaster;

    private String ipipEntryMaster;

    private String ipipBoxSlave;

    private String ipipEntrySlave;

    private String flowControlMode;

    // 入口列表
    private List<EntryReq> entryList;


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

    public Integer getWanCount() {
        return wanCount;
    }

    public void setWanCount(Integer wanCount) {
        this.wanCount = wanCount;
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

    public List<EntryReq> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<EntryReq> entryList) {
        this.entryList = entryList;
    }

    public String getLanIP() {
        return lanIP;
    }

    public void setLanIP(String lanIP) {
        this.lanIP = lanIP;
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

    public String getFlowControlMode() {
        return flowControlMode;
    }

    public void setFlowControlMode(String flowControlMode) {
        this.flowControlMode = flowControlMode;
    }
}