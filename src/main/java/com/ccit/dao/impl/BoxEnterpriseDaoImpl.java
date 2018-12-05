package com.ccit.dao.impl;


import com.ccit.entity.BoxEnterpriseEntity;
import com.ccit.enums.BoxStatusEnum;
import com.ccit.util.StringUtils;
import com.ccit.dao.BoxEnterpriseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoxEnterpriseDaoImpl extends BaseDaoImpl<BoxEnterpriseEntity, Long> implements BoxEnterpriseDao {


    public List<BoxEnterpriseEntity> listBox(String sn, String customerName, String sort) {
        StringBuilder hql = new StringBuilder();
        hql.append("FROM BoxEnterpriseEntity WHERE removed=false");

        if (StringUtils.isNotBlank(sn)) {
            hql.append(" AND serialNumber LIKE :sn");
        }

        hql.append(" ORDER BY status");

        Query query = currentSession().createQuery(hql.toString());

        if (StringUtils.isNotBlank(sn)) {
            query.setString("sn", "%" + sn + "%");
        }

        return query.list();
    }


    public List<BoxEnterpriseEntity> listByCustomerId(long customerId) {
        String hql = "FROM BoxEnterpriseEntity WHERE removed=false AND customerId=:customerId";
        Query query = currentSession().createQuery(hql).setLong("customerId", customerId);
        return query.list();
    }


    public int countByCustomerId(long customerId) {
        String hql = "SELECT count(id) FROM BoxEnterpriseEntity WHERE removed=false AND customerId=:customerId";
        Query query = currentSession().createQuery(hql).setLong("customerId", customerId);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public int countBySn(String sn) {
        String hql = "SELECT count(id) FROM BoxEnterpriseEntity WHERE removed=false AND serialNumber=:sn";
        Query query = currentSession().createQuery(hql).setString("sn", sn);
        return Integer.parseInt(query.uniqueResult().toString());
    }


    public int countByEntryIdMode(long entryId, String mode) {
        String hql = "SELECT count(id) FROM BoxEnterpriseEntity WHERE removed=false AND entryId=:entryId AND accelerateMode = :accelerateMode";
        Query query = currentSession().createQuery(hql).setLong("entryId", entryId).setString("accelerateMode", mode);
        return Integer.parseInt(query.uniqueResult().toString());
    }

    public void updateAllStatus(BoxStatusEnum offline) {
        String hql = "UPDATE BoxEnterpriseEntity SET status = :status WHERE removed=false AND status IN (:ONLINE,:OFFLINE)";
        Query query = currentSession().createQuery(hql);
        query.setString("status", offline.getCode());
        query.setString("ONLINE", BoxStatusEnum.ONLINE.getCode());
        query.setString("OFFLINE", BoxStatusEnum.OFFLINE.getCode());
        query.executeUpdate();
    }


    public BoxEnterpriseEntity findOne(Long id) {
        String hql = "FROM BoxEnterpriseEntity WHERE removed=false AND id=:id";
        Query query = currentSession().createQuery(hql).setLong("id", id);
        return (BoxEnterpriseEntity) query.uniqueResult();
    }

}