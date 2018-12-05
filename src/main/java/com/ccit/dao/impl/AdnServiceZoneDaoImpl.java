package com.ccit.dao.impl;

import com.ccit.entity.AdnServiceZoneEntity;
import com.ccit.dao.AdnServiceZoneDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdnServiceZoneDaoImpl extends BaseDaoImpl<AdnServiceZoneEntity, Long> implements AdnServiceZoneDao {

    @Override
    public List<AdnServiceZoneEntity> findByServiceIdAndServiceType(long serviceId, String serviceType) {
        String hql = "FROM AdnServiceZoneEntity WHERE serviceId = :serviceId AND serviceType = :serviceType";
        Query query = currentSession().createQuery(hql);
        query.setLong("serviceId", serviceId);
        query.setString("serviceType", serviceType);
        return query.list();
    }

    @Override
    public void deleteByServiceIdAndServiceType(long serviceId, String serviceType) {
        String hql = "DELETE FROM AdnServiceZoneEntity WHERE serviceId = :serviceId AND serviceType = :serviceType";
        Query query = currentSession().createQuery(hql);
        query.setLong("serviceId", serviceId);
        query.setString("serviceType", serviceType);
        query.executeUpdate();
    }

    @Override
    public AdnServiceZoneEntity findByServiceIdAndZoneIdAndServiceType(long serviceId, Long zoneId, String serviceType) {
        String hql = "FROM AdnServiceZoneEntity WHERE serviceId = :serviceId AND zoneId = :zoneId AND serviceType = :serviceType";
        Query query = currentSession().createQuery(hql);
        query.setLong("serviceId", serviceId);
        query.setLong("zoneId", zoneId);
        query.setString("serviceType", serviceType);
        List<AdnServiceZoneEntity> serviceZoneEntityList = query.list();
        if (serviceZoneEntityList != null && !serviceZoneEntityList.isEmpty()) {
            return serviceZoneEntityList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public AdnServiceZoneEntity findByZoneId(long zoneId) {
        String hql = "FROM AdnServiceZoneEntity WHERE zoneId=:zoneId";
        Query query = currentSession().createQuery(hql);
        query.setLong("zoneId", zoneId);
        List<AdnServiceZoneEntity> adnServiceZoneEntities = query.list();
        if (adnServiceZoneEntities != null && !adnServiceZoneEntities.isEmpty()) {
            return adnServiceZoneEntities.get(0);
        } else {
            return null;
        }
    }

    @Override
    public AdnServiceZoneEntity findByCustomerZoneId(Long customerId, Long zoneId) {
        String hql = "FROM AdnServiceZoneEntity WHERE zoneId=:zoneId AND customerId=:customerId";
        Query query = currentSession().createQuery(hql);
        query.setLong("zoneId", zoneId);
        query.setLong("customerId", customerId);
        List<AdnServiceZoneEntity> adnServiceZoneEntities = query.list();
        if (adnServiceZoneEntities != null && !adnServiceZoneEntities.isEmpty()) {
            return adnServiceZoneEntities.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<AdnServiceZoneEntity> findByCustomerId(Long customerId) {
        String hql = "FROM AdnServiceZoneEntity WHERE customerId=:customerId";
        Query query = currentSession().createQuery(hql);
        query.setLong("customerId", customerId);
        return query.list();
    }

    @Override
    public void deleteByCustomerId(long id) {
        String hql = "DELETE FROM AdnServiceZoneEntity WHERE customerId = :customerId ";
        Query query = currentSession().createQuery(hql);
        query.setLong("customerId", id);
        query.executeUpdate();
    }

    @Override
    public List<AdnServiceZoneEntity> findByServiceId(long serviceId, String serviceType) {
        String hql = "FROM AdnServiceZoneEntity WHERE serviceId = :serviceId AND serviceType=:serviceType";
        Query query = currentSession().createQuery(hql);
        query.setLong("serviceId", serviceId);
        query.setString("serviceType", serviceType);
        return query.list();
    }
}
