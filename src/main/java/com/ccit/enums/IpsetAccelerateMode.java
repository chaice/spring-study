package com.ccit.enums;

public enum IpsetAccelerateMode {

    COMMON("common"),

    SPECIAL("special");

    private String name;

    IpsetAccelerateMode(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
