package com.ccit.dao.impl;

import com.ccit.entity.EntryEnterpriseL2tpEntity;
import com.ccit.dao.EntryEnterpriseL2tpDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryEnterpriseL2tpDaoImpl extends BaseDaoImpl<EntryEnterpriseL2tpEntity, Long> implements EntryEnterpriseL2tpDao {

    public List<EntryEnterpriseL2tpEntity> searchEntry() {
        String hql = "FROM EntryEnterpriseL2tpEntity";
        Query query = currentSession().createQuery(hql);
        return query.list();
    }


    public int countByMasterIP(String masterIP) {
        String hql = "SELECT count(id) FROM EntryEnterpriseL2tpEntity WHERE masterIP=:masterIP";
        Query query = currentSession().createQuery(hql).setString("masterIP", masterIP);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public int countByName(String name) {
        String hql = "SELECT count(id) FROM EntryEnterpriseL2tpEntity WHERE name=:name";
        Query query = currentSession().createQuery(hql).setString("name", name);
        return Integer.parseInt(query.uniqueResult().toString());
    }
}