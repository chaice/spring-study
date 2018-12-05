package com.ccit.dao.impl;

import com.ccit.entity.BoxNetworkEntity;
import com.ccit.enums.BoxStatusEnum;
import com.ccit.util.StringUtils;
import com.ccit.dao.BoxNetworkDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoxNetworkDaoImpl extends BaseDaoImpl<BoxNetworkEntity, Long> implements BoxNetworkDao {

    public List<BoxNetworkEntity> listBox(String sn, String customerName, String sort) {
        StringBuilder hql = new StringBuilder();
        hql.append("FROM BoxNetworkEntity WHERE removed=false");

        if (StringUtils.isNotBlank(sn)) {
            hql.append(" AND serialNumber LIKE :sn");
        }

        Query query = currentSession().createQuery(hql.toString());

        if (StringUtils.isNotBlank(sn)) {
            query.setString("sn", "%" + sn + "%");
        }

        return query.list();
    }

    public List<BoxNetworkEntity> listByCustomerId(long customerId) {
        String hql = "FROM BoxNetworkEntity WHERE removed=false AND customerId=:customerId";
        Query query = currentSession().createQuery(hql).setLong("customerId", customerId);
        return query.list();
    }

    public int countByCustomerId(long customerId) {
        String hql = "SELECT count(id) FROM BoxNetworkEntity WHERE removed=false AND customerId=:customerId";
        Query query = currentSession().createQuery(hql).setLong("customerId", customerId);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    public int countBySn(String sn) {
        String hql = "SELECT count(id) FROM BoxNetworkEntity WHERE removed=false AND serialNumber=:sn";
        Query query = currentSession().createQuery(hql).setString("sn", sn);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    public int countByEntryId(long entryId) {
        String hql = "SELECT count(id) FROM BoxNetworkEntity WHERE removed=false AND entryId=:entryId";
        Query query = currentSession().createQuery(hql).setLong("entryId", entryId);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public void updateAllStatus(BoxStatusEnum offline) {
        String hql = "UPDATE BoxNetworkEntity SET status = :status WHERE removed=false AND status IN (:ONLINE,:OFFLINE)";
        Query query = currentSession().createQuery(hql);
        query.setString("status", offline.getCode());
        query.setString("ONLINE", BoxStatusEnum.ONLINE.getCode());
        query.setString("OFFLINE", BoxStatusEnum.OFFLINE.getCode());
        query.executeUpdate();
    }

    public BoxNetworkEntity findOne(Long id) {
        String hql = "FROM BoxNetworkEntity WHERE removed=false AND id=:id";
        Query query = currentSession().createQuery(hql).setLong("id", id);
        return (BoxNetworkEntity) query.uniqueResult();
    }

}