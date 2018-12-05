package com.ccit.dao;

import com.ccit.entity.AdnEntryEntity;

import java.util.List;

public interface AdnEntryDao extends BaseDao<AdnEntryEntity, Long> {

    AdnEntryEntity findByEntryName(String entryName);

    List<AdnEntryEntity> findByServiceIp(String serviceIp);

    AdnEntryEntity findByServerIpAndEntryType(String serviceIp, String entryType);

    List<AdnEntryEntity> findByZoneIdType(long zoneId, String zoneType);

    List<AdnEntryEntity> findByZoneId(long zoneId);

    List<AdnEntryEntity> findByServiceIpAndControlIp(String serviceIp, String controlIp);
}
