package com.ccit.vo;

import com.ccit.entity.AdnZoneEntity;

import java.util.List;

public class AdnZoneVo {

    private long id;

    private String zoneName;

    private Double longitude;

    private Double latitude;

    private List<AdnEntryVo> entryList;

    public AdnZoneVo() {
    }

    public AdnZoneVo(AdnZoneEntity adnZoneEntity) {
        this.id = adnZoneEntity.getId();
        this.zoneName = adnZoneEntity.getZoneName();
        this.latitude = adnZoneEntity.getLatitude();
        this.longitude = adnZoneEntity.getLongitude();
    }

    public AdnZoneVo(long id, String zoneName, Double longitude, Double latitude) {
        this.id = id;
        this.zoneName = zoneName;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<AdnEntryVo> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<AdnEntryVo> entryList) {
        this.entryList = entryList;
    }
}
