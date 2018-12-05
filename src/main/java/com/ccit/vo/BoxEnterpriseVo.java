package com.ccit.vo;

import java.util.List;

public class BoxEnterpriseVo {

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

    // 服务器地址(域名或ip)
    private String serverAddr;

    // 服务器端口
    private Integer serverPort;

    private String wan1IP;

    private String wan1GW;

    private String lanIP;

    //盒子公网地址
    private String pubIP;

    private String lanNetmask;

    private Integer speedUp;

    private Integer speedDown;

    private String accessUserName;

    private String accessPassword;

    private CustomerEnterpriseVo customer;

    //正在使用的入口
    private EntryEnterpriseSSGroupVo usedEntry;

    //活动的入口列表
    private List<EntryEnterpriseSSGroupVo> advancedEntryList;

    private List<EntryEnterpriseL2TPVo> simpleEntryList;

    private List<AccelerationCollectionEnterpriseVo> advancedCollectionEntityList;

    private List<AccelerationCollectionEnterpriseVo> simpleCollectionEntityList;

    private Boolean enable;

    private OrderEnterpriseVo orderVo;

    private String enterpriseName;

    private String customerName;

    private long boxAuth;

    public BoxEnterpriseVo() {
    }

    public BoxEnterpriseVo(Long id, String sn, String alias) {
        this.id = id;
        this.sn = sn;
        this.alias = alias;
    }

    public BoxEnterpriseVo(Long id, String sn, String alias, Integer speedUp, Integer speedDown, Boolean enable) {
        this(id, sn, alias);
        this.speedUp = speedUp;
        this.speedDown = speedDown;
        this.enable = enable;
    }

    public BoxEnterpriseVo(Long id, String sn, String alias, Integer speedUp, Integer speedDown, Boolean enable, String accessUserName, String accessPassword) {
        this(id, sn, alias, speedUp, speedDown, enable);
        this.accessUserName = accessUserName;
        this.accessPassword = accessPassword;
    }

    public BoxEnterpriseVo(
            Long id,
            String manufacturer,
            String model,
            String sn,
            Integer wanCount,
            String hardwareVersion,
            String softwareVersion,
            String status,
            String wan1IP,
            String wan1GW,
            String lanIP,
            String lanNetmask,
            String serverAddr,
            Integer serverPort
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
        this.wan1IP = wan1IP;
        this.wan1GW = wan1GW;
        this.lanIP = lanIP;
        this.lanNetmask = lanNetmask;
        this.serverAddr = serverAddr;
        this.serverPort = serverPort;
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

    public CustomerEnterpriseVo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEnterpriseVo customer) {
        this.customer = customer;
    }

    public EntryEnterpriseSSGroupVo getUsedEntry() {
        return usedEntry;
    }

    public void setUsedEntry(EntryEnterpriseSSGroupVo usedEntry) {
        this.usedEntry = usedEntry;
    }

    public List<AccelerationCollectionEnterpriseVo> getAdvancedCollectionEntityList() {
        return advancedCollectionEntityList;
    }

    public void setAdvancedCollectionEntityList(List<AccelerationCollectionEnterpriseVo> advancedCollectionEntityList) {
        this.advancedCollectionEntityList = advancedCollectionEntityList;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
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

    public OrderEnterpriseVo getOrderVo() {
        return orderVo;
    }

    public void setOrderVo(OrderEnterpriseVo orderVo) {
        this.orderVo = orderVo;
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

    public List<EntryEnterpriseL2TPVo> getSimpleEntryList() {
        return simpleEntryList;
    }

    public void setSimpleEntryList(List<EntryEnterpriseL2TPVo> simpleEntryList) {
        this.simpleEntryList = simpleEntryList;
    }

    public List<EntryEnterpriseSSGroupVo> getAdvancedEntryList() {
        return advancedEntryList;
    }

    public void setAdvancedEntryList(List<EntryEnterpriseSSGroupVo> advancedEntryList) {
        this.advancedEntryList = advancedEntryList;
    }

    public List<AccelerationCollectionEnterpriseVo> getSimpleCollectionEntityList() {
        return simpleCollectionEntityList;
    }

    public void setSimpleCollectionEntityList(List<AccelerationCollectionEnterpriseVo> simpleCollectionEntityList) {
        this.simpleCollectionEntityList = simpleCollectionEntityList;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getBoxAuth() {
        return boxAuth;
    }

    public void setBoxAuth(long boxAuth) {
        this.boxAuth = boxAuth;
    }
}