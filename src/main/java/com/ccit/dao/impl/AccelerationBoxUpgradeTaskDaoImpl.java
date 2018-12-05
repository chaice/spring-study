package com.ccit.dao.impl;

import com.ccit.entity.AccelerationBoxUpgradeTaskEntity;
import com.ccit.dao.AccelerationBoxUpgradeTaskDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccelerationBoxUpgradeTaskDaoImpl extends BaseDaoImpl<AccelerationBoxUpgradeTaskEntity, Long> implements AccelerationBoxUpgradeTaskDao {

    @Override
    public AccelerationBoxUpgradeTaskEntity findByBoxIdPackageId(Long boxId, Long packageId) {
        String hql = "FROM AccelerationBoxUpgradeTaskEntity WHERE boxId = :boxId AND packageId = :packageId";
        Query query = currentSession().createQuery(hql);
        query.setLong("boxId", boxId).setLong("packageId", packageId);

        List<AccelerationBoxUpgradeTaskEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void deleteByPackageId(Long packageId) {
        String hql = "DELETE FROM AccelerationBoxUpgradeTaskEntity WHERE packageId = :packageId";
        Query query = currentSession().createQuery(hql).setLong("packageId", packageId);
        query.executeUpdate();
    }

    @Override
    public void deleteByBoxId(Long boxId) {
        String hql = "DELETE FROM AccelerationBoxUpgradeTaskEntity WHERE boxId = :boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        query.executeUpdate();
    }

    @Override
    public AccelerationBoxUpgradeTaskEntity findByPackageIdAndStatus(Long packageId, String status){
        String hql = "FROM AccelerationBoxUpgradeTaskEntity WHERE packageId = :packageId AND status = :status";
        Query query = currentSession().createQuery(hql);
        query.setLong("packageId", packageId);
        query.setString("status", status);
        List<AccelerationBoxUpgradeTaskEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
