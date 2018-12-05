package com.ccit.dao.impl;

import com.ccit.entity.AdnServiceEntryEntity;
import com.ccit.dao.AdnServiceEntryDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdnServiceEntryDaoImpl extends BaseDaoImpl<AdnServiceEntryEntity, Long> implements AdnServiceEntryDao {

    @Override
    public List<AdnServiceEntryEntity> findByEntryId(long entryId) {
        String hql = "FROM AdnServiceEntryEntity WHERE entryId = :entryId";
        Query query = currentSession().createQuery(hql);
        query.setLong("entryId", entryId);
        return query.list();
    }

    @Override
    public List<AdnServiceEntryEntity> findByServiceTypeServiceIdZoneId(String serviceType, long serviceId, long zoneId) {
        String hql = "FROM AdnServiceEntryEntity WHERE serviceType = :serviceType AND serviceId = :serviceId AND zoneId = :zoneId";
        Query query = currentSession().createQuery(hql);
        query.setString("serviceType", serviceType);
        query.setLong("serviceId", serviceId);
        query.setLong("zoneId", zoneId);

        return query.list();
    }

    @Override
    public void deleteByServiceTypeServiceIdZoneId(String serviceType, long serviceId, long zoneId) {
        String hql = "DELETE FROM AdnServiceEntryEntity WHERE serviceType = :serviceType AND serviceId = :serviceId AND zoneId = :zoneId ";
        Query query = currentSession().createQuery(hql);
        query.setString("serviceType", serviceType);
        query.setLong("serviceId", serviceId);
        query.setLong("zoneId", zoneId);

        query.executeUpdate();
    }

    @Override
    public AdnServiceEntryEntity findByServiceTypeEntryIdSourcePortTransportProtocol(String serviceType, long entryId, Integer sourcePort, String transportProtocol) {
        String hql = "FROM AdnServiceEntryEntity WHERE serviceType = :serviceType AND entryId = :entryId AND sourcePort = :sourcePort AND transportProtocol = :transportProtocol";
        Query query = currentSession().createQuery(hql);
        query.setString("serviceType", serviceType);
        query.setLong("entryId", entryId);
        query.setInteger("sourcePort", sourcePort);
        query.setString("transportProtocol", transportProtocol);

        List<AdnServiceEntryEntity> adnServiceEntryEntityList = query.list();
        if (adnServiceEntryEntityList != null && !adnServiceEntryEntityList.isEmpty()) {
            return adnServiceEntryEntityList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<AdnServiceEntryEntity> findByEntryIdSourcePort(long entryId, Integer sourcePort) {
        String hql = "FROM AdnServiceEntryEntity WHERE entryId =:entryId AND sourcePort = :sourcePort";
        Query query = currentSession().createQuery(hql);
        query.setLong("entryId", entryId);
        query.setInteger("sourcePort", sourcePort);

        return query.list();
    }

    @Override
    public AdnServiceEntryEntity findByServiceTypeServiceIdEntryId(String serviceType, Long serviceId, long entryId) {
        String hql = "FROM AdnServiceEntryEntity WHERE serviceType = :serviceType AND serviceId = :serviceId AND entryId = :entryId";
        Query query = currentSession().createQuery(hql);
        query.setString("serviceType", serviceType);
        query.setLong("serviceId", serviceId);
        query.setLong("entryId", entryId);

        List<AdnServiceEntryEntity> adnServiceEntryEntityList = query.list();
        if (adnServiceEntryEntityList != null && !adnServiceEntryEntityList.isEmpty()) {
            return adnServiceEntryEntityList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<AdnServiceEntryEntity> findByEntryIdSourcePortTransportProtocol(long entryId, Integer sourcePort, String transportProtocol) {
        String hql = "FROM AdnServiceEntryEntity WHERE entryId =:entryId AND sourcePort = :sourcePort AND transportProtocol = :transportProtocol";
        Query query = currentSession().createQuery(hql);
        query.setLong("entryId", entryId);
        query.setInteger("sourcePort", sourcePort);
        query.setString("transportProtocol", transportProtocol);

        return query.list();
    }
}
