package com.ccit.entity;

import javax.persistence.*;

@Entity
@Table(name = "entry_group_ss", schema = "matrix", catalog = "")
public class EntryEnterpriseSSGroupEntity {

    private long id;

    private String name;

    private String description;

    private Long commonMasterId;

    private Long commonSlaveId;

    private Long specialMasterId;

    private Long specialSlaveId;

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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "common_master_id")
    public Long getCommonMasterId() {
        return commonMasterId;
    }

    public void setCommonMasterId(Long commonMasterId) {
        this.commonMasterId = commonMasterId;
    }

    @Basic
    @Column(name = "common_slave_id")
    public Long getCommonSlaveId() {
        return commonSlaveId;
    }

    public void setCommonSlaveId(Long commonSlaveId) {
        this.commonSlaveId = commonSlaveId;
    }

    @Basic
    @Column(name = "special_master_id")
    public Long getSpecialMasterId() {
        return specialMasterId;
    }

    public void setSpecialMasterId(Long specialMasterId) {
        this.specialMasterId = specialMasterId;
    }

    @Basic
    @Column(name = "special_slave_id")
    public Long getSpecialSlaveId() {
        return specialSlaveId;
    }

    public void setSpecialSlaveId(Long specialSlaveId) {
        this.specialSlaveId = specialSlaveId;
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
