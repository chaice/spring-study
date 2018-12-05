package com.ccit.service.impl;

import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.exception.BusinessException;
import com.ccit.bean.AdnHttpServiceBean;
import com.ccit.bean.AdnHttpDomainBean;
import com.ccit.vo.*;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.enums.AdnServiceStatus;
import com.ccit.enums.AdnServiceTypeEnum;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.rainbow.interfaces.ServiceIF;
import com.ccit.rest.rainbow.request.ServiceHttpReq;
import com.ccit.rest.rainbow.response.ServiceRes;
import com.ccit.rest.service.interfaces.AdnServiceIF;
import com.ccit.rest.service.request.AdnServiceHttpDomain;
import com.ccit.rest.service.request.AdnServiceHttpReq;
import com.ccit.rest.service.response.AdnServiceRes;
import com.ccit.service.AdnServiceHttpService;
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
public class AdnServiceHttpServiceImpl implements AdnServiceHttpService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AdnHttpDomainDao adnHttpDomainDao;

    @Autowired
    private AdnHttpServiceDao adnHttpServiceDao;

    @Autowired
    private AdnZoneDao adnZoneDao;

    @Autowired
    private AdnServiceZoneDao adnServiceZoneDao;

    @Autowired
    private AdnServiceEntryDao adnServiceEntryDao;

    @Autowired
    private AdnEntryDao adnEntryDao;

    @Autowired
    private PerformanceAdnTrafficDao performanceAdnTrafficDao;

    @Autowired
    private ServiceIF serviceIF;

    @Autowired
    private AdnServiceIF adnServiceIF;

    @Autowired
    private CustomerEnterpriseDao customerEnterpriseDao;

    @Override
    public List<AdnHttpServiceVo> listHttpServices() {
        List<AdnHttpServiceEntity> adnHttpServiceEntityList = adnHttpServiceDao.findAll();
        List<AdnHttpServiceVo> adnHttpServiceVoList = new LinkedList<>();
        adnHttpServiceEntityList.forEach(adnHttpServiceEntity -> {
            adnHttpServiceVoList.add(toVo(adnHttpServiceEntity));
        });
        return adnHttpServiceVoList;
    }

    @Override
    public AdnHttpServiceVo getHttpService(long serviceId) {
        AdnHttpServiceEntity adnHttpServiceEntity = adnHttpServiceDao.findOne(serviceId);
        Validator.notNull(adnHttpServiceEntity, BusinessException.error(CustomerErrorConstants.SERVICE_HTTP_NOT_EXIST));

        return toVo(adnHttpServiceEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyHttpService(AdnHttpServiceBean adnHttpServiceBean) {
        AdnHttpServiceEntity adnHttpServiceEntity = adnHttpServiceDao.findOne(adnHttpServiceBean.getId());

        if (adnHttpServiceEntity == null) {
            adnHttpServiceEntity = new AdnHttpServiceEntity();
            adnHttpServiceEntity.setId(adnHttpServiceBean.getId());
            adnHttpServiceEntity.setServiceName(adnHttpServiceBean.getServiceName());
            adnHttpServiceEntity.setServiceType(adnHttpServiceBean.getServiceType());
            adnHttpServiceEntity.setSourcePort(adnHttpServiceBean.getSourcePort());
            adnHttpServiceEntity.setServiceStatus(adnHttpServiceBean.getServiceStatus());
            adnHttpServiceEntity.setCustomerId(adnHttpServiceBean.getCustomerId());
            adnHttpServiceEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

            adnHttpServiceDao.saveOrUpdate(adnHttpServiceEntity);

            saveServiceZone(adnHttpServiceBean.getZoneList(), adnHttpServiceEntity);

            adnHttpServiceBean.getAdnServiceHttpDomainReqList().forEach(adnHttpDomainBean -> {
                AdnHttpDomainEntity adnHttpDomainEntity = new AdnHttpDomainEntity();
                adnHttpDomainEntity.setId(adnHttpDomainBean.getId());
                adnHttpDomainEntity.setSourceIp(adnHttpDomainBean.getSourceIp());
                adnHttpDomainEntity.setSecondLevelDomain(adnHttpDomainBean.getSecondLevelDomain());
                adnHttpDomainEntity.setServiceId(adnHttpDomainBean.getServiceId());
                adnHttpDomainEntity.setCustomerId(adnHttpDomainBean.getCustomerId());
                adnHttpDomainDao.saveOrUpdate(adnHttpDomainEntity);
            });
        } else {
            //状态为审核通过
            if (AdnServiceStatus.ACTIVATED.getStatus().equals(adnHttpServiceBean.getServiceStatus())) {
                //检验区域入口是否配置
                List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnHttpServiceEntity.getId(), AdnServiceTypeEnum.HTTP.getType());
                for (AdnServiceZoneEntity adnServiceZoneEntity : adnServiceZoneEntityList) {
                    Validator.isTrue(adnServiceEntryDao.findByServiceTypeServiceIdZoneId(adnHttpServiceEntity.getServiceType(), adnHttpServiceEntity.getId(), adnServiceZoneEntity.getZoneId()).size() != 0, BusinessException.error(CustomerErrorConstants.SERVICE_HTTP_SERVICE_ZONE_ENTRY_EMPTY));
                }
                //bean中的服务域名是否为空
                Validator.notNull(adnHttpServiceBean.getServiceDomain(), BusinessException.error(CustomerErrorConstants.SERVICE_HTTP_SERVICE_DOMAIN_EMPTY));

                //状态变更
                adnHttpServiceEntity.setServiceDomain(adnHttpServiceBean.getServiceDomain());
                adnHttpServiceEntity.setServiceStatus(AdnServiceStatus.ACTIVATED.getStatus());
                adnHttpServiceEntity.setEffectiveTime(new Timestamp(System.currentTimeMillis()));
                adnHttpServiceDao.saveOrUpdate(adnHttpServiceEntity);

                //服务信息下发
                List<AdnServiceHttpReq> adnServiceHttpReqList = new LinkedList();
                //审核通过,下发服务信息
                for (AdnServiceZoneEntity adnServiceZoneEntity : adnServiceZoneEntityList) {
                    for (AdnServiceEntryEntity adnServiceEntryEntity : adnServiceEntryDao.findByServiceTypeServiceIdZoneId(adnHttpServiceEntity.getServiceType(), adnHttpServiceEntity.getId(), adnServiceZoneEntity.getZoneId())) {
                        AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(adnServiceEntryEntity.getEntryId());

                        AdnServiceHttpReq adnServiceHttpReq = new AdnServiceHttpReq(adnEntryEntity.getServiceIp(), adnHttpServiceEntity.getServiceType(), adnHttpServiceEntity.getSourcePort());
                        adnServiceHttpReq.setServiceId(adnHttpServiceEntity.getId());

                        List<AdnHttpDomainEntity> adnHttpDomainEntityList = adnHttpDomainDao.findByServiceId(adnHttpServiceEntity.getId());
                        adnHttpDomainEntityList.forEach(adnServiceHttpDomainEntity -> {
                            adnServiceHttpReq.getHttpDomainList().add(
                                    new AdnServiceHttpDomain(adnServiceHttpDomainEntity.getSourceIp(), adnServiceHttpDomainEntity.getSecondLevelDomain())
                            );
                        });
                        adnServiceHttpReqList.add(adnServiceHttpReq);
                    }
                }
                syncHttpServices(adnServiceHttpReqList);


                //同步到rainbow
                ServiceHttpReq serviceHttpReq = new ServiceHttpReq();
                serviceHttpReq.setId(adnHttpServiceEntity.getId());
                serviceHttpReq.setServiceDomain(adnHttpServiceEntity.getServiceDomain());
                serviceHttpReq.setServiceStatus(AdnServiceStatus.ACTIVATED.getStatus());
                updateServiceHttpToRainbow(serviceHttpReq);
            } else {
                //rainbow的请求,执行修改服务名称
                adnHttpServiceEntity.setServiceName(adnHttpServiceBean.getServiceName());
                adnHttpServiceDao.saveOrUpdate(adnHttpServiceEntity);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeHttpService(long serviceId) {
        AdnHttpServiceEntity adnHttpServiceEntity = adnHttpServiceDao.findOne(serviceId);

        if (adnHttpServiceEntity != null) {
            List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnHttpServiceEntity.getId(), AdnServiceTypeEnum.HTTP.getType());
            adnServiceZoneEntityList.forEach(adnServiceZoneEntity -> {
                //删除服务信息
                List<AdnServiceHttpReq> removeServiceHttpReqList = new LinkedList<>();
                List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.HTTP.getType(), adnHttpServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
                adnServiceEntryEntityList.forEach(adnServiceEntryEntity -> {
                    AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(adnServiceEntryEntity.getEntryId());

                    AdnServiceHttpReq adnServiceHttpReq = new AdnServiceHttpReq(adnEntryEntity.getServiceIp(), adnHttpServiceEntity.getServiceType(), adnHttpServiceEntity.getSourcePort());
                    adnServiceHttpReq.setServiceId(adnHttpServiceEntity.getId());

                    removeServiceHttpReqList.add(adnServiceHttpReq);
                });
                removeHttpServices(removeServiceHttpReqList);

                //删除服务区域入口关联记录
                adnServiceEntryDao.deleteByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.HTTP.getType(), adnHttpServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
            });
            //删除服务关联域名
            adnHttpDomainDao.deleteByServiceId(serviceId);
            //删除服务区域关联记录
            adnServiceZoneDao.deleteByServiceIdAndServiceType(serviceId, AdnServiceTypeEnum.HTTP.getType());
            //删除服务
            adnHttpServiceDao.deleteById(serviceId);
            //删除服务流量
            performanceAdnTrafficDao.deleteByServiceIdAndServiceType(serviceId, AdnServiceTypeEnum.HTTP.getType());

            //同步到rainbow
            removeServiceHttpToRainbow(serviceId);
        }
    }

    @Override
    public List<AdnHttpDomainVo> listHttpServiceDomain(long serviceId) {
        AdnHttpServiceEntity adnHttpServiceEntity = adnHttpServiceDao.findOne(serviceId);
        Validator.notNull(adnHttpServiceEntity, BusinessException.error(CustomerErrorConstants.SERVICE_HTTP_NOT_EXIST));
        List<AdnHttpDomainEntity> adnHttpDomainEntityList = adnHttpDomainDao.findByServiceId(serviceId);
        List<AdnHttpDomainVo> adnHttpDomainVoList = new LinkedList<>();
        adnHttpDomainEntityList.forEach(adnServiceHttpDomainEntity -> {
            adnHttpDomainVoList.add(
                    new AdnHttpDomainVo(adnServiceHttpDomainEntity)
            );
        });
        return adnHttpDomainVoList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyHttpServiceDomain(AdnHttpDomainBean adnHttpDomainBean) {
        AdnHttpDomainEntity adnHttpDomainEntity = adnHttpDomainDao.findOne(adnHttpDomainBean.getId());

        AdnHttpServiceEntity adnHttpServiceEntity = adnHttpServiceDao.findOne(adnHttpDomainBean.getServiceId());
        if (adnHttpServiceEntity != null) {
            if (adnHttpDomainEntity == null) {
                adnHttpDomainEntity = new AdnHttpDomainEntity();
                adnHttpDomainEntity.setId(adnHttpDomainBean.getId());
                adnHttpDomainEntity.setServiceId(adnHttpDomainBean.getServiceId());
                adnHttpDomainEntity.setCustomerId(adnHttpDomainBean.getCustomerId());
                adnHttpDomainEntity.setSecondLevelDomain(adnHttpDomainBean.getSecondLevelDomain());
                adnHttpDomainEntity.setSourceIp(adnHttpDomainBean.getSourceIp());
                adnHttpDomainDao.saveOrUpdate(adnHttpDomainEntity);
            } else {
                adnHttpDomainEntity.setSourceIp(adnHttpDomainBean.getSourceIp());
                adnHttpDomainDao.saveOrUpdate(adnHttpDomainEntity);
            }
            //源ip修改服务状态改变待定
            adnHttpServiceEntity.setServiceStatus(AdnServiceStatus.IP_WAIT_CHECKING.getStatus());
            adnHttpServiceDao.saveOrUpdate(adnHttpServiceEntity);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyAdnServiceEntry(AdnHttpServiceBean adnHttpServiceBean) {
        Long zoneId = adnHttpServiceBean.getZoneList().get(0);

        AdnHttpServiceEntity adnHttpServiceEntity = adnHttpServiceDao.findOne(adnHttpServiceBean.getId());
        Validator.notNull(adnHttpServiceEntity, BusinessException.error(CustomerErrorConstants.SERVICE_HTTP_NOT_EXIST));

        AdnServiceZoneEntity adnServiceZoneEntity = adnServiceZoneDao.findByServiceIdAndZoneIdAndServiceType(adnHttpServiceBean.getId(), zoneId,AdnServiceTypeEnum.HTTP.getType());
        //判断该关联是否存在
        Validator.notNull(adnServiceZoneEntity, BusinessException.error(CustomerErrorConstants.SERVICE_HTTP_SERVICE_ZONE_NOT_EXIST));

        List<Long> removeEntryIds = new LinkedList<>();
        adnServiceEntryDao.findByServiceTypeServiceIdZoneId(adnHttpServiceEntity.getServiceType(), adnServiceZoneEntity.getServiceId(), adnServiceZoneEntity.getZoneId()).forEach(adnServiceEntryEntity -> {
            removeEntryIds.add(adnServiceEntryEntity.getEntryId());
        });

        adnServiceEntryDao.deleteByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.HTTP.getType(), adnHttpServiceEntity.getId(), zoneId);
        adnHttpServiceBean.getEntryIds().forEach(entryId -> {

            AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);
            Validator.notNull(adnEntryEntity, BusinessException.error(CustomerErrorConstants.ADN_ENTRY_NOT_EXIST));

            AdnServiceEntryEntity adnServiceEntryEntity = new AdnServiceEntryEntity();
            adnServiceEntryEntity.setEntryId(entryId);
            adnServiceEntryEntity.setServiceId(adnHttpServiceEntity.getId());
            adnServiceEntryEntity.setZoneId(zoneId);
            adnServiceEntryEntity.setSourcePort(adnHttpServiceEntity.getSourcePort());
            adnServiceEntryEntity.setServiceType(adnHttpServiceEntity.getServiceType());

            adnServiceEntryDao.saveOrUpdate(adnServiceEntryEntity);
        });

        //服务状态为已审核时下发信息
        if (AdnServiceStatus.ACTIVATED.getStatus().equals(adnHttpServiceEntity.getServiceStatus())) {
            List<AdnServiceHttpReq> removeServiceHttpReqList = new LinkedList<>();
            removeEntryIds.forEach(entryId -> {
                AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);

                AdnServiceHttpReq adnServiceHttpReq = new AdnServiceHttpReq(adnEntryEntity.getServiceIp(), adnHttpServiceEntity.getServiceType(), adnHttpServiceEntity.getSourcePort());
                adnServiceHttpReq.setServiceId(adnHttpServiceEntity.getId());

                removeServiceHttpReqList.add(adnServiceHttpReq);
            });
            removeHttpServices(removeServiceHttpReqList);

            List<AdnServiceHttpReq> addServiceHttpReqList = new LinkedList<>();
            adnHttpServiceBean.getEntryIds().forEach(entryId -> {
                AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);

                AdnServiceHttpReq adnServiceHttpReq = new AdnServiceHttpReq(adnEntryEntity.getServiceIp(), adnHttpServiceEntity.getServiceType(), adnHttpServiceEntity.getSourcePort());
                adnServiceHttpReq.setServiceId(adnHttpServiceEntity.getId());

                List<AdnHttpDomainEntity> adnHttpDomainEntityList = adnHttpDomainDao.findByServiceId(adnHttpServiceEntity.getId());
                adnHttpDomainEntityList.forEach(adnServiceHttpDomainEntity -> {
                    adnServiceHttpReq.getHttpDomainList().add(
                            new AdnServiceHttpDomain(adnServiceHttpDomainEntity.getSourceIp(), adnServiceHttpDomainEntity.getSecondLevelDomain())
                    );
                });

                addServiceHttpReqList.add(adnServiceHttpReq);
            });
            syncHttpServices(addServiceHttpReqList);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeHttpServiceDomain(long serviceId, long domainId) {
        AdnHttpServiceEntity adnHttpServiceEntity = adnHttpServiceDao.findOne(serviceId);

        AdnHttpDomainEntity adnHttpDomainEntity = adnHttpDomainDao.findOne(domainId);
        if (adnHttpDomainEntity != null && adnHttpServiceEntity != null) {
            adnHttpDomainDao.deleteById(domainId);

            adnHttpServiceEntity.setServiceStatus(AdnServiceStatus.IP_WAIT_CHECKING.getStatus());
            adnHttpServiceDao.saveOrUpdate(adnHttpServiceEntity);
        }
    }

    private void syncHttpServices(List<AdnServiceHttpReq> adnServiceHttpReqList) {
        adnServiceHttpReqList.forEach(adnServiceHttpReq -> {

            AdnServiceRes adnServiceRes = adnServiceIF.addServiceHttp(adnServiceHttpReq);
            if (!adnServiceRes.isSuccess()) {
                logger.error(String.format("Matrix-下发7Layer服务信息失败，错误码：%s，错误信息：%s",
                        adnServiceRes.getErrorBody().getErrorCode(),
                        adnServiceRes.getErrorBody().getMessage()));
            }

        });
    }

    private void removeHttpServices(List<AdnServiceHttpReq> adnServiceHttpReqList) {
        adnServiceHttpReqList.forEach(adnServiceHttpReq -> {

            AdnServiceRes adnServiceRes = adnServiceIF.removeServiceHttp(adnServiceHttpReq);
            if (!adnServiceRes.isSuccess()) {
                logger.error(String.format("Matrix-删除7Layer服务信息失败，错误码：%s，错误信息：%s",
                        adnServiceRes.getErrorBody().getErrorCode(),
                        adnServiceRes.getErrorBody().getMessage()));
            }

        });
    }

    public void updateServiceHttpToRainbow(ServiceHttpReq serviceHttpReq) {

        ServiceRes serviceRes = serviceIF.updateServiceHttp(serviceHttpReq.getId(), serviceHttpReq);
        if (!serviceRes.isSuccess()) {
            logger.error(String.format("RainBow-更新Http服务-同步Http服务失败，错误码：%s，错误信息：%s",
                    serviceRes.getErrorBody().getErrorCode(),
                    serviceRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.SERVICE_HTTP_UPDATE_FAIL);
        }
    }

    public void removeServiceHttpToRainbow(long serviceId) {

        ServiceRes serviceRes = serviceIF.removeServiceHttp(serviceId);
        if (!serviceRes.isSuccess()) {
            logger.error(String.format("RainBow-删除Http服务-同步Http服务失败，错误码：%s，错误信息：%s",
                    serviceRes.getErrorBody().getErrorCode(),
                    serviceRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.SERVICE_HTTP_REMOVE_FAIL);
        }
    }

    private void saveServiceZone(List<Long> zoneList, AdnHttpServiceEntity adnHttpServiceEntity) {
        zoneList.forEach(zoneId -> {
            AdnServiceZoneEntity adnServiceZoneEntity = new AdnServiceZoneEntity();
            adnServiceZoneEntity.setServiceId(adnHttpServiceEntity.getId());
            adnServiceZoneEntity.setServiceType(AdnServiceTypeEnum.HTTP.getType());
            adnServiceZoneEntity.setZoneId(zoneId);
            adnServiceZoneEntity.setCustomerId(adnHttpServiceEntity.getCustomerId());

            adnServiceZoneDao.saveOrUpdate(adnServiceZoneEntity);
        });
    }

    private AdnHttpServiceVo toVo(AdnHttpServiceEntity adnHttpServiceEntity) {
        AdnHttpServiceVo adnHttpServiceVo = new AdnHttpServiceVo();
        adnHttpServiceVo.setId(adnHttpServiceEntity.getId());
        adnHttpServiceVo.setServiceName(adnHttpServiceEntity.getServiceName());
        adnHttpServiceVo.setServiceType(adnHttpServiceEntity.getServiceType());
        adnHttpServiceVo.setOriginalDomain(adnHttpServiceEntity.getOriginalDomain());
        adnHttpServiceVo.setSourcePort(adnHttpServiceEntity.getSourcePort());
        adnHttpServiceVo.setServiceStatus(adnHttpServiceEntity.getServiceStatus());
        adnHttpServiceVo.setServiceDomain(adnHttpServiceEntity.getServiceDomain() == null ? "" : adnHttpServiceEntity.getServiceDomain());

        adnHttpServiceVo.setAdnZoneVoList(new LinkedList<>());
        List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByServiceIdAndServiceType(adnHttpServiceEntity.getId(), AdnServiceTypeEnum.HTTP.getType());
        adnServiceZoneEntityList.forEach(adnServiceZoneEntity -> {
            AdnZoneVo adnZoneVo = new AdnZoneVo(adnZoneDao.findOne(adnServiceZoneEntity.getZoneId()));
            adnZoneVo.setEntryList(new LinkedList<>());
            List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByServiceTypeServiceIdZoneId(adnHttpServiceEntity.getServiceType(), adnHttpServiceEntity.getId(), adnServiceZoneEntity.getZoneId());
            adnServiceEntryEntityList.forEach(adnServiceEntryEntity -> {
                adnZoneVo.getEntryList().add(
                        new AdnEntryVo(adnEntryDao.findOne(adnServiceEntryEntity.getEntryId()))
                );
            });
            adnHttpServiceVo.getAdnZoneVoList().add(adnZoneVo);
        });

        adnHttpServiceVo.setAdnHttpDomainVoList(new LinkedList<>());
        //获取该服务关联的三级域名
        adnHttpDomainDao.findByServiceId(adnHttpServiceEntity.getId()).forEach(adnServiceHttpDomainEntity -> {
            adnHttpServiceVo.getAdnHttpDomainVoList().add(
                    new AdnHttpDomainVo(adnServiceHttpDomainEntity)
            );
        });

        if (adnHttpServiceEntity.getCustomerId() != null) {
            CustomerEnterpriseEntity customerEnterpriseEntity = customerEnterpriseDao.findOne(adnHttpServiceEntity.getCustomerId());
            if (customerEnterpriseEntity != null) {
                adnHttpServiceVo.setCustomer(new CustomerEnterpriseVo(
                        customerEnterpriseEntity.getId(),
                        customerEnterpriseEntity.getName()));
            } else {
                adnHttpServiceVo.setCustomer(new CustomerEnterpriseVo(
                        0l, ""
                ));
            }
        }

        return adnHttpServiceVo;
    }
}
