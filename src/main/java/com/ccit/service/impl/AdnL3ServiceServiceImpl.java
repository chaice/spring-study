package com.ccit.service.impl;

import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.exception.BusinessException;
import com.ccit.bean.AdnL3ServiceBean;
import com.ccit.vo.AdnEntryVo;
import com.ccit.vo.AdnL3ServiceVo;
import com.ccit.vo.AdnZoneVo;
import com.ccit.vo.CustomerEnterpriseVo;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.enums.AdnServiceStatus;
import com.ccit.enums.AdnServiceTypeEnum;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.rainbow.interfaces.ServiceIF;
import com.ccit.rest.rainbow.request.Service3LayerReq;
import com.ccit.rest.rainbow.response.ServiceRes;
import com.ccit.rest.service.interfaces.AdnServiceIF;
import com.ccit.rest.service.request.AdnService3LayerReq;
import com.ccit.rest.service.response.AdnServiceRes;
import com.ccit.service.AdnL3ServiceService;
import com.ccit.vo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AdnL3ServiceServiceImpl implements AdnL3ServiceService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AdnL3ServiceDao adnL3ServiceDao;

    @Autowired
    private AdnZoneDao adnZoneDao;

    @Autowired
    private AdnServiceZoneDao adnServiceZoneDao;

    @Autowired
    private AdnServiceEntryDao adnServiceEntryDao;

    @Autowired
    private AdnEntryDao adnEntryDao;

    @Autowired
    private CustomerEnterpriseDao customerEnterpriseDao;

    @Autowired
    private PerformanceAdnTrafficDao performanceAdnTrafficDao;

    @Autowired
    private ServiceIF serviceIF;

    @Autowired
    private AdnServiceIF adnServiceIF;

    @Override
    public List<AdnL3ServiceVo> list3LayerServices() {
        List<AdnL3ServiceEntity> adnL3ServiceEntityList = adnL3ServiceDao.findAll();
        List<AdnL3ServiceVo> adnL3ServiceVoList = new LinkedList<>();
        adnL3ServiceEntityList.forEach(adnL3ServiceEntity -> {
            adnL3ServiceVoList.add(toVo(adnL3ServiceEntity));
        });
        return adnL3ServiceVoList;
    }

    @Override
    public AdnL3ServiceVo get3LayerService(long serviceId) {
        AdnL3ServiceEntity adnL3ServiceEntity = adnL3ServiceDao.findOne(serviceId);
        Validator.notNull(adnL3ServiceEntity, BusinessException.error(CustomerErrorConstants.SERVICE_3LAYER_NOT_EXIST));

        return toVo(adnL3ServiceEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modify3LayerService(AdnL3ServiceBean adnL3ServiceBean) {
        AdnL3ServiceEntity adnL3ServiceEntity = adnL3ServiceDao.findOne(adnL3ServiceBean.getId());

        if (adnL3ServiceEntity == null) {
            adnL3ServiceEntity = new AdnL3ServiceEntity();
            adnL3ServiceEntity.setId(adnL3ServiceBean.getId());
            adnL3ServiceEntity.setServiceName(adnL3ServiceBean.getServiceName());
            adnL3ServiceEntity.setOriginalDomain(adnL3ServiceBean.getOriginalDomain() == null ? "" : adnL3ServiceBean.getOriginalDomain());
            adnL3ServiceEntity.setSourceIp(adnL3ServiceBean.getSourceIp());
            adnL3ServiceEntity.setCustomerId(adnL3ServiceBean.getCustomerId());
            adnL3ServiceEntity.setServiceStatus(adnL3ServiceBean.getServiceStatus());
            adnL3ServiceEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

            adnL3ServiceDao.saveOrUpdate(adnL3ServiceEntity);
            saveServiceZone(adnL3ServiceBean.getZoneList(), adnL3ServiceEntity);
        } else {
            //状态为审核通过
            if (AdnServiceStatus.ACTIVATED.getStatus().equals(adnL3ServiceBean.getServiceStatus())) {
                //检验区域入口是否配置
                List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnL3ServiceEntity.getId(), AdnServiceTypeEnum.THREE_LAYER.getType());
                for (AdnServiceZoneEntity adnServiceZoneEntity : adnServiceZoneEntityList) {
                    Validator.isTrue(adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.THREE_LAYER.getType(), adnL3ServiceEntity.getId(), adnServiceZoneEntity.getZoneId()).size() != 0, BusinessException.error(CustomerErrorConstants.SERVICE_3LAYER_SERVICE_ZONE_ENTRY_EMPTY));
                }
                //bean中的服务域名是否为空
                Validator.notNull(adnL3ServiceBean.getServiceDomain(), BusinessException.error(CustomerErrorConstants.SERVICE_3LAYER_SERVICE_DOMAIN_EMPTY));
                //状态变更,为该服务配置入口
                adnL3ServiceEntity.setServiceDomain(adnL3ServiceBean.getServiceDomain());
                adnL3ServiceEntity.setServiceStatus(AdnServiceStatus.ACTIVATED.getStatus());
                adnL3ServiceEntity.setEffectiveTime(new Timestamp(System.currentTimeMillis()));
                adnL3ServiceDao.saveOrUpdate(adnL3ServiceEntity);

                List<AdnService3LayerReq> adnService3LayerReqList = new LinkedList();
                //审核通过,下发服务信息
                for (AdnServiceZoneEntity adnServiceZoneEntity : adnServiceZoneEntityList) {
                    for (AdnServiceEntryEntity adnServiceEntryEntity : adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.THREE_LAYER.getType(), adnL3ServiceEntity.getId(), adnServiceZoneEntity.getZoneId())) {
                        AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(adnServiceEntryEntity.getEntryId());

                        AdnService3LayerReq adnService3LayerReq = new AdnService3LayerReq();
                        adnService3LayerReq.setServiceId(adnL3ServiceEntity.getId());
                        adnService3LayerReq.setControlIp(adnEntryEntity.getControlIp());
                        adnService3LayerReq.setServiceIp(adnEntryEntity.getServiceIp());
                        adnService3LayerReq.setSourceIp(adnL3ServiceEntity.getSourceIp());

                        adnService3LayerReqList.add(adnService3LayerReq);
                    }
                }
                sync3LayerServices(adnService3LayerReqList);

                //同步到rainbow
                Service3LayerReq service3LayerReq = new Service3LayerReq();
                service3LayerReq.setId(adnL3ServiceEntity.getId());
                service3LayerReq.setServiceDomain(adnL3ServiceEntity.getServiceDomain());
                service3LayerReq.setServiceStatus(adnL3ServiceEntity.getServiceStatus());
                updateService3LayerToRainbow(service3LayerReq);
            } else {
                //rainbow的请求,执行修改服务名称或者源IP
                adnL3ServiceEntity.setServiceName(adnL3ServiceBean.getServiceName());
                if (!adnL3ServiceEntity.getSourceIp().equals(adnL3ServiceBean.getSourceIp())) {
                    adnL3ServiceEntity.setSourceIp(adnL3ServiceBean.getSourceIp());
                    //源IP修改,服务状态要修改
                    adnL3ServiceEntity.setServiceStatus(AdnServiceStatus.IP_WAIT_CHECKING.getStatus());
                }
                adnL3ServiceDao.saveOrUpdate(adnL3ServiceEntity);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyAdnServiceEntry(AdnL3ServiceBean adnL3ServiceBean) {
        Long zoneId = adnL3ServiceBean.getZoneList().get(0);

        AdnL3ServiceEntity adnL3ServiceEntity = adnL3ServiceDao.findOne(adnL3ServiceBean.getId());
        Validator.notNull(adnL3ServiceEntity, BusinessException.error(CustomerErrorConstants.SERVICE_3LAYER_NOT_EXIST));

        AdnServiceZoneEntity adnServiceZoneEntity = adnServiceZoneDao.findByServiceIdAndZoneIdAndServiceType(adnL3ServiceBean.getId(), zoneId, AdnServiceTypeEnum.THREE_LAYER.getType());
        //判断该关联是否存在
        Validator.notNull(adnServiceZoneEntity, BusinessException.error(CustomerErrorConstants.SERVICE_3LAYER_SERVICE_ZONE_NOT_EXIST));

        List<Long> removeEntryIds = new LinkedList<>();
        adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.THREE_LAYER.getType(), adnServiceZoneEntity.getServiceId(), adnServiceZoneEntity.getZoneId()).forEach(adnServiceEntryEntity -> {
            removeEntryIds.add(adnServiceEntryEntity.getEntryId());
        });

        //删除所有的服务区域关联记录
        adnServiceEntryDao.deleteByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.THREE_LAYER.getType(), adnL3ServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
        adnL3ServiceBean.getEntryIds().forEach(entryId -> {

            if (removeEntryIds.contains(entryId)) {
                removeEntryIds.remove(entryId);
            }

            AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);
            Validator.notNull(adnEntryEntity, BusinessException.error(CustomerErrorConstants.ADN_ENTRY_NOT_EXIST));

            AdnServiceEntryEntity adnServiceEntryEntity = new AdnServiceEntryEntity();
            adnServiceEntryEntity.setEntryId(entryId);
            adnServiceEntryEntity.setServiceId(adnL3ServiceEntity.getId());
            adnServiceEntryEntity.setZoneId(zoneId);
            adnServiceEntryEntity.setServiceType(AdnServiceTypeEnum.THREE_LAYER.getType());

            adnServiceEntryDao.saveOrUpdate(adnServiceEntryEntity);
        });

        //服务状态为已审核时下发信息
        if (AdnServiceStatus.ACTIVATED.getStatus().equals(adnL3ServiceEntity.getServiceStatus())) {
            List<AdnService3LayerReq> removeService3LayerReqList = new LinkedList<>();
            removeEntryIds.forEach(entryId -> {
                AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);

                AdnService3LayerReq adnService3LayerReq = new AdnService3LayerReq();
                adnService3LayerReq.setServiceId(adnL3ServiceEntity.getId());
                adnService3LayerReq.setControlIp(adnEntryEntity.getControlIp());
                adnService3LayerReq.setSourceIp(adnL3ServiceEntity.getSourceIp());

                removeService3LayerReqList.add(adnService3LayerReq);
            });
            remove3LayerServices(removeService3LayerReqList);

            List<AdnService3LayerReq> syncService3LayerReqList = new LinkedList<>();
            adnL3ServiceBean.getEntryIds().forEach(entryId -> {
                AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);

                AdnService3LayerReq adnService3LayerReq = new AdnService3LayerReq();
                adnService3LayerReq.setServiceId(adnL3ServiceEntity.getId());
                adnService3LayerReq.setControlIp(adnEntryEntity.getControlIp());
                adnService3LayerReq.setServiceIp(adnEntryEntity.getServiceIp());
                adnService3LayerReq.setSourceIp(adnL3ServiceEntity.getSourceIp());

                syncService3LayerReqList.add(adnService3LayerReq);
            });
            sync3LayerServices(syncService3LayerReqList);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void remove3LayerService(long serviceId) {
        AdnL3ServiceEntity adnL3ServiceEntity = adnL3ServiceDao.findOne(serviceId);

        if (adnL3ServiceEntity != null) {
            List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnL3ServiceEntity.getId(), AdnServiceTypeEnum.THREE_LAYER.getType());
            adnServiceZoneEntityList.forEach(adnServiceZoneEntity -> {
                if (!AdnServiceStatus.WAIT_CHECKING.getStatus().equals(adnL3ServiceEntity.getServiceStatus())) {
                    //删除服务信息
                    List<AdnService3LayerReq> removeService3LayerReqList = new LinkedList<>();
                    List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.THREE_LAYER.getType(), adnL3ServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
                    adnServiceEntryEntityList.forEach(adnServiceEntryEntity -> {
                        AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(adnServiceEntryEntity.getEntryId());

                        AdnService3LayerReq adnService3LayerReq = new AdnService3LayerReq();
                        adnService3LayerReq.setServiceId(adnL3ServiceEntity.getId());
                        adnService3LayerReq.setControlIp(adnEntryEntity.getControlIp());
                        adnService3LayerReq.setSourceIp(adnL3ServiceEntity.getSourceIp());

                        removeService3LayerReqList.add(adnService3LayerReq);
                    });
                    remove3LayerServices(removeService3LayerReqList);
                }

                //删除区域入口关联记录
                adnServiceEntryDao.deleteByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.THREE_LAYER.getType(), adnL3ServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
            });
            //删除服务区域关联记录
            adnServiceZoneDao.deleteByServiceIdAndServiceType(serviceId, AdnServiceTypeEnum.THREE_LAYER.getType());
            //删除服务
            adnL3ServiceDao.deleteById(serviceId);
            //删除服务流量
            performanceAdnTrafficDao.deleteByServiceIdAndServiceType(serviceId, AdnServiceTypeEnum.THREE_LAYER.getType());
            //同步到 rainbow
            removeService3LayerToRainbow(serviceId);
        }
    }

    private void sync3LayerServices(List<AdnService3LayerReq> syncService3LayerReqList) {
        syncService3LayerReqList.forEach(adnService3LayerReq -> {

            AdnServiceRes adnServiceRes = adnServiceIF.syncService3Layer(adnService3LayerReq);
            if (!adnServiceRes.isSuccess()) {
                logger.error(String.format("Matrix-下发3Layer服务信息失败，错误码：%s，错误信息：%s",
                        adnServiceRes.getErrorBody().getErrorCode(),
                        adnServiceRes.getErrorBody().getMessage()));
            }

        });
    }

    private void remove3LayerServices(List<AdnService3LayerReq> adnService3LayerReqList) {
        adnService3LayerReqList.forEach(adnService3LayerReq -> {

            AdnServiceRes adnServiceRes = adnServiceIF.removeService3Layer(adnService3LayerReq);
            if (!adnServiceRes.isSuccess()) {
                logger.error(String.format("Matrix-删除3Layer服务信息失败，错误码：%s，错误信息：%s",
                        adnServiceRes.getErrorBody().getErrorCode(),
                        adnServiceRes.getErrorBody().getMessage()));
            }

        });
    }

    public void updateService3LayerToRainbow(Service3LayerReq service3LayerReq) {

        ServiceRes serviceRes = serviceIF.updateService3Layer(service3LayerReq.getId(), service3LayerReq);
        if (!serviceRes.isSuccess()) {
            logger.error(String.format("RainBow-更新3Layer服务-同步3Layer服务失败，错误码：%s，错误信息：%s",
                    serviceRes.getErrorBody().getErrorCode(),
                    serviceRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.SERVICE_3LAYER_UPDATE_FAIL);
        }
    }

    public void removeService3LayerToRainbow(long serviceId) {

        ServiceRes serviceRes = serviceIF.removeService3Layer(serviceId);
        if (!serviceRes.isSuccess()) {
            logger.error(String.format("RainBow-删除3Layer服务-同步3Layer服务失败，错误码：%s，错误信息：%s",
                    serviceRes.getErrorBody().getErrorCode(),
                    serviceRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.SERVICE_3LAYER_REMOVE_FAIL);
        }
    }

    private void saveServiceZone(List<Long> zoneList, AdnL3ServiceEntity adnL3ServiceEntity) {
        zoneList.forEach(zoneId -> {

            AdnServiceZoneEntity adnServiceZoneEntity = new AdnServiceZoneEntity();
            adnServiceZoneEntity.setServiceId(adnL3ServiceEntity.getId());
            adnServiceZoneEntity.setServiceType(AdnServiceTypeEnum.THREE_LAYER.getType());
            adnServiceZoneEntity.setZoneId(zoneId);
            adnServiceZoneEntity.setCustomerId(adnL3ServiceEntity.getCustomerId());

            adnServiceZoneDao.saveOrUpdate(adnServiceZoneEntity);
        });
    }

    private AdnL3ServiceVo toVo(AdnL3ServiceEntity adnL3ServiceEntity) {
        AdnL3ServiceVo adnL3ServiceVo = new AdnL3ServiceVo();
        adnL3ServiceVo.setId(adnL3ServiceEntity.getId());
        adnL3ServiceVo.setServiceName(adnL3ServiceEntity.getServiceName());
        adnL3ServiceVo.setOriginalDomain(adnL3ServiceEntity.getOriginalDomain());
        adnL3ServiceVo.setSourceIp(adnL3ServiceEntity.getSourceIp());
        adnL3ServiceVo.setServiceStatus(adnL3ServiceEntity.getServiceStatus());
        adnL3ServiceVo.setServiceDomain(adnL3ServiceEntity.getServiceDomain() == null ? "" : adnL3ServiceEntity.getServiceDomain());
        adnL3ServiceVo.setAdnZoneVoList(new LinkedList<>());

        List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnL3ServiceEntity.getId(), AdnServiceTypeEnum.THREE_LAYER.getType());
        adnServiceZoneEntityList.forEach(adnServiceZoneEntity -> {
            AdnZoneVo adnZoneVo = new AdnZoneVo(adnZoneDao.findOne(adnServiceZoneEntity.getZoneId()));
            adnZoneVo.setEntryList(new LinkedList<>());
            List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.THREE_LAYER.getType(), adnL3ServiceVo.getId(), adnServiceZoneEntity.getZoneId());
            adnServiceEntryEntityList.forEach(adnServiceEntryEntity -> {
                adnZoneVo.getEntryList().add(
                        new AdnEntryVo(adnEntryDao.findOne(adnServiceEntryEntity.getEntryId()))
                );
            });
            adnL3ServiceVo.getAdnZoneVoList().add(adnZoneVo);
        });

        if (adnL3ServiceEntity.getCustomerId() != null) {
            CustomerEnterpriseEntity customerEnterpriseEntity = customerEnterpriseDao.findOne(adnL3ServiceEntity.getCustomerId());
            if (customerEnterpriseEntity != null) {
                adnL3ServiceVo.setCustomer(new CustomerEnterpriseVo(
                        customerEnterpriseEntity.getId(),
                        customerEnterpriseEntity.getName()));
            } else {
                adnL3ServiceVo.setCustomer(new CustomerEnterpriseVo(
                        0l, ""
                ));
            }
        }

        return adnL3ServiceVo;
    }
}
