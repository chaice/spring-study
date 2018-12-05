package com.ccit.bean;


public class AccelerationCollectionEnterpriseBean {
    private long id;
    private String collectionName;
    private String collectionDescription;
    private String type;
    private String accelerateMode;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccelerateMode() { return accelerateMode; }

    public void setAccelerateMode(String accelerateMode) { this.accelerateMode = accelerateMode; }
}
