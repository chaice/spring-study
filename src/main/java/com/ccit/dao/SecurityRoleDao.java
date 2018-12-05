package com.ccit.dao;

import com.ccit.entity.SecurityRoleEntity;

public interface SecurityRoleDao extends BaseDao<SecurityRoleEntity, Long> {

    SecurityRoleEntity getByName(String name);

}
