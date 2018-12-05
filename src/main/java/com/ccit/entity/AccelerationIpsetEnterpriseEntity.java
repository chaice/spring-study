package com.ccit.entity;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "acceleration_ipset_enterprise", schema = "matrix", catalog = "")
public class AccelerationIpsetEnterpriseEntity {
    private long id;
    private Long collectionId;
    private String ipsetName;
    private String transportProtocol;
    private String portTotal;
    private Timestamp createTime;
    private long sequence;
    private Boolean enable;
    private String accelerateMode;

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
    @Column(name = "collection_id")
    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    @Basic
    @Column(name = "transport_protocol")
    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    @Basic
    @Column(name = "port_total")
    public String getPortTotal() {
        return portTotal;
    }

    public void setPortTotal(String portTotal) {
        this.portTotal = portTotal;
    }

    @Basic
    @Column(name = "ipset_name")
    public String getIpsetName() {
        return ipsetName;
    }

    public void setIpsetName(String ipsetName) {
        this.ipsetName = ipsetName;
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
    @Column(name = "sequence")
    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
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
    @Column(name = "accelerate_mode")
    public String getAccelerateMode() {
        return accelerateMode;
    }

    public void setAccelerateMode(String accelerateMode) {
        this.accelerateMode = accelerateMode;
    }
}
