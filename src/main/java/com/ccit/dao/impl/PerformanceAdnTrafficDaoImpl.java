package com.ccit.dao.impl;

import com.ccit.bean.PerformanceQueryBean;
import com.ccit.entity.PerformanceAdnEntryTrafficEntity;
import com.ccit.dao.PerformanceAdnTrafficDao;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PerformanceAdnTrafficDaoImpl extends BaseDaoImpl<PerformanceAdnEntryTrafficEntity, Long> implements PerformanceAdnTrafficDao {

    @Override
    public void deleteByServiceIdAndServiceType(long serviceId, String serviceType) {
        String hql = "DELETE FROM PerformanceAdnEntryTrafficEntity WHERE serviceId = :serviceId AND serviceType = :serviceType";
        Query query = currentSession().createQuery(hql);
        query.setLong("serviceId", serviceId);
        query.setString("serviceType", serviceType);
        query.executeUpdate();
    }

    @Override
    public List<PerformanceAdnEntryTrafficEntity> findTrafficByService(long serviceId, String serviceType, PerformanceQueryBean performanceQueryBean) {
        Query query;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT");
        sql.append(" sum(`difference_throughput_tx`) AS `differenceThroughputTx`,");
        sql.append(" sum(`difference_throughput_rx`) AS `differenceThroughputRx`,");
        sql.append(" sum(`bandwidth_tx`) AS `bandwidthTx`,");
        sql.append(" sum(`bandwidth_rx`) AS `bandwidthRx`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(") AS `samplingTime`,");
        sql.append(" `service_id` AS `serviceId`,");
        sql.append(" `service_type` AS `serviceType` ");
        sql.append(" FROM");
        sql.append(" `performance_adn_entry_traffic`");
        sql.append(" WHERE `service_id`=:serviceId and `service_type`=:serviceType and `sampling_time`>=:beginTime and `sampling_time` < :endTime");
        sql.append(" GROUP BY");
        sql.append(" `service_id`,");
        sql.append(" `service_type`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(")");
        sql.append(" ORDER BY");
        sql.append(" `sampling_time`;");

        query = currentSession().createSQLQuery(sql.toString())
                .addScalar("differenceThroughputTx", LongType.INSTANCE)
                .addScalar("differenceThroughputRx", LongType.INSTANCE)
                .addScalar("bandwidthTx", LongType.INSTANCE)
                .addScalar("bandwidthRx", LongType.INSTANCE)
                .addScalar("samplingTime", TimestampType.INSTANCE)
                .addScalar("serviceId", LongType.INSTANCE)
                .addScalar("serviceType", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(PerformanceAdnEntryTrafficEntity.class));
        query.setLong("serviceId", serviceId);
        query.setString("serviceType", serviceType);
        query.setTimestamp("beginTime", performanceQueryBean.getBeginTime());
        query.setTimestamp("endTime", performanceQueryBean.getEndTime());

        return query.list();
    }

    @Override
    public List<PerformanceAdnEntryTrafficEntity> findTrafficByServiceZone(long serviceId, long zoneId, String serviceType, PerformanceQueryBean performanceQueryBean) {
        Query query;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT");
        sql.append(" sum(`difference_throughput_tx`) AS `differenceThroughputTx`,");
        sql.append(" sum(`difference_throughput_rx`) AS `differenceThroughputRx`,");
        sql.append(" sum(`bandwidth_tx`) AS `bandwidthTx`,");
        sql.append(" sum(`bandwidth_rx`) AS `bandwidthRx`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(") AS `samplingTime`,");
        sql.append(" `service_id` AS `serviceId` ,");
        sql.append(" `service_type` AS `serviceType` ,");
        sql.append(" `zone_id` AS `zoneId` ");
        sql.append(" FROM");
        sql.append(" `performance_adn_entry_traffic`");
        sql.append(" WHERE `zone_id`=:zoneId and `service_id`=:serviceId and `service_type`=:serviceType and `sampling_time`>=:beginTime and `sampling_time` < :endTime");
        sql.append(" GROUP BY");
        sql.append(" `service_id` ,");
        sql.append(" `serviceType` ,");
        sql.append(" `zone_id` ,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(")");
        sql.append(" ORDER BY");
        sql.append(" `sampling_time`;");

        query = currentSession().createSQLQuery(sql.toString())
                .addScalar("differenceThroughputTx", LongType.INSTANCE)
                .addScalar("differenceThroughputRx", LongType.INSTANCE)
                .addScalar("bandwidthTx", LongType.INSTANCE)
                .addScalar("bandwidthRx", LongType.INSTANCE)
                .addScalar("samplingTime", TimestampType.INSTANCE)
                .addScalar("zoneId", LongType.INSTANCE)
                .addScalar("serviceId", LongType.INSTANCE)
                .addScalar("serviceType", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(PerformanceAdnEntryTrafficEntity.class));
        query.setLong("zoneId", zoneId);
        query.setLong("serviceId", serviceId);
        query.setString("serviceType", serviceType);

        query.setTimestamp("beginTime", performanceQueryBean.getBeginTime());
        query.setTimestamp("endTime", performanceQueryBean.getEndTime());

        return query.list();
    }

    @Override
    public List<PerformanceAdnEntryTrafficEntity> findTrafficByServiceEntry(long serviceId, long zoneId, long entryId, String serviceType, PerformanceQueryBean performanceQueryBean) {
        Query query;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT");
        sql.append(" sum(`difference_throughput_tx`) AS `differenceThroughputTx`,");
        sql.append(" sum(`difference_throughput_rx`) AS `differenceThroughputRx`,");
        sql.append(" sum(`bandwidth_tx`) AS `bandwidthTx`,");
        sql.append(" sum(`bandwidth_rx`) AS `bandwidthRx`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(") AS `samplingTime`,");
        sql.append(" `service_id` AS `serviceId` ,");
        sql.append(" `service_type` AS `serviceType` ,");
        sql.append(" `zone_id` AS `zoneId` ,");
        sql.append(" `entry_id` AS `entryId` ");
        sql.append(" FROM");
        sql.append(" `performance_adn_entry_traffic`");
        sql.append(" WHERE `zone_id`=:zoneId and `service_id`=:serviceId and `service_type`=:serviceType and `entry_id`=:entryId and `sampling_time`>=:beginTime and `sampling_time` < :endTime");
        sql.append(" GROUP BY");
        sql.append(" `service_id` ,");
        sql.append(" `serviceType` ,");
        sql.append(" `zone_id` ,");
        sql.append(" `entry_id` ,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(")");
        sql.append(" ORDER BY");
        sql.append(" `sampling_time`;");

        query = currentSession().createSQLQuery(sql.toString())
                .addScalar("differenceThroughputTx", LongType.INSTANCE)
                .addScalar("differenceThroughputRx", LongType.INSTANCE)
                .addScalar("bandwidthTx", LongType.INSTANCE)
                .addScalar("bandwidthRx", LongType.INSTANCE)
                .addScalar("samplingTime", TimestampType.INSTANCE)
                .addScalar("entryId", LongType.INSTANCE)
                .addScalar("zoneId", LongType.INSTANCE)
                .addScalar("serviceId", LongType.INSTANCE)
                .addScalar("serviceType", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(PerformanceAdnEntryTrafficEntity.class));
        query.setLong("zoneId", zoneId);
        query.setLong("serviceId", serviceId);
        query.setString("serviceType", serviceType);
        query.setString("entryId", serviceType);

        query.setTimestamp("beginTime", performanceQueryBean.getBeginTime());
        query.setTimestamp("endTime", performanceQueryBean.getEndTime());

        return query.list();
    }

    @Override
    public List<PerformanceAdnEntryTrafficEntity> findTrafficByCustomerId(long customerId, PerformanceQueryBean performanceQueryBean) {
        Query query;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT");
        sql.append(" sum(`difference_throughput_tx`) AS `differenceThroughputTx`,");
        sql.append(" sum(`difference_throughput_rx`) AS `differenceThroughputRx`,");
        sql.append(" sum(`bandwidth_tx`) AS `bandwidthTx`,");
        sql.append(" sum(`bandwidth_rx`) AS `bandwidthRx`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(") AS `samplingTime`,");
        sql.append(" `customer_id` AS `customerId` ");
        sql.append(" FROM");
        sql.append(" `performance_adn_entry_traffic`");
        sql.append(" WHERE `customer_id`=:customerId and `sampling_time`>=:beginTime and `sampling_time` < :endTime");
        sql.append(" GROUP BY");
        sql.append(" `customer_id`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(")");
        sql.append(" ORDER BY");
        sql.append(" `sampling_time`;");
        query = currentSession().createSQLQuery(sql.toString())
                .addScalar("differenceThroughputTx", LongType.INSTANCE)
                .addScalar("differenceThroughputRx", LongType.INSTANCE)
                .addScalar("bandwidthTx", LongType.INSTANCE)
                .addScalar("bandwidthRx", LongType.INSTANCE)
                .addScalar("samplingTime", TimestampType.INSTANCE)
                .addScalar("customerId", LongType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(PerformanceAdnEntryTrafficEntity.class));
        query.setLong("customerId", customerId);
        query.setTimestamp("beginTime", performanceQueryBean.getBeginTime());
        query.setTimestamp("endTime", performanceQueryBean.getEndTime());

        return query.list();
    }

    @Override
    public List<PerformanceAdnEntryTrafficEntity> findTrafficByCustomerZone(long zoneId, PerformanceQueryBean performanceQueryBean) {
        Query query;

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT");
        sql.append(" sum(`difference_throughput_tx`) AS `differenceThroughputTx`,");
        sql.append(" sum(`difference_throughput_rx`) AS `differenceThroughputRx`,");
        sql.append(" sum(`bandwidth_tx`) AS `bandwidthTx`,");
        sql.append(" sum(`bandwidth_rx`) AS `bandwidthRx`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(") AS `samplingTime`,");
        sql.append(" `zone_id` AS `zoneId` ");
        sql.append(" FROM");
        sql.append(" `performance_adn_entry_traffic`");
        sql.append(" WHERE `zone_id`=:zoneId and `sampling_time`>=:beginTime and `sampling_time` < :endTime");
        sql.append(" GROUP BY");
        sql.append(" `zone_id`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(")");
        sql.append(" ORDER BY");
        sql.append(" `sampling_time`;");

        query = currentSession().createSQLQuery(sql.toString())
                .addScalar("differenceThroughputTx", LongType.INSTANCE)
                .addScalar("differenceThroughputRx", LongType.INSTANCE)
                .addScalar("bandwidthTx", LongType.INSTANCE)
                .addScalar("bandwidthRx", LongType.INSTANCE)
                .addScalar("samplingTime", TimestampType.INSTANCE)
                .addScalar("zoneId", LongType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(PerformanceAdnEntryTrafficEntity.class));
        query.setLong("zoneId", zoneId);
        query.setTimestamp("beginTime", performanceQueryBean.getBeginTime());
        query.setTimestamp("endTime", performanceQueryBean.getEndTime());

        return query.list();
    }

    @Override
    public List<PerformanceAdnEntryTrafficEntity> findTrafficEntry(long entryId, PerformanceQueryBean performanceQueryBean) {
        Query query;
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT");
        sql.append(" sum(`difference_throughput_tx`) AS `differenceThroughputTx`,");
        sql.append(" sum(`difference_throughput_rx`) AS `differenceThroughputRx`,");
        sql.append(" sum(`bandwidth_tx`) AS `bandwidthTx`,");
        sql.append(" sum(`bandwidth_rx`) AS `bandwidthRx`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(") AS `samplingTime`,");
        sql.append(" `entry_id` AS `entryId` ");
        sql.append(" FROM");
        sql.append(" `performance_adn_entry_traffic`");
        sql.append(" WHERE `entry_id`=:entryId and `sampling_time`>=:beginTime and `sampling_time` < :endTime");
        sql.append(" GROUP BY");
        sql.append(" `entry_id`,");
        sql.append(" from_unixtime(");
        sql.append("        (");
        sql.append("                floor(");
        sql.append("                        (");
        sql.append("                                unix_timestamp(`sampling_time`) + " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append("                        ) / " + performanceQueryBean.getInterval());
        sql.append("                ) * " + performanceQueryBean.getInterval());
        sql.append("        ) - " + performanceQueryBean.tz.getRawOffset() / 1000);
        sql.append(")");
        sql.append(" ORDER BY");
        sql.append(" `sampling_time`;");

        query = currentSession().createSQLQuery(sql.toString())
                .addScalar("differenceThroughputTx", LongType.INSTANCE)
                .addScalar("differenceThroughputRx", LongType.INSTANCE)
                .addScalar("bandwidthTx", LongType.INSTANCE)
                .addScalar("bandwidthRx", LongType.INSTANCE)
                .addScalar("samplingTime", TimestampType.INSTANCE)
                .addScalar("entryId", LongType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(PerformanceAdnEntryTrafficEntity.class));
        query.setLong("entryId", entryId);
        query.setTimestamp("beginTime", performanceQueryBean.getBeginTime());
        query.setTimestamp("endTime", performanceQueryBean.getEndTime());

        return query.list();
    }

    @Override
    public PerformanceAdnEntryTrafficEntity findByServiceEntryIdSamplingTime(long serviceEntryId, Timestamp samplingTime) {
        String hql = "FROM PerformanceAdnEntryTrafficEntity WHERE serviceEntryId = :serviceEntryId AND samplingTime = :samplingTime";
        Query query = currentSession().createQuery(hql);
        query.setLong("serviceEntryId", serviceEntryId);
        query.setTimestamp("samplingTime", samplingTime);

        List<PerformanceAdnEntryTrafficEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
