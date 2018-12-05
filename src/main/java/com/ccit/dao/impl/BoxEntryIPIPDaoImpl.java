package com.ccit.dao.impl;

import com.ccit.entity.BoxEntryIPIPEntity;
import com.ccit.dao.BoxEntryIPIPDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoxEntryIPIPDaoImpl extends BaseDaoImpl<BoxEntryIPIPEntity, String> implements BoxEntryIPIPDao {

    public int countByBoxId(long boxId) {
        String hql = "SELECT count(id) FROM BoxEntryIPIPEntity WHERE boxId=:boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public List<BoxEntryIPIPEntity> listByBoxId(long boxId) {
        String hql = "FROM BoxEntryIPIPEntity WHERE boxId=:boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        return query.list();
    }


    public int deleteByBoxId(long boxId) {
        String hql = "DELETE FROM BoxEntryIPIPEntity WHERE boxId=:boxId";
        return currentSession().createQuery(hql).setLong("boxId", boxId).executeUpdate();
    }

    public int deleteByEntryId(long entryId){
        String hql = "DELETE FROM BoxEntryIPIPEntity WHERE entryId=:entryId";
        return currentSession().createQuery(hql).setLong("entryId", entryId).executeUpdate();
    }
}