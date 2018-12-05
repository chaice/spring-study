package com.ccit.dao.impl;

import com.ccit.entity.NetworkBoxUpgradeTaskEntity;
import com.ccit.dao.NetworkBoxUpgradeTaskDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NetworkBoxUpgradeTaskDaoImpl extends BaseDaoImpl<NetworkBoxUpgradeTaskEntity, Long> implements NetworkBoxUpgradeTaskDao {

    @Override
    public NetworkBoxUpgradeTaskEntity findByBoxIdPackageId(Long boxId, Long packageId) {
        String hql = "FROM NetworkBoxUpgradeTaskEntity WHERE boxId = :boxId AND packageId = :packageId";
        Query query = currentSession().createQuery(hql);
        query.setLong("boxId", boxId).setLong("packageId", packageId);

        List<NetworkBoxUpgradeTaskEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void deleteByPackageId(Long packageId) {
        String hql = "DELETE FROM NetworkBoxUpgradeTaskEntity WHERE packageId = :packageId";
        Query query = currentSession().createQuery(hql).setLong("packageId", packageId);
        query.executeUpdate();
    }

    @Override
    public void deleteByBoxId(Long boxId) {
        String hql = "DELETE FROM NetworkBoxUpgradeTaskEntity WHERE boxId = :boxId";
        Query query = currentSession().createQuery(hql).setLong("boxId", boxId);
        query.executeUpdate();
    }

    @Override
    public NetworkBoxUpgradeTaskEntity findByPackageIdAndStatus(Long packageId, String status){
        String hql = "FROM NetworkBoxUpgradeTaskEntity WHERE packageId = :packageId AND status = :status";
        Query query = currentSession().createQuery(hql);
        query.setLong("packageId", packageId);
        query.setString("status", status);
        List<NetworkBoxUpgradeTaskEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
