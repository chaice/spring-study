package com.ccit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "acceleration_collection_enterprise", schema = "matrix", catalog = "")
public class AccelerationCollectionEnterpriseEntity {
    private long id;
    private String collectionName;
    private String collectionDescription;
    private Timestamp createTime;
    private String type;
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
    @Column(name = "collection_name")
    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @Basic
    @Column(name = "collection_description")
    public String getCollectionDescription() {
        return collectionDescription;
    }

    public void setCollectionDescription(String collectionDescription) {
        this.collectionDescription = collectionDescription;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
