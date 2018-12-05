package com.ccit.dao.impl;

import com.ccit.entity.NetworkBoxUpgradePackageEntity;
import com.ccit.dao.NetworkBoxUpgradePackageDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NetworkBoxUpgradePackageDaoImpl extends BaseDaoImpl<NetworkBoxUpgradePackageEntity, Long> implements NetworkBoxUpgradePackageDao {


    public NetworkBoxUpgradePackageEntity findByFileMd5(String  fileMd5) {
        String hql = "FROM NetworkBoxUpgradePackageEntity WHERE fileMd5=:md5 ";
        Query query = currentSession().createQuery(hql);
        query.setString("md5", fileMd5);
        List<NetworkBoxUpgradePackageEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
