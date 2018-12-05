package com.ccit.enums;

public enum UserSortEnum {

    /**
     * 用户姓名升序
     */
    REAL_NAME_ASC("11"),

    /**
     * 用户姓名降序
     */
    REAL_NAME_DESC("12"),

    /**
     * 手机号升序
     */
    PHONE_ASC("13"),

    /**
     * 手机号降序
     */
    PHONE_DESC("14");


    private String code;


    UserSortEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
