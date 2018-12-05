package com.ccit.entity;

import javax.persistence.Basic;
import javax.persistence.Column;

/**
 * @author Lance
 * @date 2017-16-05 11:16
 */
//@Entity
//@Table(name = "oauth_code", schema = "boss", catalog = "")
public class OauthCodeEntity {
    private String code;
    private byte[] authentication;

    @Basic
    @Column(name = "code", nullable = true, length = 256)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "authentication", nullable = true)
    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

}
