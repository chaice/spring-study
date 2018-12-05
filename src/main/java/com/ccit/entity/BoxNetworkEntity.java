package com.ccit.entity;

import com.ccit.enums.BoxStatusEnum;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "box_network", schema = "matrix", catalog = "")
public class BoxNetworkEntity {
    private long id;

    // 厂商
    private String manufacturer;

    // 型号
    private String model;

    private String serialNumber;

    private String alias;

    // wan口数
    private Integer wanCount;

    // 客户id
    private Long customerId;

    // 硬件版本
    private String hardwareVersion;

    // 软件版本
    private String softwareVersion;

    // 状态
    private String status;

    // wan1 ip
    private String wan1IP;

    // wan1 gateway
    private String wan1GW;

    private String wan2IP;

    // wan2 gateway
    private String wan2GW;

    private String lanIP;

    private String lanNetmask;

    // 最大上行速率（M bps）
    private Integer speedUp;

    // 最大下行速率（M bps）
    private Integer speedDown;

    // 使用中的加速入口id
    private Long entryId;

    private String serverAddr;

    private Integer serverPort;

    private Timestamp createTime;

    private Long createUid;

    private Timestamp lastTime;

    private Long lastUid;

    private Boolean enable;

    private Boolean removed;

    private Long orderId;

    private String accessUserName;

    private String accessPassword;

    public BoxNetworkEntity() {
    }

    public BoxNetworkEntity(
            Long createUid,
            String manufacturer,
            String model,
            String serialNumber,
            Integer wanCount, String hardwareVersion,
            String softwareVersion,
            String serverAddr,
            Integer serverPort
    ) {
        this.createUid = createUid;
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNumber = serialNumber;
        this.wanCount = wanCount;
        this.hardwareVersion = hardwareVersion;
        this.softwareVersion = softwareVersion;
        this.enable = Boolean.FALSE;
        this.status = BoxStatusEnum.STORE.getCode();
        this.serverAddr = serverAddr;
        this.serverPort = serverPort;
        this.entryId = 0L;
        this.removed = Boolean.FALSE;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    @Basic
    @Column(name = "serial_number")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    @Basic
    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    @Basic
    @Column(name = "wan_count")
    public Integer getWanCount() {
        return wanCount;
    }

    public void setWanCount(Integer wanCount) {
        this.wanCount = wanCount;
    }


    @Basic
    @Column(name = "customer_id")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "hardware_version")
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    @Basic
    @Column(name = "software_version")
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }


    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "wan1_ip")
    public String getWan1IP() {
        return wan1IP;
    }

    public void setWan1IP(String wan1IP) {
        this.wan1IP = wan1IP;
    }

    @Basic
    @Column(name = "wan1_gw")
    public String getWan1GW() {
        return wan1GW;
    }

    public void setWan1GW(String wan1GW) {
        this.wan1GW = wan1GW;
    }

    @Basic
    @Column(name = "lan_ip")
    public String getLanIP() {
        return lanIP;
    }

    public void setLanIP(String lanIP) {
        this.lanIP = lanIP;
    }

    @Basic
    @Column(name = "speed_up")
    public Integer getSpeedUp() {
        return speedUp;
    }

    public void setSpeedUp(Integer speedUp) {
        this.speedUp = speedUp;
    }

    @Basic
    @Column(name = "speed_down")
    public Integer getSpeedDown() {
        return speedDown;
    }

    public void setSpeedDown(Integer speedDown) {
        this.speedDown = speedDown;
    }

    @Basic
    @Column(name = "entry_id")
    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "create_uid")
    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    @Basic
    @Column(name = "last_time")
    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }

    @Basic
    @Column(name = "last_uid")
    public Long getLastUid() {
        return lastUid;
    }

    public void setLastUid(Long lastUid) {
        this.lastUid = lastUid;
    }


    @Basic
    @Column(name = "is_enable")
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "lan_netmask")
    public String getLanNetmask() {
        return lanNetmask;
    }

    public void setLanNetmask(String lanNetmask) {
        this.lanNetmask = lanNetmask;
    }

    @Basic
    @Column(name = "removed")
    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    @Basic
    @Column(name = "server_addr")
    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }


    @Basic
    @Column(name = "server_port")
    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    @Basic
    @Column(name = "order_id")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "access_user_name")
    public String getAccessUserName() {
        return accessUserName;
    }

    public void setAccessUserName(String accessUserName) {
        this.accessUserName = accessUserName;
    }

    @Basic
    @Column(name = "access_password")
    public String getAccessPassword() {
        return accessPassword;
    }

    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }

    @Basic
    @Column(name = "wan2_ip")
    public String getWan2IP() {
        return wan2IP;
    }

    public void setWan2IP(String wan2IP) {
        this.wan2IP = wan2IP;
    }

    @Basic
    @Column(name = "wan2_gw")
    public String getWan2GW() {
        return wan2GW;
    }

    public void setWan2GW(String wan2GW) {
        this.wan2GW = wan2GW;
    }
}
