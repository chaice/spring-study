package com.ccit.entity;

import javax.persistence.*;

@Entity
@Table(name = "entry_network", schema = "matrix", catalog = "")
public class EntryNetworkEntity {

    private long id;

    private String name;

    private String masterIP;

    private String slaveIP;

    private String operators1;

    private String operators2;

    private Boolean needSync;

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
    @Column(name = "master_ip")
    public String getMasterIP() {
        return masterIP;
    }

    public void setMasterIP(String masterIP) {
        this.masterIP = masterIP;
    }

    @Basic
    @Column(name = "slave_ip")
    public String getSlaveIP() {
        return slaveIP;
    }

    public void setSlaveIP(String slaveIP) {
        this.slaveIP = slaveIP;
    }

    @Basic
    @Column(name = "operators1")
    public String getOperators1() {
        return operators1;
    }

    public void setOperators1(String operators1) {
        this.operators1 = operators1;
    }

    @Basic
    @Column(name = "operators2")
    public String getOperators2() {
        return operators2;
    }

    public void setOperators2(String operators2) {
        this.operators2 = operators2;
    }

    @Basic
    @Column(name = "need_sync")
    public Boolean getNeedSync() {
        return needSync;
    }

    public void setNeedSync(Boolean needSync) {
        this.needSync = needSync;
    }
}
