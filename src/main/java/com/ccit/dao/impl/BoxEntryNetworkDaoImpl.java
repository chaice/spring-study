package com.ccit.dao.impl;

import com.ccit.entity.BoxEntryNetworkEntity;
import com.ccit.dao.BoxEntryNetworkDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoxEntryNetworkDaoImpl extends BaseDaoImpl<BoxEntryNetworkEntity, String> implements BoxEntryNetworkDao {

    public int countByBoxId(long boxId) {
        String hql = "SELECT count(id) FROM BoxEntryNetworkEntity WHERE boxId=:boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public List<BoxEntryNetworkEntity> listByBoxId(long boxId) {
        String hql = "FROM BoxEntryNetworkEntity WHERE boxId=:boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        return query.list();
    }


    public int deleteByBoxId(long boxId) {
        String hql = "DELETE FROM BoxEntryNetworkEntity WHERE boxId=:boxId";
        return currentSession().createQuery(hql).setLong("boxId", boxId).executeUpdate();
    }

    public int deleteByEntryId(long entryId){
        String hql = "DELETE FROM BoxEntryNetworkEntity WHERE entryId=:entryId";
        return currentSession().createQuery(hql).setLong("entryId", entryId).executeUpdate();
    }
}