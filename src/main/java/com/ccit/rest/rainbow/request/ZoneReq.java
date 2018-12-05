package com.ccit.rest.rainbow.request;

import com.ccit.entity.AdnZoneEntity;

public class ZoneReq {

    private long id;

    private String zoneName;

    private Double longitude;

    private Double latitude;

    public ZoneReq(AdnZoneEntity entity) {
            this.id = entity.getId();
            this.zoneName = entity.getZoneName();
            this.latitude = entity.getLatitude();
            this.longitude = entity.getLongitude();
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
}
