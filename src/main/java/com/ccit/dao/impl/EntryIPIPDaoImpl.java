package com.ccit.dao.impl;

import com.ccit.entity.EntryIPIPEntity;
import com.ccit.dao.EntryIPIPDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryIPIPDaoImpl extends BaseDaoImpl<EntryIPIPEntity, Long> implements EntryIPIPDao {

    public List<EntryIPIPEntity> searchEntry() {
        String hql = "FROM EntryIPIPEntity";
        Query query = currentSession().createQuery(hql);
        return query.list();
    }


    public int countByMasterIP(String masterIP) {
        String hql = "SELECT count(id) FROM EntryIPIPEntity WHERE masterIP=:masterIP";
        Query query = currentSession().createQuery(hql).setString("masterIP", masterIP);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public int countByName(String name) {
        String hql = "SELECT count(id) FROM EntryIPIPEntity WHERE name=:name";
        Query query = currentSession().createQuery(hql).setString("name", name);
        return Integer.parseInt(query.uniqueResult().toString());
    }
}