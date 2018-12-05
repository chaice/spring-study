package com.ccit.service.impl;


import com.ccit.dao.*;
import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.BoxEnterpriseBean;
import com.ccit.bean.OrderCheckEnterpriseBean;
import com.ccit.dao.*;
import com.ccit.entity.BoxEnterpriseEntity;
import com.ccit.entity.CustomerEnterpriseEntity;
import com.ccit.entity.OrderEnterpriseEntity;
import com.ccit.enums.AccelerateMode;
import com.ccit.enums.BoxStatusEnum;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.sextant.interfaces.BoxIF;
import com.ccit.rest.sextant.request.BoxReq;
import com.ccit.rest.sextant.response.BoxRes;
import com.ccit.service.BoxEnterpriseService;
import com.ccit.service.GlobalDictionaryService;
import com.ccit.service.ResourceService;
import com.ccit.util.StringUtils;
import com.ccit.vo.BoxEnterpriseVo;
import com.ccit.vo.CustomerEnterpriseVo;
import com.ccit.vo.OrderEnterpriseVo;
import com.ccit.web.interceptor.UserInterceptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class BoxEnterpriseServiceImpl implements BoxEnterpriseService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private BoxEnterpriseDao boxEnterpriseDao;

    @Autowired
    private CustomerEnterpriseDao customerEnterpriseDao;

    @Autowired
    private OrderEnterpriseDao orderEnterpriseDao;

    @Autowired
    private BoxEntryEnterpriseSSDao boxEntryEnterpriseSSDao;

    @Autowired
    private BoxEntryEnterpriseL2tpDao boxEntryEnterpriseL2tpDao;

    @Autowired
    private AccelerationBoxUpgradeTaskDao accelerationBoxUpgradeTaskDao;

    @Autowired
    @Qualifier("sextantBoxIF")
    private BoxIF boxIF;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private GlobalDictionaryService dictionaryService;

    @Autowired
    private Assembler<OrderCheckEnterpriseBean, OrderEnterpriseEntity, OrderEnterpriseVo> orderEnterpriseAssembler;

    private final String SPEED_UP_DEFAULT = "box.speed.up";
    private final String SPEED_DOWN_DEFAULT = "box.speed.down";

    public List<BoxEnterpriseVo> listBox(String sn, String customerEnterpriseName, String sort) {
        List<BoxEnterpriseVo> boxVoList = new LinkedList<>();
        List<BoxEnterpriseEntity> boxEntityList = boxEnterpriseDao.listBox(sn, customerEnterpriseName, sort);

        boxEntityList.forEach(boxEntity -> {
            boxVoList.add(toVo(boxEntity));
        });
        return boxVoList;
    }

    public BoxEnterpriseVo getById(long id) {
        BoxEnterpriseEntity boxEntity = boxEnterpriseDao.findOne(id);
        Validator.notNull(boxEntity, ResourceException.error(CustomerErrorConstants.BOX_ENTERPRISE_NOT_EXIST));

        return toVo(boxEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveBox(BoxEnterpriseBean boxBean) {
        // 序列号check
        assertSnValid(null, boxBean.getSn());
        BoxEnterpriseEntity boxEntity = new BoxEnterpriseEntity(
                boxBean.getCreateUid(),
                boxBean.getManufacturer(),
                boxBean.getModel(),
                boxBean.getSn(),
                boxBean.getWanCount(),
                boxBean.getHardwareVersion(),
                boxBean.getSoftwareVersion(),
                Integer.parseInt(dictionaryService.fromKey(SPEED_UP_DEFAULT)),
                Integer.parseInt(dictionaryService.fromKey(SPEED_DOWN_DEFAULT)),
                boxBean.getServerAddr(),
                boxBean.getServerPort()
        );
        boxEntity.setBoxAuth(0l);
        boxEntity.setAccelerateMode(AccelerateMode.SIMPLE_MODE.getMode());
        boxEntity.setStatus(BoxStatusEnum.STORE.getCode());
        boxEntity.setCreateUid(UserInterceptor.getLocalUser().getId());
        boxEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boxEnterpriseDao.saveOrUpdate(boxEntity);

        // sextant上新建盒子记录
        updateBoxOfSextant(boxEntity);
    }

//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    public void updateBox(BoxEnterpriseBean boxBean) {
//        BoxEnterpriseEntity boxEntity = boxEnterpriseDao.findOne(boxBean.getId());
//        Validator.notNull(boxEntity, BusinessException.error(CustomerErrorConstants.BOX_ENTERPRISE_NOT_EXIST));
//
//        assertSnValid(boxEntity.getSerialNumber(), boxBean.getSn());
//
//        boxEntity.setSpeedUp(boxBean.getSpeedUp());
//        boxEntity.setSpeedDown(boxBean.getSpeedDown());
//        boxEntity.setLastUid(boxBean.getLastUid());
//        boxEntity.setLastTime(new Timestamp(System.currentTimeMillis()));
//        boxEnterpriseDao.saveOrUpdate(boxEntity);
//
//        updateBoxOfSextant(boxEntity);
//    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateBoxPart(BoxEnterpriseBean boxBean) {
        BoxEnterpriseEntity boxEntity = boxEnterpriseDao.findOne(boxBean.getId());
        Validator.notNull(boxEntity, BusinessException.error(CustomerErrorConstants.BOX_ENTERPRISE_NOT_EXIST));

        if (StringUtils.isNotBlank(boxBean.getStatus())) {
            boxEntity.setStatus(boxBean.getStatus());
        }
        if (StringUtils.isNotBlank(boxBean.getAlias())) {
            boxEntity.setAlias(boxBean.getAlias());
        }
        if (StringUtils.isNotBlank(boxBean.getWan1IP())) {
            boxEntity.setWan1IP(boxBean.getWan1IP());
        }
        if (StringUtils.isNotBlank(boxBean.getWan1GW())) {
            boxEntity.setWan1GW(boxBean.getWan1GW());
        }
        if (StringUtils.isNotBlank(boxBean.getLanIP())) {
            boxEntity.setLanIP(boxBean.getLanIP());
        }
        if (StringUtils.isNotBlank(boxBean.getLanNetmask())) {
            boxEntity.setLanNetmask(boxBean.getLanNetmask());
        }
        if (null != boxBean.getEntryId()) {
            boxEntity.setEntryId(boxBean.getEntryId());
        }
        if (null != boxBean.getCustomerId()) {
            boxEntity.setCustomerId(boxBean.getCustomerId());
        }
        if (boxBean.getAccelerateMode() != null) {
            boxEntity.setAccelerateMode(boxBean.getAccelerateMode());
        }
        boxEnterpriseDao.saveOrUpdate(boxEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeBox(long id) {
        BoxEnterpriseEntity boxEntity = boxEnterpriseDao.findOne(id);
        if (null != boxEntity) {
            resourceService.check(boxEntity);

            String delTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            boxEntity.setSerialNumber(boxEntity.getSerialNumber() + "_" + delTime);
            boxEntity.setRemoved(true);
            boxEnterpriseDao.saveOrUpdate(boxEntity);

            boxEntryEnterpriseSSDao.deleteByBoxId(boxEntity.getId());
            boxEntryEnterpriseL2tpDao.deleteByBoxId(boxEntity.getId());
            accelerationBoxUpgradeTaskDao.deleteByBoxId(boxEntity.getId());

            removeBoxOfSextant(boxEntity.getId());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateBoxPart(List<BoxEnterpriseBean> boxEnterpriseBeanList) {
        boxEnterpriseDao.updateAllStatus(BoxStatusEnum.OFFLINE);
        for (BoxEnterpriseBean boxEnterpriseBean : boxEnterpriseBeanList) {
            try {
                updateBoxPart(boxEnterpriseBean);
            } catch (Exception e) {
                logger.error("盒子信息更新错误：盒子id=" + boxEnterpriseBean.getId(), e);
            }
        }
    }

    // 序列号重复抛异常
    private void assertSnValid(String oldSn, String newSn) throws BusinessException {
        if (StringUtils.equals(oldSn, newSn)) {
            return;
        }
        int count = boxEnterpriseDao.countBySn(newSn);
        if (count > 0) {
            throw new BusinessException(CustomerErrorConstants.BOX_ENTERPRISE_SN_REPEAT);
        }
    }

    // Sextant盒子更新
    private void updateBoxOfSextant(BoxEnterpriseEntity boxEntity) {
        BoxReq boxReq = new BoxReq();

        boxReq.setSn(boxEntity.getSerialNumber());
        boxReq.setHardwareVersion(boxEntity.getHardwareVersion());
        boxReq.setSoftwareVersion(boxEntity.getSoftwareVersion());
        boxReq.setWanCount(boxEntity.getWanCount());
        boxReq.setSpeedUp(boxEntity.getSpeedUp());
        boxReq.setSpeedDown(boxEntity.getSpeedDown());
        boxReq.setServerAddr(boxEntity.getServerAddr());
        boxReq.setServerPort(boxEntity.getServerPort());
        boxReq.setAccelerateMode(boxEntity.getAccelerateMode());

        BoxRes boxRes = boxIF.updateBox(boxEntity.getId(), boxReq);
        if (!boxRes.isSuccess()) {
            logger.error(String.format("Sextant-更新盒子-同步盒子失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_ENTERPRISE_SYNCHRONIZATION_FAIL);
        }
    }

    // Sextant盒子删除
    public void removeBoxOfSextant(long id) {
        BoxRes boxRes = boxIF.removeBox(id);
        if (!boxRes.isSuccess()) {
            logger.error(String.format("Sextant-删除盒子-同步盒子失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_ENTERPRISE_SYNCHRONIZATION_FAIL);
        }
    }

    private BoxEnterpriseVo toVo(BoxEnterpriseEntity entity) {
        BoxEnterpriseVo vo = new BoxEnterpriseVo(
                entity.getId(),
                entity.getManufacturer(),
                entity.getModel(),
                entity.getSerialNumber(),
                entity.getWanCount(),
                entity.getHardwareVersion(),
                entity.getSoftwareVersion(),
                entity.getStatus(),
                entity.getWan1IP(),
                entity.getWan1GW(),
                entity.getLanIP(),
                entity.getLanNetmask(),
                entity.getServerAddr(),
                entity.getServerPort()
        );

        CustomerEnterpriseEntity customerEnterpriseEntity = null;
        if (null != entity.getCustomerId()) {
            customerEnterpriseEntity = customerEnterpriseDao.findOne(entity.getCustomerId());
        }

        if (entity.getOrderId() != null) {
            OrderEnterpriseEntity enterpriseEntity = orderEnterpriseDao.findOne(entity.getOrderId());
            if (enterpriseEntity != null) {
                vo.setOrderVo(orderEnterpriseAssembler.toVo(enterpriseEntity));
            }
        }

        if (customerEnterpriseEntity != null) {
            vo.setCustomer(new CustomerEnterpriseVo(customerEnterpriseEntity.getId(), customerEnterpriseEntity.getSextantName()));
        } else {
            vo.setCustomer(new CustomerEnterpriseVo(0l, ""));
        }

        return vo;
    }


}