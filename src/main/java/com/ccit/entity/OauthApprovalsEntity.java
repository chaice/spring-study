package com.ccit.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * @author Lance
 * @date 2017-16-05 11:16
 */
//@Entity
//@Table(name = "oauth_approvals", schema = "boss", catalog = "")
public class OauthApprovalsEntity {
    private String userId;
    private String clientId;
    private String scope;
    private String status;
    private Timestamp expiresAt;
    private Timestamp lastModifiedAt;

    @Basic
    @Column(name = "userId", nullable = true, length = 256)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "clientId", nullable = true, length = 256)
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "scope", nullable = true, length = 256)
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 256)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "expiresAt", nullable = true)
    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Basic
    @Column(name = "lastModifiedAt", nullable = true)
    public Timestamp getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Timestamp lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }


}
