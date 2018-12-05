package com.ccit.entity;

import javax.persistence.Basic;
import javax.persistence.Column;

/**
 * @author Lance
 * @date 2017-16-05 11:16
 */
//@Entity
//@Table(name = "oauth_refresh_token", schema = "boss", catalog = "")
public class OauthRefreshTokenEntity {
    private String tokenId;
    private byte[] token;
    private byte[] authentication;

    @Basic
    @Column(name = "token_id", nullable = false, length = 256)
    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    @Basic
    @Column(name = "token", nullable = true)
    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
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
