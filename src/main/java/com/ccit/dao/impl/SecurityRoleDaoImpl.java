package com.ccit.dao.impl;

import com.ccit.entity.SecurityRoleEntity;
import com.ccit.dao.SecurityRoleDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityRoleDaoImpl extends BaseDaoImpl<SecurityRoleEntity, Long> implements SecurityRoleDao {
    @Override
    public SecurityRoleEntity getByName(String roleName) {
        String hql = "FROM SecurityRoleEntity WHERE roleName=:roleName";
        Query query = currentSession().createQuery(hql).setString("roleName", roleName);
        return (SecurityRoleEntity) query.uniqueResult();
    }
}
