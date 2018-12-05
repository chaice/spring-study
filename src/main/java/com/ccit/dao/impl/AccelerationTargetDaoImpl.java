package com.ccit.dao.impl;

import com.ccit.entity.AccelerationTargetEntity;
import com.ccit.dao.AccelerationTargetDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class AccelerationTargetDaoImpl extends BaseDaoImpl<AccelerationTargetEntity, Long> implements AccelerationTargetDao {

    @Override
    public void deleteByExpired(Timestamp expiredTime) {
        String hql = "DELETE FROM AccelerationTargetEntity WHERE updateTime<:expiredTime";
        Query query = currentSession().createQuery(hql);
        query.setTimestamp("expiredTime", expiredTime);
        query.executeUpdate();
    }

    @Override
    public AccelerationTargetEntity findByCIDR(String cidr) {
        String hql = "FROM AccelerationTargetEntity WHERE cidr=:cidr";
        Query query = currentSession().createQuery(hql).setString("cidr", cidr);
        return (AccelerationTargetEntity) query.uniqueResult();
    }
}
