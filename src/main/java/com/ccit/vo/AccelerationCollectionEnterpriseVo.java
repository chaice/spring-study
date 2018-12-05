package com.ccit.vo;

import java.util.List;

public class AccelerationCollectionEnterpriseVo {

    private long id;

    private String collectionName;

    private String collectionDescription;

    private String createTime;

    private String type;

    private String accelerateMode;

    private List<AccelerationIpsetEnterpriseVo> ipsetList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionDescription() {
        return collectionDescription;
    }

    public void setCollectionDescription(String collectionDescription) {
        this.collectionDescription = collectionDescription;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<AccelerationIpsetEnterpriseVo> getIpsetList() {
        return ipsetList;
    }

    public void setIpsetList(List<AccelerationIpsetEnterpriseVo> ipsetList) {
        this.ipsetList = ipsetList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccelerateMode() { return accelerateMode; }

    public void setAccelerateMode(String accelerateMode) { this.accelerateMode = accelerateMode; }
}
