package com.ccit.dao.impl;

import com.ccit.entity.BoxCollectionEntity;
import com.ccit.dao.BoxCollectionDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoxCollectionDaoImpl extends BaseDaoImpl<BoxCollectionEntity, Long> implements BoxCollectionDao {
    @Override
    public List<BoxCollectionEntity> listByBoxId(long boxId) {
        String hql = "FROM BoxCollectionEntity WHERE boxId=:boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        return query.list();
    }

    @Override
    public int deleteByBoxId(long boxId) {
        String hql = "DELETE FROM BoxCollectionEntity WHERE boxId=:boxId";
        return currentSession().createQuery(hql).setLong("boxId", boxId).executeUpdate();
    }

    @Override
    public int deleteByCollectionId(long collectionId) {
        String hql = "DELETE FROM BoxCollectionEntity WHERE collectionId=:collectionId";
        return currentSession().createQuery(hql).setLong("collectionId", collectionId).executeUpdate();
    }
}
