package com.ccit.dao.impl;

import com.ccit.entity.EntryEnterpriseSSEntity;
import com.ccit.dao.EntryEnterpriseSSDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryEnterpriseSSDaoImpl extends BaseDaoImpl<EntryEnterpriseSSEntity, Long> implements EntryEnterpriseSSDao {

    public List<EntryEnterpriseSSEntity> searchEntry() {
        String hql = "FROM EntryEnterpriseSSEntity";
        Query query = currentSession().createQuery(hql);
        return query.list();
    }


    public int countByIpPort(String ip, int port) {
        String hql = "SELECT count(id) FROM EntryEnterpriseSSEntity WHERE ip=:ip AND port=:port";
        Query query = currentSession().createQuery(hql);
        query.setString("ip", ip);
        query.setInteger("port", port);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public int countByName(String name) {
        String hql = "SELECT count(id) FROM EntryEnterpriseSSEntity WHERE name=:name";
        Query query = currentSession().createQuery(hql).setString("name", name);
        return Integer.parseInt(query.uniqueResult().toString());
    }
}