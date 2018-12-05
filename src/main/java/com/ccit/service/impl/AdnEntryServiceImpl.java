package com.ccit.service.impl;

import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.exception.BusinessException;
import com.ccit.bean.AdnEntryBean;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.enums.AdnServiceTypeEnum;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.service.AdnEntryService;
import com.ccit.service.AdnZoneService;
import com.ccit.util.StringUtils;
import com.ccit.vo.AdnEntryVo;
import com.ccit.vo.AdnZoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AdnEntryServiceImpl implements AdnEntryService {

    @Autowired
    private AdnEntryDao adnEntryDao;

    @Autowired
    private AdnZoneService adnZoneService;

    @Autowired
    private AdnServiceEntryDao adnServiceEntryDao;

    @Autowired
    private AdnServiceZoneDao adnServiceZoneDao;

    @Autowired
    private AdnL3ServiceDao adnL3ServiceDao;

    @Autowired
    private AdnL4ServiceDao adnL4ServiceDao;

    @Autowired
    private AdnHttpServiceDao adnHttpServiceDao;

    @Override
    public List<AdnEntryVo> listEntry() {
        List<AdnEntryEntity> adnEntryEntityList = adnEntryDao.findAll();
        List<AdnEntryVo> adnEntryVoList = new LinkedList<>();
        adnEntryEntityList.forEach(adnEntryEntity -> {
            adnEntryVoList.add(toVo(adnEntryEntity));
        });

        return adnEntryVoList;
    }

    /**
     * 区域入口可用列表
     *
     * @param entryType
     * @param serviceId
     * @param zoneId
     * @return
     */
    @Override
    public List<AdnEntryVo> listUsableEntry(String entryType, long serviceId, long zoneId) {
        List<AdnEntryVo> adnEntryVoList = new LinkedList<>();

        List<AdnEntryEntity> adnEntryEntityList = adnEntryDao.findByZoneIdType(zoneId, entryType);

        //区域可选入口,针对4层处理(同一端口,只能使用一次)
        if (AdnServiceTypeEnum.FOUR_LAYER.getType().equals(entryType)) {
            AdnL4ServiceEntity adnL4ServiceEntity = adnL4ServiceDao.findOne(serviceId);
            if (adnL4ServiceEntity == null) {
                return adnEntryVoList;
            }

            AdnServiceZoneEntity adnServiceZoneEntity = adnServiceZoneDao.findByServiceIdAndZoneIdAndServiceType(serviceId, zoneId, AdnServiceTypeEnum.FOUR_LAYER.getType());
            if (adnServiceZoneEntity == null) {
                return adnEntryVoList;
            }

            for (AdnEntryEntity adnEntryEntity : adnEntryEntityList) {
                List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByEntryIdSourcePortTransportProtocol(adnEntryEntity.getId(), adnL4ServiceEntity.getSourcePort(), adnL4ServiceEntity.getTransportProtocol());
                if (adnServiceEntryEntityList != null && !adnServiceEntryEntityList.isEmpty()) {
                    if (adnServiceEntryEntityList.get(0).getServiceId().equals(adnL4ServiceEntity.getId())) {
                        adnEntryVoList.add(toVo(adnEntryEntity));
                    }
                } else {
                    boolean flag = true;
                    //服务Ip相同的入口的某一端口只能是一个服务
                    for (AdnEntryEntity entryEntity : adnEntryDao.findByServiceIp(adnEntryEntity.getServiceIp())) {
                        if (!adnServiceEntryDao.findByEntryIdSourcePort(entryEntity.getId(), adnL4ServiceEntity.getSourcePort()).isEmpty()) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        adnEntryVoList.add(toVo(adnEntryEntity));
                    }
                }
            }
        } else if (AdnServiceTypeEnum.HTTP.getType().equals(entryType)) {
            //针对7层服务入口选择,入口对应的某一端口只能是http或https
            AdnHttpServiceEntity adnHttpServiceEntity = adnHttpServiceDao.findOne(serviceId);
            if (adnHttpServiceEntity == null) {
                return adnEntryVoList;
            }

            AdnServiceZoneEntity adnServiceZoneEntity = adnServiceZoneDao.findByServiceIdAndZoneIdAndServiceType(serviceId, zoneId, AdnServiceTypeEnum.HTTP.getType());
            if (adnServiceZoneEntity == null) {
                return adnEntryVoList;
            }

            for (AdnEntryEntity adnEntryEntity : adnEntryEntityList) {
                List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByEntryIdSourcePort(adnEntryEntity.getId(), adnHttpServiceEntity.getSourcePort());
                if (adnServiceEntryEntityList != null && !adnServiceEntryEntityList.isEmpty()) {
                    if (adnServiceEntryEntityList.get(0).getServiceType().equals(adnHttpServiceEntity.getServiceType())) {
                        adnEntryVoList.add(toVo(adnEntryEntity));
                    }
                } else {
                    //服务Ip相同的入口某一端口只能是一种服务
                    boolean flag = true;
                    //服务Ip相同的入口的某一端口只能是一种服务
                    for (AdnEntryEntity entryEntity : adnEntryDao.findByServiceIp(adnEntryEntity.getServiceIp())) {
                        if (!adnServiceEntryDao.findByEntryIdSourcePort(entryEntity.getId(), adnHttpServiceEntity.getSourcePort()).isEmpty()) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        adnEntryVoList.add(toVo(adnEntryEntity));
                    }
                }
            }
        } else if (AdnServiceTypeEnum.THREE_LAYER.getType().equals(entryType)) {
            AdnL3ServiceEntity adnL3ServiceEntity = adnL3ServiceDao.findOne(serviceId);
            if (adnL3ServiceEntity == null) {
                return adnEntryVoList;
            }

            AdnServiceZoneEntity adnServiceZoneEntity = adnServiceZoneDao.findByServiceIdAndZoneIdAndServiceType(serviceId, zoneId, AdnServiceTypeEnum.THREE_LAYER.getType());
            if (adnServiceZoneEntity == null) {
                return adnEntryVoList;
            }

            for (AdnEntryEntity adnEntryEntity : adnEntryEntityList) {
                List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByEntryId(adnEntryEntity.getId());
                if (adnServiceEntryEntityList != null && !adnServiceEntryEntityList.isEmpty()) {
                    if (adnServiceEntryEntityList.get(0).getServiceId().equals(adnL3ServiceEntity.getId())) {
                        adnEntryVoList.add(toVo(adnEntryEntity));
                    }
                } else {
                    adnEntryVoList.add(toVo(adnEntryEntity));
                }
            }
        }
        return adnEntryVoList;
    }

    @Override
    public AdnEntryVo getEntry(long entryId) {
        AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);
        Validator.notNull(adnEntryEntity, BusinessException.error(CustomerErrorConstants.ADN_ENTRY_NOT_EXIST));

        return toVo(adnEntryEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createEntry(AdnEntryBean adnEntryBean) {
        //判断入口合法性
        assertAdnEntryValid(adnEntryBean, null);

        AdnEntryEntity adnEntryEntity = new AdnEntryEntity();
        adnEntryEntity.setEntryName(adnEntryBean.getEntryName());
        adnEntryEntity.setEntryType(adnEntryBean.getEntryType());
        adnEntryEntity.setServiceIp(adnEntryBean.getServerIp());
        adnEntryEntity.setZoneId(adnEntryBean.getZoneId());
        if (!StringUtils.isNull(adnEntryBean.getControlIp())) {
            adnEntryEntity.setControlIp(adnEntryBean.getControlIp());
        }

        adnEntryDao.saveOrUpdate(adnEntryEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyEntry(AdnEntryBean adnEntryBean) {
        AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(adnEntryBean.getId());
        Validator.notNull(adnEntryEntity, BusinessException.error(CustomerErrorConstants.ADN_ENTRY_NOT_EXIST));

        //判断入口合法性
        assertAdnEntryValid(adnEntryBean, adnEntryEntity);

        adnEntryEntity.setEntryName(adnEntryBean.getEntryName());
        adnEntryDao.saveOrUpdate(adnEntryEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeEntry(long entryId) {
        List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByEntryId(entryId);
        Validator.isTrue((adnServiceEntryEntityList.isEmpty()), BusinessException.error(CustomerErrorConstants.ADN_ENTRY_NOT_FREED));

        AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(entryId);
        if (adnEntryEntity != null) {
            adnEntryDao.delete(adnEntryEntity);
        }
    }

    private void assertAdnEntryValid(AdnEntryBean adnEntryBean, AdnEntryEntity adnEntryEntity) {
        if (adnEntryEntity != null) {
            if (!adnEntryBean.getEntryName().equals(adnEntryEntity.getEntryName())) {
                Validator.isNull(adnEntryDao.findByEntryName(adnEntryBean.getEntryName()), BusinessException.error(CustomerErrorConstants.ADN_ENTRY_ENTRY_NAME_REPEAT));
            }
//            if (AdnServiceTypeEnum.THREE_LAYER.getType().equals(adnEntryEntity.getEntryType()) && !adnEntryBean.getServerIp().equals(adnEntryEntity.getControlIp())) {
//                Validator.isTrue(adnEntryDao.findByServiceIpAndControlIp(adnEntryBean.getServerIp(), adnEntryBean.getControlIp()).isEmpty(), error(CustomerErrorConstants.ADN_ENTRY_SERVER_IP_EXIST));
//            }
//            if (!AdnServiceTypeEnum.THREE_LAYER.getType().equals(adnEntryEntity.getEntryType()) && !adnEntryBean.getServerIp().equals(adnEntryEntity.getControlIp())) {
//                Validator.isNull(adnEntryDao.findByServerIpAndEntryType(adnEntryBean.getServerIp(), adnEntryEntity.getEntryType()), error(CustomerErrorConstants.ADN_ENTRY_SERVER_IP_EXIST));
//            }
        } else {
            Validator.isNull(adnEntryDao.findByEntryName(adnEntryBean.getEntryName()), BusinessException.error(CustomerErrorConstants.ADN_ENTRY_ENTRY_NAME_REPEAT));
            //入口ip对于三层只能使用一次
            if (AdnServiceTypeEnum.THREE_LAYER.getType().equals(adnEntryBean.getEntryType())) {
                Validator.isTrue(adnEntryDao.findByServiceIpAndControlIp(adnEntryBean.getServerIp(), adnEntryBean.getControlIp()).isEmpty(), BusinessException.error(CustomerErrorConstants.ADN_ENTRY_SERVER_IP_EXIST));
            } else {
                Validator.isNull(adnEntryDao.findByServerIpAndEntryType(adnEntryBean.getServerIp(), adnEntryBean.getEntryType()), BusinessException.error(CustomerErrorConstants.ADN_ENTRY_SERVER_IP_EXIST));
            }
        }
    }

    private AdnEntryVo toVo(AdnEntryEntity adnEntryEntity) {
        AdnEntryVo adnEntryVo = new AdnEntryVo();
        adnEntryVo.setId(adnEntryEntity.getId());
        adnEntryVo.setEntryName(adnEntryEntity.getEntryName());
        adnEntryVo.setEntryType(adnEntryEntity.getEntryType());
        adnEntryVo.setServerIp(adnEntryEntity.getServiceIp());
        if (!StringUtils.isNull(adnEntryEntity.getControlIp())) {
            adnEntryVo.setControlIp(adnEntryEntity.getControlIp());
        }

        AdnZoneVo adnZoneVo = adnZoneService.getZone(adnEntryEntity.getZoneId());
        adnEntryVo.setAdnZoneVo(adnZoneVo);

        return adnEntryVo;
    }
}
