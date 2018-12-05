package com.ccit.rest.sextant.request;

import com.ccit.bean.EntryEnterpriseSSGroupBean;

public class EntryGroupReq {

    private Long id;

    private String name;

    private String description;

    private Long commonMasterId;

    private Long commonSlaveId;

    private Long specialMasterId;

    private Long specialSlaveId;

    private Boolean needSync;

    public EntryGroupReq(){

    }

    public EntryGroupReq(EntryEnterpriseSSGroupBean enterpriseGroupSSBean){
        this.id = enterpriseGroupSSBean.getId();
        this.name = enterpriseGroupSSBean.getName();
        this.description = enterpriseGroupSSBean.getDescription();
        this.commonMasterId = enterpriseGroupSSBean.getCommonMasterId();
        this.commonSlaveId = enterpriseGroupSSBean.getCommonSlaveId();
        this.specialMasterId = enterpriseGroupSSBean.getSpecialMasterId();
        this.specialSlaveId = enterpriseGroupSSBean.getSpecialSlaveId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCommonMasterId() {
        return commonMasterId;
    }

    public void setCommonMasterId(Long commonMasterId) {
        this.commonMasterId = commonMasterId;
    }

    public Long getCommonSlaveId() {
        return commonSlaveId;
    }

    public void setCommonSlaveId(Long commonSlaveId) {
        this.commonSlaveId = commonSlaveId;
    }

    public Long getSpecialMasterId() {
        return specialMasterId;
    }

    public void setSpecialMasterId(Long specialMasterId) {
        this.specialMasterId = specialMasterId;
    }

    public Long getSpecialSlaveId() {
        return specialSlaveId;
    }

    public void setSpecialSlaveId(Long specialSlaveId) {
        this.specialSlaveId = specialSlaveId;
    }

    public Boolean getNeedSync() {
        return needSync;
    }

    public void setNeedSync(Boolean needSync) {
        this.needSync = needSync;
    }
}