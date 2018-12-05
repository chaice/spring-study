package com.ccit.service.impl;


import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.AdnCustomerZoneBean;
import com.ccit.bean.AdnZoneBean;
import com.ccit.dao.AdnCustomerZoneDao;
import com.ccit.dao.AdnEntryDao;
import com.ccit.dao.AdnServiceZoneDao;
import com.ccit.dao.AdnZoneDao;
import com.ccit.entity.AdnCustomerZoneEntity;
import com.ccit.entity.AdnEntryEntity;
import com.ccit.entity.AdnServiceZoneEntity;
import com.ccit.entity.AdnZoneEntity;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.rainbow.interfaces.ZoneIF;
import com.ccit.rest.rainbow.request.CustomerZoneReq;
import com.ccit.rest.rainbow.request.ZoneReq;
import com.ccit.rest.rainbow.response.CustomerZoneRes;
import com.ccit.rest.rainbow.response.ZoneRes;
import com.ccit.service.AdnZoneService;
import com.ccit.vo.AdnZoneVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AdnZoneServiceImpl implements AdnZoneService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AdnZoneDao adnZoneDao;

    @Autowired
    private AdnCustomerZoneDao adnCustomerZoneDao;

    @Autowired
    private AdnServiceZoneDao adnServiceZoneDao;

    @Autowired
    private AdnEntryDao adnEntryDao;

    @Autowired
    Assembler<AdnZoneBean, AdnZoneEntity, AdnZoneVo> adnZoneAssembler;

    @Autowired
    @Qualifier("rainbowZoneIF")
    private ZoneIF zoneIF;

    @Override
    public List<AdnZoneVo> listZone() {

        List<AdnZoneVo> adnZoneVos = new ArrayList<>();
        List<AdnZoneEntity> adnZoneEntities = adnZoneDao.findAll();
        adnZoneEntities.forEach(adnZoneEntity -> {
            adnZoneVos.add(adnZoneAssembler.toVo(adnZoneEntity));
        });
        return adnZoneVos;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addZone(AdnZoneBean adnZoneBean) {

        AdnZoneEntity entity = adnZoneAssembler.fromBean(adnZoneBean);
        adnZoneDao.saveOrUpdate(entity);
        updateZoneOfRainbow(entity);

    }

    @Override
    public AdnZoneVo getZone(long zoneId) {
        AdnZoneEntity entity = adnZoneDao.findOne(zoneId);
        Validator.notNull(entity, ResourceException.error(CustomerErrorConstants.ADN_ZONE_NOT_EXIST));

        return adnZoneAssembler.toVo(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeZone(long zoneId) {
        AdnZoneEntity entity = adnZoneDao.findOne(zoneId);
        Validator.notNull(entity, ResourceException.error(CustomerErrorConstants.ADN_ZONE_NOT_EXIST));
        //区域中包含入口不能删除
        List<AdnEntryEntity> entryEntities = adnEntryDao.findByZoneId(zoneId);
        Validator.isTrue(ObjectUtils.isEmpty(entryEntities), ResourceException.error(CustomerErrorConstants.ADN_ZONE_ENTRY_NOT_DELETE));

        //服务中包含区域不能删除
        AdnServiceZoneEntity adnServiceZoneEntity = adnServiceZoneDao.findByZoneId(zoneId);
        Validator.isNull(adnServiceZoneEntity, ResourceException.error(CustomerErrorConstants.ADN_ZONE_NOT_FREED));

        //用户关联的区域不能删除
        List<AdnCustomerZoneEntity> customerZoneEntities = adnCustomerZoneDao.findByZoneId(zoneId);
        Validator.isTrue(ObjectUtils.isEmpty(customerZoneEntities), ResourceException.error(CustomerErrorConstants.ADN_ZONE_CUSTOMER_ZONE_NOT_DELETE));

        //删除区域
        adnZoneDao.deleteById(zoneId);

        removeZoneOfRainbow(zoneId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyZone(AdnZoneBean adnZoneBean) {
        AdnZoneEntity entity = adnZoneDao.findOne(adnZoneBean.getId());
        Validator.notNull(entity, ResourceException.error(CustomerErrorConstants.ADN_ZONE_NOT_EXIST));

        entity.setZoneName(adnZoneBean.getZoneName());
        entity.setLongitude(adnZoneBean.getLongitude());
        entity.setLatitude(adnZoneBean.getLatitude());

        adnZoneDao.saveOrUpdate(entity);
        updateZoneOfRainbow(entity);

    }

    @Override
    public List<AdnCustomerZoneEntity> getCustomerZone(Long customerId) {

        return adnCustomerZoneDao.findByCustomerId(customerId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyCustomerZone(AdnCustomerZoneBean adnCustomerZoneBean) {
        List<Long> zoneIds = adnCustomerZoneBean.getZoneList();
        //修改时服务中存在的区域不能删除
        List<AdnServiceZoneEntity> adnServiceZoneEntities = adnServiceZoneDao.findByCustomerId(adnCustomerZoneBean.getCustomerId());

        if(!ObjectUtils.isEmpty(adnServiceZoneEntities)){
            List<Long> serviceZones = new ArrayList<>();
            adnServiceZoneEntities.forEach(adnServiceZoneEntity -> {
                serviceZones.add(adnServiceZoneEntity.getZoneId());
            });
            Validator.isTrue(zoneIds.containsAll(serviceZones), BusinessException.error(CustomerErrorConstants.ADN_ZONE_CUSTOMER_NOT_DELETE));
        }


        adnCustomerZoneDao.deleteByCustomerId(adnCustomerZoneBean.getCustomerId());

        zoneIds.forEach(zoneId -> {

            AdnCustomerZoneEntity entity = new AdnCustomerZoneEntity();
            entity.setCustomerId(adnCustomerZoneBean.getCustomerId());
            entity.setZoneId(zoneId);
            adnCustomerZoneDao.saveOrUpdate(entity);
        });
        updateCustomerZoneOfRainbow(adnCustomerZoneBean);
    }

    @Override
    public List<AdnZoneVo> getZones(long customerId) {
        List<AdnZoneVo> adnZoneVos = new ArrayList<>();
        List<AdnCustomerZoneEntity>  adnCustomerZoneEntities = adnCustomerZoneDao.findByCustomerId(customerId);

        adnCustomerZoneEntities.forEach(adnCustomerZoneEntity -> {
            adnZoneVos.add(adnZoneAssembler.toVo(adnZoneDao.findOne(adnCustomerZoneEntity.getZoneId())));
        });
        return adnZoneVos;
    }

    private void updateZoneOfRainbow(AdnZoneEntity entity) {
        ZoneRes zoneRes = zoneIF.updateZone(new ZoneReq(entity));
        if (!zoneRes.isSuccess()) {
            logger.error(String.format("Rainbow-更新区域-同步失败，错误码：%s，错误信息：%s",
                    zoneRes.getErrorBody().getErrorCode(),
                    zoneRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ADN_ZONE_SYNCHRONIZATION_FAIL);
        }

    }

    private void removeZoneOfRainbow(long id) {
        ZoneRes zoneRes = zoneIF.removeZone(id);
        if (!zoneRes.isSuccess()) {
            logger.error(String.format("Rainbow-删除区域-同步失败，错误码：%s，错误信息：%s",
                    zoneRes.getErrorBody().getErrorCode(),
                    zoneRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ADN_ZONE_SYNCHRONIZATION_FAIL);
        }
    }

    private void updateCustomerZoneOfRainbow(AdnCustomerZoneBean adnCustomerZoneBean) {
        CustomerZoneRes customerZoneRes = zoneIF.updateCustomerZone(new CustomerZoneReq(adnCustomerZoneBean));
        if (!customerZoneRes.isSuccess()) {
            logger.error(String.format("Rainbow-更新区域-同步失败，错误码：%s，错误信息：%s",
                    customerZoneRes.getErrorBody().getErrorCode(),
                    customerZoneRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ADN_ZONE_CUSTOMER_SYNCHRONIZATION_FAIL);
        }

    }
}
