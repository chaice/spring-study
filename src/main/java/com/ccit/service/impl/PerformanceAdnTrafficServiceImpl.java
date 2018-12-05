package com.ccit.service.impl;


import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.exception.BusinessException;
import com.ccit.bean.PerformanceBean;
import com.ccit.bean.PerformanceQueryBean;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.enums.AdnServiceTypeEnum;
import com.ccit.enums.PerformanceInterval;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.rest.rainbow.interfaces.PerformanceIF;
import com.ccit.rest.rainbow.request.PerformanceTrafficReq;
import com.ccit.rest.rainbow.response.PerformanceRes;
import com.ccit.service.ConvertService;
import com.ccit.service.PerformanceAdnTrafficService;
import com.ccit.vo.PerformanceTrafficVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class PerformanceAdnTrafficServiceImpl implements PerformanceAdnTrafficService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private PerformanceAdnTrafficDao performanceAdnTrafficDao;

    @Autowired
    private AdnServiceEntryDao adnServiceEntryDao;

    @Autowired
    private AdnServiceZoneDao adnServiceZoneDao;

    @Autowired
    private AdnEntryDao adnEntryDao;

    @Autowired
    private AdnHttpDomainDao adnHttpDomainDao;

    @Autowired
    private AdnHttpServiceDao adnHttpServiceDao;

    @Autowired
    private AdnL3ServiceDao adnL3ServiceDao;

    @Autowired
    private ConvertService convertService;

    @Autowired
    private PerformanceIF performanceIF;

    private static final long performanceInterval = 1000l * PerformanceInterval.DAILY.getCode();

    @Override
    public List<PerformanceTrafficVo> getCustomerBandwidth(long customerId, PerformanceQueryBean performanceQueryBean) {
        List<PerformanceAdnEntryTrafficEntity> trafficList = performanceAdnTrafficDao.findTrafficByCustomerId(customerId, performanceQueryBean);
        return convertService.convertTrafficEntityToVo(trafficList, performanceQueryBean);
    }

    @Override
    public List<PerformanceTrafficVo> getCurZoneBandwidth(long zoneId, PerformanceQueryBean performanceQueryBean) {
        List<PerformanceAdnEntryTrafficEntity> trafficList = performanceAdnTrafficDao.findTrafficByCustomerZone(zoneId, performanceQueryBean);
        return convertService.convertTrafficEntityToVo(trafficList, performanceQueryBean);
    }

    @Override
    public List<PerformanceTrafficVo> getZoneEntryBandwidth(long entryId, PerformanceQueryBean performanceQueryBean) {
        List<PerformanceAdnEntryTrafficEntity> trafficList = performanceAdnTrafficDao.findTrafficEntry(entryId, performanceQueryBean);
        return convertService.convertTrafficEntityToVo(trafficList, performanceQueryBean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createPerformanceAdnTraffic(PerformanceBean performanceBean) {
        if (performanceBean.getPerformanceTrafficList().isEmpty()) {
            return;
        }
        Timestamp samplingTime = new Timestamp(System.currentTimeMillis() / performanceInterval * performanceInterval);
        performanceBean.getPerformanceTrafficList().forEach(performanceAdnTrafficBean -> {

            PerformanceAdnEntryTrafficEntity currentTrafficEntity;

            //入口类型是三层的执行
            if (AdnServiceTypeEnum.THREE_LAYER.getType().equals(performanceAdnTrafficBean.getServiceType())) {
                List<AdnEntryEntity> adnEntryEntityList = adnEntryDao.findByServiceIp(performanceAdnTrafficBean.getServiceIp());

                List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByEntryId(adnEntryEntityList.get(0).getId());

                if (adnServiceEntryEntityList == null || adnServiceEntryEntityList.isEmpty()) {
                    return;
                }

                //三层入口根据入口id只能找到一个服务
                AdnL3ServiceEntity adnL3ServiceEntity = adnL3ServiceDao.findOne(adnServiceEntryEntityList.get(0).getServiceId());
                if (adnL3ServiceEntity == null) {
                    return;
                }

                AdnServiceZoneEntity adnServiceZoneEntity = adnServiceZoneDao.findByServiceIdAndZoneIdAndServiceType(adnServiceEntryEntityList.get(0).getServiceId(), adnServiceEntryEntityList.get(0).getZoneId(), AdnServiceTypeEnum.THREE_LAYER.getType());
                if (adnServiceZoneEntity == null) {
                    return;
                }

                currentTrafficEntity = performanceAdnTrafficDao.findByServiceEntryIdSamplingTime(adnServiceEntryEntityList.get(0).getId(), samplingTime);
                if (currentTrafficEntity == null) {
                    currentTrafficEntity = new PerformanceAdnEntryTrafficEntity();
                }
                currentTrafficEntity.setServiceEntryId(adnServiceEntryEntityList.get(0).getId());
                currentTrafficEntity.setServiceZoneId(adnServiceZoneEntity.getId());
                currentTrafficEntity.setServiceId(adnL3ServiceEntity.getId());
                currentTrafficEntity.setServiceType(AdnServiceTypeEnum.THREE_LAYER.getType());
                currentTrafficEntity.setEntryId(adnEntryEntityList.get(0).getId());
                currentTrafficEntity.setZoneId(adnServiceZoneEntity.getZoneId());
                currentTrafficEntity.setCustomerId(adnL3ServiceEntity.getCustomerId());
                currentTrafficEntity.setSamplingTime(samplingTime);

                addAdnEntryTraffic(currentTrafficEntity, samplingTime, performanceAdnTrafficBean.getTx(), performanceAdnTrafficBean.getRx());

                return;
            }


            AdnEntryEntity adnEntryEntity = adnEntryDao.findByServerIpAndEntryType(performanceBean.getControlIp(), performanceAdnTrafficBean.getServiceType());
            if (adnEntryEntity == null) {
                return;
            }

            AdnServiceEntryEntity adnServiceEntryEntity = adnServiceEntryDao.findByServiceTypeEntryIdSourcePortTransportProtocol(AdnServiceTypeEnum.FOUR_LAYER.getType(), adnEntryEntity.getId(), performanceAdnTrafficBean.getSourcePort(), performanceAdnTrafficBean.getTransportProtocol());
            //入口id加源端口存在记录,4层
            if (adnServiceEntryEntity != null) {
                AdnServiceZoneEntity adnServiceZoneEntity = adnServiceZoneDao.findByServiceIdAndZoneIdAndServiceType(adnServiceEntryEntity.getServiceId(), adnServiceEntryEntity.getZoneId(), AdnServiceTypeEnum.FOUR_LAYER.getType());
                if (adnServiceZoneEntity == null) {
                    return;
                }
                currentTrafficEntity = performanceAdnTrafficDao.findByServiceEntryIdSamplingTime(adnServiceEntryEntity.getId(), samplingTime);
                if (currentTrafficEntity == null) {
                    currentTrafficEntity = new PerformanceAdnEntryTrafficEntity();
                }
                currentTrafficEntity.setServiceEntryId(adnServiceEntryEntity.getId());
                currentTrafficEntity.setServiceZoneId(adnServiceZoneEntity.getId());
                currentTrafficEntity.setServiceId(adnServiceZoneEntity.getServiceId());
                currentTrafficEntity.setServiceType(AdnServiceTypeEnum.FOUR_LAYER.getType());
                currentTrafficEntity.setEntryId(adnServiceEntryEntity.getEntryId());
                currentTrafficEntity.setZoneId(adnServiceZoneEntity.getZoneId());
                currentTrafficEntity.setCustomerId(adnServiceZoneEntity.getCustomerId());
                currentTrafficEntity.setSamplingTime(samplingTime);

                addAdnEntryTraffic(currentTrafficEntity, samplingTime, performanceAdnTrafficBean.getTx(), performanceAdnTrafficBean.getRx());
            } else {//不存在,7层
                //根据sourceIp查询服务域名
                List<AdnHttpDomainEntity> adnHttpDomainEntityList = adnHttpDomainDao.findBySourceIp(performanceAdnTrafficBean.getSourceIp());
                if (adnHttpDomainEntityList == null || adnHttpDomainEntityList.isEmpty()) {
                    return;
                }
                //根据服务域名记录中关联的服务id找到7层服务
                AdnHttpServiceEntity adnHttpServiceEntity = adnHttpServiceDao.findBySourcePortAndCustomerId(performanceAdnTrafficBean.getSourcePort(), adnHttpDomainEntityList.get(0).getCustomerId());
                if (adnHttpServiceEntity == null) {
                    return;
                }
                //根据服务id找到服务区域关联记录
                List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnHttpServiceEntity.getId(), AdnServiceTypeEnum.HTTP.getType());
                for (AdnServiceZoneEntity adnServiceZoneEntity : adnServiceZoneEntityList) {
                    //根据服务区域关联记录+入口id找到唯一的入口服务区域关联记录
                    AdnServiceEntryEntity adnServiceHttpEntryEntity = adnServiceEntryDao.findByServiceTypeServiceIdEntryId(adnHttpServiceEntity.getServiceType(), adnServiceZoneEntity.getServiceId(), adnEntryEntity.getId());
                    if (adnServiceHttpEntryEntity == null) {
                        return;
                    }
                    currentTrafficEntity = performanceAdnTrafficDao.findByServiceEntryIdSamplingTime(adnServiceHttpEntryEntity.getId(), samplingTime);
                    if (currentTrafficEntity == null) {
                        currentTrafficEntity = new PerformanceAdnEntryTrafficEntity();
                    }
                    currentTrafficEntity.setServiceEntryId(adnServiceHttpEntryEntity.getId());
                    currentTrafficEntity.setServiceZoneId(adnServiceZoneEntity.getId());
                    currentTrafficEntity.setServiceId(adnHttpServiceEntity.getId());
                    currentTrafficEntity.setServiceType(AdnServiceTypeEnum.HTTP.getType());
                    currentTrafficEntity.setEntryId(adnServiceHttpEntryEntity.getEntryId());
                    currentTrafficEntity.setZoneId(adnServiceZoneEntity.getZoneId());
                    currentTrafficEntity.setCustomerId(adnHttpServiceEntity.getCustomerId());
                    currentTrafficEntity.setSamplingTime(samplingTime);
                    currentTrafficEntity.setServiceHttpDomainId(adnHttpDomainEntityList.get(0).getId());

                    addAdnEntryTraffic(currentTrafficEntity, samplingTime, performanceAdnTrafficBean.getTx(), performanceAdnTrafficBean.getRx());
                }
            }
        });
    }

    @Override
    public List<PerformanceTrafficVo> getServiceTraffic(long serviceId, String serviceType, PerformanceQueryBean performanceQueryBean) {
        List<PerformanceAdnEntryTrafficEntity> trafficList = performanceAdnTrafficDao.findTrafficByService(serviceId, serviceType, performanceQueryBean);
        return convertService.convertTrafficEntityToVo(trafficList, performanceQueryBean);
    }

    @Override
    public List<PerformanceTrafficVo> getZoneTraffic(long serviceId, long zoneId, String serviceType, PerformanceQueryBean performanceQueryBean) {
        List<PerformanceAdnEntryTrafficEntity> trafficList = performanceAdnTrafficDao.findTrafficByServiceZone(serviceId, zoneId, serviceType, performanceQueryBean);
        return convertService.convertTrafficEntityToVo(trafficList, performanceQueryBean);
    }

    @Override
    public List<PerformanceTrafficVo> getEntryTraffic(long serviceId, long zoneId, long entryId, String serviceType, PerformanceQueryBean performanceQueryBean) {
        List<PerformanceAdnEntryTrafficEntity> trafficList = performanceAdnTrafficDao.findTrafficByServiceEntry(serviceId, zoneId, entryId, serviceType, performanceQueryBean);
        return convertService.convertTrafficEntityToVo(trafficList, performanceQueryBean);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void addAdnEntryTraffic(PerformanceAdnEntryTrafficEntity currentTrafficEntity, Timestamp samplingTime, long tx, long rx) {

        PerformanceAdnEntryTrafficEntity lastTrafficEntity = performanceAdnTrafficDao.findByServiceEntryIdSamplingTime(currentTrafficEntity.getServiceEntryId(), new Timestamp(samplingTime.getTime() - performanceInterval));

        currentTrafficEntity.setAbsoluteThroughputRx(rx);
        currentTrafficEntity.setAbsoluteThroughputTx(tx);
        if (lastTrafficEntity == null) {
            currentTrafficEntity.setDifferenceThroughputRx(0l);
            currentTrafficEntity.setDifferenceThroughputTx(0l);
        } else {
            long differentThroughputRx = currentTrafficEntity.getAbsoluteThroughputRx() - lastTrafficEntity.getAbsoluteThroughputRx();
            if (differentThroughputRx > 0) {
                currentTrafficEntity.setDifferenceThroughputRx(differentThroughputRx);
            } else {
                currentTrafficEntity.setDifferenceThroughputRx(0l);
            }
            long differentThroughputTx = currentTrafficEntity.getAbsoluteThroughputTx() - lastTrafficEntity.getAbsoluteThroughputTx();
            if (differentThroughputTx > 0) {
                currentTrafficEntity.setDifferenceThroughputTx(differentThroughputTx);
            } else {
                currentTrafficEntity.setDifferenceThroughputTx(0l);
            }
        }
        currentTrafficEntity.setBandwidthRx(currentTrafficEntity.getDifferenceThroughputRx() * 8 / PerformanceInterval.DAILY.getCode());
        currentTrafficEntity.setBandwidthTx(currentTrafficEntity.getDifferenceThroughputTx() * 8 / PerformanceInterval.DAILY.getCode());

        performanceAdnTrafficDao.saveOrUpdate(currentTrafficEntity);
        //同步到rainbow
        syncAdnTrafficToRainbow(currentTrafficEntity);
    }

    //同步流量到rainbow
    private void syncAdnTrafficToRainbow(PerformanceAdnEntryTrafficEntity currentTrafficEntity) {
        PerformanceTrafficReq performanceTrafficReq = new PerformanceTrafficReq(currentTrafficEntity);
        PerformanceRes performanceRes = performanceIF.syncAdnTraffic(performanceTrafficReq);
        if (!performanceRes.isSuccess()) {
            logger.error(String.format("RainBow-云链服务流量-同步服务流量失败，错误码：%s，错误信息：%s",
                    performanceRes.getErrorBody().getErrorCode(),
                    performanceRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.PERFORMANCE_TRAFFIC_SYNC_FAIL);
        }
    }
}
