package com.ccit.enums;

public enum TransportProtocolEnum {

    ALL("all"),

    TCP("tcp"),

    UDP("udp");

    private String code;

    TransportProtocolEnum(String code) {
        this.code = code;
    }


    public String getCode() {
        return this.code;
    }
}