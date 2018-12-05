package com.ccit.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Lance
 * @date 2017-16-05 11:16
 */
@Entity
@Table(name = "security_role", schema = "matrix", catalog = "")
public class SecurityRoleEntity {
    private long id;
    private String roleName;
    private String roleDescription;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role_name", nullable = true, length = 32)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "role_description", nullable = true, length = 32)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecurityRoleEntity that = (SecurityRoleEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
