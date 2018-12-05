package com.ccit.dao.impl;

import com.ccit.entity.AdnEntryEntity;
import com.ccit.dao.AdnEntryDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdnEntryDaoImpl extends BaseDaoImpl<AdnEntryEntity, Long> implements AdnEntryDao {

    @Override
    public AdnEntryEntity findByEntryName(String entryName) {
        String hql = "FROM AdnEntryEntity WHERE entryName = :entryName";
        Query query = currentSession().createQuery(hql);
        query.setString("entryName", entryName);
        List<AdnEntryEntity> adnEntryEntityList = query.list();
        if (adnEntryEntityList != null && !adnEntryEntityList.isEmpty()) {
            return adnEntryEntityList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<AdnEntryEntity> findByServiceIp(String serviceIp) {
        String hql = "FROM AdnEntryEntity WHERE serviceIp = :serviceIp";
        Query query = currentSession().createQuery(hql);
        query.setString("serviceIp", serviceIp);
        return query.list();
    }

    @Override
    public AdnEntryEntity findByServerIpAndEntryType(String serviceIp, String entryType) {
        String hql = "FROM AdnEntryEntity WHERE serviceIp = :serviceIp AND entryType = :entryType";
        Query query = currentSession().createQuery(hql);
        query.setString("serviceIp", serviceIp);
        query.setString("entryType", entryType);
        List<AdnEntryEntity> adnEntryEntityList = query.list();
        if (adnEntryEntityList != null && !adnEntryEntityList.isEmpty()) {
            return adnEntryEntityList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<AdnEntryEntity> findByZoneIdType(long zoneId, String entryType) {
        String hql = "FROM AdnEntryEntity WHERE zoneId = :zoneId AND entryType = :entryType";
        Query query = currentSession().createQuery(hql);
        query.setLong("zoneId", zoneId);
        query.setString("entryType", entryType);
        return query.list();
    }

    @Override
    public List<AdnEntryEntity> findByZoneId(long zoneId) {
        String hql = "FROM AdnEntryEntity WHERE zoneId = :zoneId";
        Query query = currentSession().createQuery(hql);
        query.setLong("zoneId", zoneId);
        return query.list();
    }

    @Override
    public List<AdnEntryEntity> findByServiceIpAndControlIp(String serviceIp, String controlIp) {
        String hql = "FROM AdnEntryEntity WHERE serviceIp = :serviceIp AND controlIp = :controlIp";
        Query query = currentSession().createQuery(hql);
        query.setString("serviceIp", serviceIp);
        query.setString("controlIp", controlIp);
        return query.list();
    }
}
