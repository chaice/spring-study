package com.ccit.dao.impl;

import com.ccit.entity.AccelerationBoxUpgradePackageEntity;
import com.ccit.dao.AccelerationBoxUpgradePackageDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccelerationBoxUpgradePackageDaoImpl extends BaseDaoImpl<AccelerationBoxUpgradePackageEntity, Long> implements AccelerationBoxUpgradePackageDao {


    public AccelerationBoxUpgradePackageEntity findByFileMd5(String  fileMd5) {
        String hql = "FROM AccelerationBoxUpgradePackageEntity WHERE fileMd5=:md5 ";
        Query query = currentSession().createQuery(hql);
        query.setString("md5", fileMd5);
        List<AccelerationBoxUpgradePackageEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
