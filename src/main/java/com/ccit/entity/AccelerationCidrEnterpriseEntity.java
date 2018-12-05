package com.ccit.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "acceleration_cidr_enterprise", schema = "matrix", catalog = "")
public class AccelerationCidrEnterpriseEntity {
    private long id;
    private Long ipsetId;
    private Long collectionId;
    private String cidr;
    private Timestamp createTime;
    private String country;

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
    @Column(name = "ipset_id")
    public Long getIpsetId() {
        return ipsetId;
    }

    public void setIpsetId(Long ipsetId) {
        this.ipsetId = ipsetId;
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
    @Column(name = "cidr")
    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
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
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
