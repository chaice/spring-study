package com.ccit.enums;

public enum BoxStatusEnum {

    // 存货
    STORE("101"),

    // 在线
    ONLINE("102"),

    // 离线
    OFFLINE("103");


    private String code;

    BoxStatusEnum(String code) {
        this.code = code;
    }


    public String getCode() {
        return this.code;
    }
}