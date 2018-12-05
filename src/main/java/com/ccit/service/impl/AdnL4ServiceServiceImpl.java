package com.ccit.service.impl;

import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.exception.BusinessException;
import com.ccit.bean.AdnL4ServiceBean;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.enums.AdnServiceStatus;
import com.ccit.enums.AdnServiceTypeEnum;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.rainbow.interfaces.ServiceIF;
import com.ccit.rest.rainbow.request.Service4LayerReq;
import com.ccit.rest.rainbow.response.ServiceRes;
import com.ccit.rest.service.interfaces.AdnServiceIF;
import com.ccit.rest.service.request.AdnService4LayerReq;
import com.ccit.rest.service.response.AdnServiceRes;
import com.ccit.service.AdnL4ServiceService;
import com.ccit.vo.AdnEntryVo;
import com.ccit.vo.AdnL4ServiceVo;
import com.ccit.vo.AdnZoneVo;
import com.ccit.vo.CustomerEnterpriseVo;
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
public class AdnL4ServiceServiceImpl implements AdnL4ServiceService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AdnL4ServiceDao adnL4ServiceDao;

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
    public List<AdnL4ServiceVo> list4LayerServices() {
        List<AdnL4ServiceEntity> adnL4ServiceEntityList = adnL4ServiceDao.findAll();
        List<AdnL4ServiceVo> adnL4ServiceVoList = new LinkedList<>();
        adnL4ServiceEntityList.forEach(adnL4ServiceEntity -> {
            adnL4ServiceVoList.add(toVo(adnL4ServiceEntity));
        });
        return adnL4ServiceVoList;
    }

    @Override
    public AdnL4ServiceVo get4LayerService(long serviceId) {
        AdnL4ServiceEntity adnL4ServiceEntity = adnL4ServiceDao.findOne(serviceId);
        Validator.notNull(adnL4ServiceEntity, BusinessException.error(CustomerErrorConstants.SERVICE_4LAYER_NOT_EXIST));

        return toVo(adnL4ServiceEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modify4LayerService(AdnL4ServiceBean adnL4ServiceBean) {
        AdnL4ServiceEntity adnL4ServiceEntity = adnL4ServiceDao.findOne(adnL4ServiceBean.getId());

        if (adnL4ServiceEntity == null) {
            adnL4ServiceEntity = new AdnL4ServiceEntity();
            adnL4ServiceEntity.setId(adnL4ServiceBean.getId());
            adnL4ServiceEntity.setServiceName(adnL4ServiceBean.getServiceName());
            adnL4ServiceEntity.setOriginalDomain(adnL4ServiceBean.getOriginalDomain() == null ? "" : adnL4ServiceBean.getOriginalDomain());
            adnL4ServiceEntity.setTransportProtocol(adnL4ServiceBean.getTransportProtocol());
            adnL4ServiceEntity.setSourceIp(adnL4ServiceBean.getSourceIp());
            adnL4ServiceEntity.setSourcePort(adnL4ServiceBean.getSourcePort());
            adnL4ServiceEntity.setCustomerId(adnL4ServiceBean.getCustomerId());
            adnL4ServiceEntity.setServiceStatus(adnL4ServiceBean.getServiceStatus());
            adnL4ServiceEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

            adnL4ServiceDao.saveOrUpdate(adnL4ServiceEntity);
            saveServiceZone(adnL4ServiceBean.getZoneList(), adnL4ServiceEntity);
        } else {
            //状态为审核通过
            if (AdnServiceStatus.ACTIVATED.getStatus().equals(adnL4ServiceBean.getServiceStatus())) {
                //检验区域入口是否配置
                List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnL4ServiceEntity.getId(), AdnServiceTypeEnum.FOUR_LAYER.getType());
                for (AdnServiceZoneEntity adnServiceZoneEntity : adnServiceZoneEntityList) {
                    Validator.isTrue(adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.FOUR_LAYER.getType(), adnL4ServiceEntity.getId(), adnServiceZoneEntity.getZoneId()).size() != 0, BusinessException.error(CustomerErrorConstants.SERVICE_4LAYER_SERVICE_ZONE_ENTRY_EMPTY));
                }
                //bean中的服务域名是否为空
                Validator.notNull(adnL4ServiceBean.getServiceDomain(), BusinessException.error(CustomerErrorConstants.SERVICE_4LAYER_SERVICE_DOMAIN_EMPTY));
                //状态变更,为该服务配置入口
                adnL4ServiceEntity.setServiceDomain(adnL4ServiceBean.getServiceDomain());
                adnL4ServiceEntity.setServiceStatus(AdnServiceStatus.ACTIVATED.getStatus());
                adnL4ServiceEntity.setEffectiveTime(new Timestamp(System.currentTimeMillis()));
                adnL4ServiceDao.saveOrUpdate(adnL4ServiceEntity);

                List<AdnService4LayerReq> adnService4LayerReqList = new LinkedList();
                //审核通过,下发服务信息
                for (AdnServiceZoneEntity adnServiceZoneEntity : adnServiceZoneEntityList) {
                    for (AdnServiceEntryEntity adnServiceEntryEntity : adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.FOUR_LAYER.getType(), adnL4ServiceEntity.getId(), adnServiceZoneEntity.getZoneId())) {
                        AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(adnServiceEntryEntity.getEntryId());

                        AdnService4LayerReq adnService4LayerReq = new AdnService4LayerReq();
                        adnService4LayerReq.setServiceId(adnL4ServiceEntity.getId());
                        adnService4LayerReq.setControlIp(adnEntryEntity.getServiceIp());
                        adnService4LayerReq.setTransportProtocol(adnL4ServiceEntity.getTransportProtocol());
                        adnService4LayerReq.setSourcePort(adnL4ServiceEntity.getSourcePort());
                        adnService4LayerReq.setSourceIp(adnL4ServiceEntity.getSourceIp());

                        adnService4LayerReqList.add(adnService4LayerReq);
                    }
                }
                sync4LayerServices(adnService4LayerReqList);

                //同步到rainbow
                Service4LayerReq service4LayerReq = new Service4LayerReq();
                service4LayerReq.setId(adnL4ServiceEntity.getId());
                service4LayerReq.setServiceDomain(adnL4ServiceEntity.getServiceDomain());
                service4LayerReq.setServiceStatus(adnL4ServiceEntity.getServiceStatus());
                updateService4LayerToRainbow(service4LayerReq);
            } else {
                //rainbow的请求,执行修改服务名称或者源IP
                adnL4ServiceEntity.setServiceName(adnL4ServiceBean.getServiceName());
                if (!adnL4ServiceEntity.getSourceIp().equals(adnL4ServiceBean.getSourceIp())) {
                    adnL4ServiceEntity.setSourceIp(adnL4ServiceBean.getSourceIp());
                    //源IP修改,服务状态要修改
                    adnL4ServiceEntity.setServiceStatus(AdnServiceStatus.IP_WAIT_CHECKING.getStatus());
                }
                adnL4ServiceDao.saveOrUpdate(adnL4ServiceEntity);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyAdnServiceEntry(AdnL4ServiceBean adnL4ServiceBean) {
        Long zoneId = adnL4ServiceBean.getZoneList().get(0);

        AdnL4ServiceEntity adnL4ServiceEntity = adnL4ServiceDao.findOne(adnL4ServiceBean.getId());
        Validator.notNull(adnL4ServiceEntity, BusinessException.error(CustomerErrorConstants.SERVICE_4LAYER_NOT_EXIST));

        AdnServiceZoneEntity adnServiceZoneEntity = adnServiceZoneDao.findByServiceIdAndZoneIdAndServiceType(adnL4ServiceBean.getId(), zoneId, AdnServiceTypeEnum.FOUR_LAYER.getType());
        //判断该关联是否存在
        Validator.notNull(adnServiceZoneEntity, BusinessException.error(CustomerErrorConstants.SERVICE_4LAYER_SERVICE_ZONE_NOT_EXIST));

        List<Long> removeEntryIds = new LinkedList<>();
        adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.FOUR_LAYER.getType(), adnServiceZoneEntity.getServiceId(), adnServiceZoneEntity.getZoneId()).forEach(adnServiceEntryEntity -> {
            removeEntryIds.add(adnServiceEntryEntity.getEntryId());
        });

        //删除所有的服务区域关联记录
        adnServiceEntryDao.deleteByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.FOUR_LAYER.getType(), adnL4ServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
        adnL4ServiceBean.getEntryIds().forEach(entryId -> {

            if (removeEntryIds.contains(entryId)) {
                removeEntryIds.remove(entryId);
            }

            AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);
            Validator.notNull(adnEntryEntity, BusinessException.error(CustomerErrorConstants.ADN_ENTRY_NOT_EXIST));

            AdnServiceEntryEntity adnServiceEntryEntity = new AdnServiceEntryEntity();
            adnServiceEntryEntity.setEntryId(entryId);
            adnServiceEntryEntity.setSourcePort(adnL4ServiceEntity.getSourcePort());
            adnServiceEntryEntity.setServiceId(adnL4ServiceEntity.getId());
            adnServiceEntryEntity.setTransportProtocol(adnL4ServiceEntity.getTransportProtocol());
            adnServiceEntryEntity.setZoneId(zoneId);
            adnServiceEntryEntity.setServiceType(AdnServiceTypeEnum.FOUR_LAYER.getType());

            adnServiceEntryDao.saveOrUpdate(adnServiceEntryEntity);
        });

        //服务状态为已审核时下发信息
        if (AdnServiceStatus.ACTIVATED.getStatus().equals(adnL4ServiceEntity.getServiceStatus())) {
            List<AdnService4LayerReq> removeService4LayerReqList = new LinkedList<>();
            removeEntryIds.forEach(entryId -> {
                AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);

                AdnService4LayerReq adnService4LayerReq = new AdnService4LayerReq();
                adnService4LayerReq.setServiceId(adnL4ServiceEntity.getId());
                adnService4LayerReq.setControlIp(adnEntryEntity.getServiceIp());

                removeService4LayerReqList.add(adnService4LayerReq);
            });
            remove4LayerServices(removeService4LayerReqList);

            List<AdnService4LayerReq> syncService4LayerReqList = new LinkedList<>();
            adnL4ServiceBean.getEntryIds().forEach(entryId -> {
                AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);

                AdnService4LayerReq adnService4LayerReq = new AdnService4LayerReq();
                adnService4LayerReq.setServiceId(adnL4ServiceEntity.getId());
                adnService4LayerReq.setControlIp(adnEntryEntity.getServiceIp());
                adnService4LayerReq.setTransportProtocol(adnL4ServiceEntity.getTransportProtocol());
                adnService4LayerReq.setSourcePort(adnL4ServiceEntity.getSourcePort());
                adnService4LayerReq.setSourceIp(adnL4ServiceEntity.getSourceIp());
                syncService4LayerReqList.add(adnService4LayerReq);
            });
            sync4LayerServices(syncService4LayerReqList);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void remove4LayerService(long serviceId) {
        AdnL4ServiceEntity adnL4ServiceEntity = adnL4ServiceDao.findOne(serviceId);

        if (adnL4ServiceEntity != null) {
            List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnL4ServiceEntity.getId(), AdnServiceTypeEnum.FOUR_LAYER.getType());
            adnServiceZoneEntityList.forEach(adnServiceZoneEntity -> {
                if (!AdnServiceStatus.WAIT_CHECKING.getStatus().equals(adnL4ServiceEntity.getServiceStatus())) {
                    //删除服务信息
                    List<AdnService4LayerReq> removeService4LayerReqList = new LinkedList<>();
                    List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.FOUR_LAYER.getType(), adnL4ServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
                    adnServiceEntryEntityList.forEach(adnServiceEntryEntity -> {
                        AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(adnServiceEntryEntity.getEntryId());

                        AdnService4LayerReq adnService4LayerReq = new AdnService4LayerReq();
                        adnService4LayerReq.setServiceId(adnL4ServiceEntity.getId());
                        adnService4LayerReq.setControlIp(adnEntryEntity.getServiceIp());

                        removeService4LayerReqList.add(adnService4LayerReq);
                    });
                    remove4LayerServices(removeService4LayerReqList);
                }

                //删除区域入口关联记录
                adnServiceEntryDao.deleteByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.FOUR_LAYER.getType(), adnL4ServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
            });
            //删除服务区域关联记录
            adnServiceZoneDao.deleteByServiceIdAndServiceType(serviceId, AdnServiceTypeEnum.FOUR_LAYER.getType());
            //删除服务
            adnL4ServiceDao.deleteById(serviceId);
            //删除服务流量
            performanceAdnTrafficDao.deleteByServiceIdAndServiceType(serviceId, AdnServiceTypeEnum.FOUR_LAYER.getType());
            //同步到 rainbow
            removeService4LayerToRainbow(serviceId);
        }
    }

    private void sync4LayerServices(List<AdnService4LayerReq> syncService4LayerReqList) {
        syncService4LayerReqList.forEach(adnService4LayerReq -> {

            AdnServiceRes adnServiceRes = adnServiceIF.syncService4Layer(adnService4LayerReq);
            if (!adnServiceRes.isSuccess()) {
                logger.error(String.format("Matrix-下发4Layer服务信息失败，错误码：%s，错误信息：%s",
                        adnServiceRes.getErrorBody().getErrorCode(),
                        adnServiceRes.getErrorBody().getMessage()));
            }

        });
    }

    private void remove4LayerServices(List<AdnService4LayerReq> adnService4LayerReqList) {
        adnService4LayerReqList.forEach(adnService4LayerReq -> {

            AdnServiceRes adnServiceRes = adnServiceIF.removeService4Layer(adnService4LayerReq);
            if (!adnServiceRes.isSuccess()) {
                logger.error(String.format("Matrix-删除4Layer服务信息失败，错误码：%s，错误信息：%s",
                        adnServiceRes.getErrorBody().getErrorCode(),
                        adnServiceRes.getErrorBody().getMessage()));
            }

        });
    }

    public void updateService4LayerToRainbow(Service4LayerReq service4LayerReq) {

        ServiceRes serviceRes = serviceIF.updateService4Layer(service4LayerReq.getId(), service4LayerReq);
        if (!serviceRes.isSuccess()) {
            logger.error(String.format("RainBow-更新4Layer服务-同步4Layer服务失败，错误码：%s，错误信息：%s",
                    serviceRes.getErrorBody().getErrorCode(),
                    serviceRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.SERVICE_4LAYER_UPDATE_FAIL);
        }
    }

    public void removeService4LayerToRainbow(long serviceId) {

        ServiceRes serviceRes = serviceIF.removeService4Layer(serviceId);
        if (!serviceRes.isSuccess()) {
            logger.error(String.format("RainBow-删除4Layer服务-同步4Layer服务失败，错误码：%s，错误信息：%s",
                    serviceRes.getErrorBody().getErrorCode(),
                    serviceRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.SERVICE_4LAYER_REMOVE_FAIL);
        }
    }

    private void saveServiceZone(List<Long> zoneList, AdnL4ServiceEntity adnL4ServiceEntity) {
        zoneList.forEach(zoneId -> {

            AdnServiceZoneEntity adnServiceZoneEntity = new AdnServiceZoneEntity();
            adnServiceZoneEntity.setServiceId(adnL4ServiceEntity.getId());
            adnServiceZoneEntity.setServiceType(AdnServiceTypeEnum.FOUR_LAYER.getType());
            adnServiceZoneEntity.setZoneId(zoneId);
            adnServiceZoneEntity.setCustomerId(adnL4ServiceEntity.getCustomerId());

            adnServiceZoneDao.saveOrUpdate(adnServiceZoneEntity);
        });
    }

    private AdnL4ServiceVo toVo(AdnL4ServiceEntity adnL4ServiceEntity) {
        AdnL4ServiceVo adnL4ServiceVo = new AdnL4ServiceVo();
        adnL4ServiceVo.setId(adnL4ServiceEntity.getId());
        adnL4ServiceVo.setServiceName(adnL4ServiceEntity.getServiceName());
        adnL4ServiceVo.setOriginalDomain(adnL4ServiceEntity.getOriginalDomain());
        adnL4ServiceVo.setSourceIp(adnL4ServiceEntity.getSourceIp());
        adnL4ServiceVo.setTransportProtocol(adnL4ServiceEntity.getTransportProtocol());
        adnL4ServiceVo.setSourcePort(adnL4ServiceEntity.getSourcePort());
        adnL4ServiceVo.setServiceStatus(adnL4ServiceEntity.getServiceStatus());
        adnL4ServiceVo.setServiceDomain(adnL4ServiceEntity.getServiceDomain() == null ? "" : adnL4ServiceEntity.getServiceDomain());
        adnL4ServiceVo.setAdnZoneVoList(new LinkedList<>());

        List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnL4ServiceEntity.getId(), AdnServiceTypeEnum.FOUR_LAYER.getType());
        adnServiceZoneEntityList.forEach(adnServiceZoneEntity -> {
            AdnZoneVo adnZoneVo = new AdnZoneVo(adnZoneDao.findOne(adnServiceZoneEntity.getZoneId()));
            adnZoneVo.setEntryList(new LinkedList<>());
            List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.FOUR_LAYER.getType(), adnL4ServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
            adnServiceEntryEntityList.forEach(adnServiceEntryEntity -> {
                adnZoneVo.getEntryList().add(
                        new AdnEntryVo(adnEntryDao.findOne(adnServiceEntryEntity.getEntryId()))
                );
            });
            adnL4ServiceVo.getAdnZoneVoList().add(adnZoneVo);
        });

        if (adnL4ServiceEntity.getCustomerId() != null) {
            CustomerEnterpriseEntity customerEnterpriseEntity = customerEnterpriseDao.findOne(adnL4ServiceEntity.getCustomerId());
            if (customerEnterpriseEntity != null) {
                adnL4ServiceVo.setCustomer(new CustomerEnterpriseVo(
                        customerEnterpriseEntity.getId(),
                        customerEnterpriseEntity.getName()));
            } else {
                adnL4ServiceVo.setCustomer(new CustomerEnterpriseVo(
                        0l, ""
                ));
            }
        }

        return adnL4ServiceVo;
    }
}
