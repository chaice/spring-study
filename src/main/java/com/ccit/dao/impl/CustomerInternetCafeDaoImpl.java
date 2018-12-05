package com.ccit.dao.impl;

import com.ccit.entity.CustomerInternetCafeEntity;
import com.ccit.util.StringUtils;
import com.ccit.dao.CustomerInternetCafeDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerInternetCafeDaoImpl extends BaseDaoImpl<CustomerInternetCafeEntity, Long> implements CustomerInternetCafeDao {


    public List<CustomerInternetCafeEntity> searchCustomerInternetCafe(String name, String zionName, String sort) {
        StringBuilder select = new StringBuilder();
        select.append(" FROM CustomerInternetCafeEntity WHERE removed=false");

        if (StringUtils.isNotBlank(name)) {
            select.append(" AND name LIKE :name");
        }
        if (StringUtils.isNotBlank(zionName)) {
            select.append(" AND zionName LIKE :zionName");
        }

        Query query = currentSession().createQuery(select.toString());

        if (StringUtils.isNotBlank(name)) {
            query.setString("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(zionName)) {
            query.setString("zionName", "%" + zionName + "%");
        }

        return query.list();
    }

    public CustomerInternetCafeEntity getByName(String name) {
        String hql = "FROM CustomerInternetCafeEntity WHERE name=:name";
        Query query = currentSession().createQuery(hql).setString("name", name);
        return (CustomerInternetCafeEntity) query.uniqueResult();
    }


    public CustomerInternetCafeEntity getByZionName(String zionName) {
        String hql = "FROM CustomerInternetCafeEntity WHERE zionName=:zionName";
        Query query = currentSession().createQuery(hql).setString("zionName", zionName);
        return (CustomerInternetCafeEntity) query.uniqueResult();
    }
}
