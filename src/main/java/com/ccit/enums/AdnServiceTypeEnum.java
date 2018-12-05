package com.ccit.enums;

public enum AdnServiceTypeEnum {

    //三层
    THREE_LAYER("L3"),
    //四层
    FOUR_LAYER("L4"),
    //七层
    HTTP("http");

    private String type;

    AdnServiceTypeEnum(String type) { this.type = type; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
