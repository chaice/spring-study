package com.ccit.enums;

public enum AccelerateMode {

    //专家模式
    ADVANCED_MODE("advanced"),
    SIMPLE_MODE("simple");

    private String mode;

    AccelerateMode(String mode) { this.mode = mode; }

    public String getMode() { return mode; }

    public void setMode(String mode) { this.mode = mode; }
}
