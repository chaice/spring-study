package com.ccit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "adn_entry", schema = "matrix", catalog = "")
public class AdnEntryEntity {

    private long id;

    private String entryName;

    private String controlIp;

    private Integer controlPort;

    private String serviceIp;

    private String entryType;

    private Long zoneId;

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
    @Column(name = "entry_name")
    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    @Basic
    @Column(name = "control_ip")
    public String getControlIp() {
        return controlIp;
    }

    public void setControlIp(String controlIp) {
        this.controlIp = controlIp;
    }

    @Basic
    @Column(name = "control_port")
    public Integer getControlPort() {
        return controlPort;
    }

    public void setControlPort(Integer controlPort) {
        this.controlPort = controlPort;
    }

    @Basic
    @Column(name = "service_ip")
    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    @Basic
    @Column(name = "entry_type")
    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    @Basic
    @Column(name = "zone_id")
    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }
}
