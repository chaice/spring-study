package com.ccit.rest.sextant.request;

import com.ccit.bean.BoxCollectionBean;

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

    private String accelerateMode;

    private String accessUserName;

    private String accessPassword;

    // 专家模式可用入口
    private List<EntryReq> ssEntryList;

    //简易模式可用入口
    private List<EntryReq> l2tpEntryList;

    //集合列表
    private List<BoxCollectionBean> activeCollectionList;

    private Long boxAuth;

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

    public List<EntryReq> getSsEntryList() {
        return ssEntryList;
    }

    public void setSsEntryList(List<EntryReq> ssEntryList) {
        this.ssEntryList = ssEntryList;
    }

    public List<BoxCollectionBean> getActiveCollectionList() {
        return activeCollectionList;
    }

    public void setActiveCollectionList(List<BoxCollectionBean> activeCollectionList) {
        this.activeCollectionList = activeCollectionList;
    }

    public List<EntryReq> getL2tpEntryList() {
        return l2tpEntryList;
    }

    public void setL2tpEntryList(List<EntryReq> l2tpEntryList) {
        this.l2tpEntryList = l2tpEntryList;
    }

    public String getAccelerateMode() {
        return accelerateMode;
    }

    public void setAccelerateMode(String accelerateMode) {
        this.accelerateMode = accelerateMode;
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

    public Long getBoxAuth() {
        return boxAuth;
    }

    public void setBoxAuth(Long boxAuth) {
        this.boxAuth = boxAuth;
    }
}