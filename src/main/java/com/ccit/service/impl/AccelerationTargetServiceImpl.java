package com.ccit.service.impl;

import com.ccit.assembler.AccelerationTargetAssembler;
import com.ccit.exception.ResourceException;
import com.ccit.bean.AccelerationTargetBean;
import com.ccit.dao.AccelerationTargetDao;
import com.ccit.entity.AccelerationTargetEntity;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.service.AccelerationTargetService;
import com.ccit.service.GlobalDictionaryService;
import com.ccit.service.ResourceService;
import com.ccit.util.IPv4Utils;
import com.ccit.util.StringUtils;
import com.ccit.vo.AccelerationTargetVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AccelerationTargetServiceImpl implements AccelerationTargetService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AccelerationTargetDao accelerationTargetDao;

    @Autowired
    private AccelerationTargetAssembler accelerationTargetAssembler;

    @Autowired
    private GlobalDictionaryService dictionaryService;

    @Autowired
    private ResourceService resourceService;

    private final String ACCELERATION_TARGET_EXPIRED_HOURS = "acceleration.target.expired.hours";

    private final String DEFAULT_IPV4_PREFIX = "default.ipv4.prefix";

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<AccelerationTargetVo> listAccelerationTarget() {
        Timestamp expired = new Timestamp(
                System.currentTimeMillis() - TimeUnit.HOURS.toMillis(
                        Long.parseLong(
                                dictionaryService.fromKey(ACCELERATION_TARGET_EXPIRED_HOURS)
                        )
                )
        );
        accelerationTargetDao.deleteByExpired(expired);

        List<AccelerationTargetEntity> accelerationTargetEntities = accelerationTargetDao.findAll();
        List<AccelerationTargetVo> accelerationTargetVos = accelerationTargetAssembler.toVos(accelerationTargetEntities);
        return accelerationTargetVos;
    }


    public AccelerationTargetVo getById(long id) {
        AccelerationTargetEntity accelerationTargetEntity = accelerationTargetDao.findOne(id);
        Validator.notNull(accelerationTargetEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_TARGET_NOT_EXIST));

        return accelerationTargetAssembler.toVo(accelerationTargetEntity);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, readOnly = false)
    public void updateAccelerationTargets(List<AccelerationTargetBean> accelerationTargetBeanList) {
        Hashtable<String, AccelerationTargetBean> accelerationTargetMap = new Hashtable<>();
        String defaultIPv4Prefix = dictionaryService.fromKey(DEFAULT_IPV4_PREFIX);
        for (AccelerationTargetBean accelerationTargetBean : accelerationTargetBeanList) {
            accelerationTargetBean.setCidr(IPv4Utils.transferCIDR(accelerationTargetBean.getCidr().split("/")[0], defaultIPv4Prefix));
            accelerationTargetMap.put(accelerationTargetBean.getCidr(), accelerationTargetBean);
        }

        for (AccelerationTargetBean accelerationTargetBean : accelerationTargetMap.values()) {
            AccelerationTargetEntity accelerationTargetEntity = accelerationTargetDao.findByCIDR(accelerationTargetBean.getCidr());

            if (accelerationTargetEntity == null) {
                accelerationTargetEntity = accelerationTargetAssembler.fromBean(accelerationTargetBean);
                accelerationTargetDao.saveOrUpdate(accelerationTargetEntity);
            } else {
                updateAccelerationTarget(accelerationTargetBean);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateAccelerationTarget(AccelerationTargetBean accelerationTargetBean) {
        AccelerationTargetEntity accelerationTargetEntity = accelerationTargetDao.findByCIDR(accelerationTargetBean.getCidr());
        Validator.notNull(accelerationTargetEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_TARGET_NOT_EXIST));

        accelerationTargetEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (StringUtils.isBlank(accelerationTargetBean.getCategory())) {
            accelerationTargetEntity.setCategory("unknown");
        } else {
            accelerationTargetEntity.setCategory(accelerationTargetBean.getCategory());
        }
        if (StringUtils.isBlank(accelerationTargetBean.getSubcategory())) {
            accelerationTargetEntity.setSubcategory("unknown");
        } else {
            accelerationTargetEntity.setSubcategory(accelerationTargetBean.getSubcategory());
        }
        accelerationTargetDao.saveOrUpdate(accelerationTargetEntity);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeAccelerationTarget(long id) {
        AccelerationTargetEntity accelerationTargetEntity = accelerationTargetDao.findOne(id);
        if (null != accelerationTargetEntity) {
            accelerationTargetDao.delete(accelerationTargetEntity);
        }
    }

}