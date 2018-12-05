package com.ccit.entity;

import javax.persistence.*;

@Entity
@Table(name = "entry_ipip", schema = "matrix", catalog = "")
public class EntryIPIPEntity {

    private long id;

    private String name;

    private String masterIP;

    private String slaveIP;

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
}
