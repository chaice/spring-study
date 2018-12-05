package com.ccit.dao.impl;

import com.ccit.entity.BoxEntryL2tpEntity;
import com.ccit.dao.BoxEntryEnterpriseL2tpDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoxEntryEnterpriseL2tpDaoImpl extends BaseDaoImpl<BoxEntryL2tpEntity, Long> implements BoxEntryEnterpriseL2tpDao {

    public int countByBoxId(long boxId) {
        String hql = "SELECT count(id) FROM BoxEntryL2tpEntity WHERE boxId=:boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public List<BoxEntryL2tpEntity> listByBoxId(long boxId) {
        String hql = "FROM BoxEntryL2tpEntity WHERE boxId=:boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        return query.list();
    }


    public int deleteByBoxId(long boxId) {
        String hql = "DELETE FROM BoxEntryL2tpEntity WHERE boxId=:boxId";
        return currentSession().createQuery(hql).setLong("boxId", boxId).executeUpdate();
    }

    public int deleteByEntryId(long entryId){
        String hql = "DELETE FROM BoxEntryL2tpEntity WHERE entryId=:entryId";
        return currentSession().createQuery(hql).setLong("entryId", entryId).executeUpdate();
    }
}