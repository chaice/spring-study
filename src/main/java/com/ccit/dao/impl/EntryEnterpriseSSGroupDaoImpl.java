package com.ccit.dao.impl;

import com.ccit.entity.EntryEnterpriseSSGroupEntity;
import com.ccit.dao.EntryEnterpriseSSGroupDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryEnterpriseSSGroupDaoImpl extends BaseDaoImpl<EntryEnterpriseSSGroupEntity, Long> implements EntryEnterpriseSSGroupDao {

    public int countByEntryId(Long commonMasterId, Long commonSlaveId, Long specialMasterId, Long specialSlaveId) {
        String hql = "SELECT count(id) FROM EntryEnterpriseSSGroupEntity WHERE commonMasterId=:commonMasterId AND commonSlaveId=:commonSlaveId AND specialMasterId=:specialMasterId AND specialSlaveId=:specialSlaveId";
        Query query = currentSession().createQuery(hql);
        query.setLong("commonMasterId", commonMasterId);
        query.setLong("commonSlaveId", commonSlaveId);
        query.setLong("specialMasterId", specialMasterId);
        query.setLong("specialSlaveId", specialSlaveId);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    public int countByName(String name) {
        String hql = "SELECT count(id) FROM EntryEnterpriseSSGroupEntity WHERE name=:name";
        Query query = currentSession().createQuery(hql).setString("name", name);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public int deleteByEntryId(long entryId) {
        String hql = "DELETE FROM EntryEnterpriseSSGroupEntity WHERE commonMasterId=:entryId OR commonSlaveId=:entryId OR specialMasterId=:entryId OR specialSlaveId=:entryId";
        return currentSession().createQuery(hql).setLong("entryId", entryId).executeUpdate();
    }

    @Override
    public List<EntryEnterpriseSSGroupEntity> findByEntryId(long entryId) {
        String hql = "FROM EntryEnterpriseSSGroupEntity WHERE commonMasterId=:entryId OR commonSlaveId=:entryId OR specialMasterId=:entryId OR specialSlaveId=:entryId";
        Query query = currentSession().createQuery(hql);
        query.setLong("entryId", entryId);
        return query.list();
    }
}