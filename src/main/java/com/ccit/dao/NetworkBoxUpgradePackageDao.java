package com.ccit.dao;

import com.ccit.entity.NetworkBoxUpgradePackageEntity;

public interface NetworkBoxUpgradePackageDao extends BaseDao<NetworkBoxUpgradePackageEntity, Long> {
    NetworkBoxUpgradePackageEntity findByFileMd5(String fileMd5);
}
