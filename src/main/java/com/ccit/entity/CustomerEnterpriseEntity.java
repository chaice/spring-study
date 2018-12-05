package com.ccit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "customer_enterprise", schema = "matrix", catalog = "")
public class CustomerEnterpriseEntity {

    private long id;

    private String name;

    private String info;

    private String province;

    private String city;

    private String contact;

    private String contactPhone;

    private String sextantName;

    private String sextantPassword;

    private Long createUid;

    private Timestamp createTime;

    private Long lastUid;

    private Timestamp lastTime;

    private Boolean enable;

    private Boolean removed;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Basic
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "sextant_name")
    public String getSextantName() {
        return sextantName;
    }

    public void setSextantName(String zionName) {
        this.sextantName = zionName;
    }


    @Basic
    @Column(name = "sextant_password")
    public String getSextantPassword() {
        return sextantPassword;
    }

    public void setSextantPassword(String zionPassword) {
        this.sextantPassword = zionPassword;
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
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
    @Column(name = "last_time")
    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
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
    @Column(name = "removed")
    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "contact_phone")
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
