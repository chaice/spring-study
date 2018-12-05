package com.ccit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lance on 7/11/17.
 */
@Entity
@Table(name = "agency_enterprise", schema = "matrix", catalog = "")
public class AgencyEnterpriseEntity {
    private long id;
    private String name;
    private String account;
    private String code;
    private String password;
    private String role;
    private Long upstreamId;
    private long createUid;
    private Timestamp createTime;
    private Long lastUid;
    private Timestamp lastTime;
    private byte isEnable;
    private byte removed;

    @Id
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
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "upstreamId")
    public Long getUpstreamId() {
        return upstreamId;
    }

    public void setUpstreamId(Long upstreamId) {
        this.upstreamId = upstreamId;
    }

    @Basic
    @Column(name = "create_uid")
    public long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(long createUid) {
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
    public byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(byte isEnable) {
        this.isEnable = isEnable;
    }

    @Basic
    @Column(name = "removed")
    public byte getRemoved() {
        return removed;
    }

    public void setRemoved(byte removed) {
        this.removed = removed;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
