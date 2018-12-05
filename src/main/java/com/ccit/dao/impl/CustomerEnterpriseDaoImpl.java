package com.ccit.dao.impl;

import com.ccit.entity.CustomerEnterpriseEntity;
import com.ccit.util.StringUtils;
import com.ccit.dao.CustomerEnterpriseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerEnterpriseDaoImpl extends BaseDaoImpl<CustomerEnterpriseEntity, Long> implements CustomerEnterpriseDao {


    public List<CustomerEnterpriseEntity> searchCustomerEnterprise(String name, String sextantName, String sort) {
        StringBuilder select = new StringBuilder();
        select.append(" FROM CustomerEnterpriseEntity WHERE removed=false");

        if (StringUtils.isNotBlank(name)) {
            select.append(" AND name LIKE :name");
        }
        if (StringUtils.isNotBlank(sextantName)) {
            select.append(" AND sextantName LIKE :sextantName");
        }

        Query query = currentSession().createQuery(select.toString());

        if (StringUtils.isNotBlank(name)) {
            query.setString("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(sextantName)) {
            query.setString("sextantName", "%" + sextantName + "%");
        }

        return query.list();
    }

    public CustomerEnterpriseEntity getByName(String name) {
        String hql = "FROM CustomerEnterpriseEntity WHERE name=:name";
        Query query = currentSession().createQuery(hql).setString("name", name);
        return (CustomerEnterpriseEntity) query.uniqueResult();
    }


    public CustomerEnterpriseEntity getBySextantName(String sextantName) {
        String hql = "FROM CustomerEnterpriseEntity WHERE sextantName=:sextantName";
        Query query = currentSession().createQuery(hql).setString("sextantName", sextantName);
        return (CustomerEnterpriseEntity) query.uniqueResult();
    }
}
