package com.ccit.dao.impl;

import com.ccit.entity.EntryNetworkEntity;
import com.ccit.dao.EntryNetworkDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryNetworkDaoImpl extends BaseDaoImpl<EntryNetworkEntity, Long> implements EntryNetworkDao {

    public List<EntryNetworkEntity> searchEntry() {
        String hql = "FROM EntryNetworkEntity";
        Query query = currentSession().createQuery(hql);
        return query.list();
    }


    public int countByMasterIPSlaveIP(String masterIP, String slaveIP) {
        String hql = "SELECT count(id) FROM EntryNetworkEntity WHERE masterIP=:masterIP AND slaveIP=:slaveIP";
        Query query = currentSession().createQuery(hql).setString("masterIP", masterIP).setString("slaveIP",slaveIP);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public int countByName(String name) {
        String hql = "SELECT count(id) FROM EntryNetworkEntity WHERE name=:name";
        Query query = currentSession().createQuery(hql).setString("name", name);
        return Integer.parseInt(query.uniqueResult().toString());
    }
}