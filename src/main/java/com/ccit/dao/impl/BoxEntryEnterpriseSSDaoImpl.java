package com.ccit.dao.impl;

import com.ccit.entity.BoxEntryShadowsocksEntity;
import com.ccit.dao.BoxEntryEnterpriseSSDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoxEntryEnterpriseSSDaoImpl extends BaseDaoImpl<BoxEntryShadowsocksEntity, String> implements BoxEntryEnterpriseSSDao {

    public int countByBoxId(long boxId) {
        String hql = "SELECT count(id) FROM BoxEntryShadowsocksEntity WHERE boxId=:boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public List<BoxEntryShadowsocksEntity> listByBoxId(long boxId) {
        String hql = "FROM BoxEntryShadowsocksEntity WHERE boxId=:boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        return query.list();
    }


    public int deleteByBoxId(long boxId) {
        String hql = "DELETE FROM BoxEntryShadowsocksEntity WHERE boxId=:boxId";
        return currentSession().createQuery(hql).setLong("boxId", boxId).executeUpdate();
    }

    public int deleteByEntryId(long entryId){
        String hql = "DELETE FROM BoxEntryShadowsocksEntity WHERE entryId=:entryId";
        return currentSession().createQuery(hql).setLong("entryId", entryId).executeUpdate();
    }
}