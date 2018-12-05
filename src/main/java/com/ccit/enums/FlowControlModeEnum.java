package com.ccit.enums;

public enum FlowControlModeEnum {

    // Panabit
    PA("panabit"),

    // NetFlow
    NF("netflow");

    private String code;

    FlowControlModeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}