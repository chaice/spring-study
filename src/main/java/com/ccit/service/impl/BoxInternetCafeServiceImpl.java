package com.ccit.service.impl;


import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.BoxInternetCafeBean;
import com.ccit.dao.BoxInternetCafeDao;
import com.ccit.dao.CustomerInternetCafeDao;
import com.ccit.entity.BoxInternetCafeEntity;
import com.ccit.entity.CustomerInternetCafeEntity;
import com.ccit.enums.BoxStatusEnum;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.zion.interfaces.BoxIF;
import com.ccit.rest.zion.request.BoxReq;
import com.ccit.rest.zion.response.BoxRes;
import com.ccit.service.BoxInternetCafeService;
import com.ccit.service.GlobalDictionaryService;
import com.ccit.service.ResourceService;
import com.ccit.util.StringUtils;
import com.ccit.vo.BoxInternetCafeVo;
import com.ccit.vo.CustomerInternetCafeVo;
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
public class BoxInternetCafeServiceImpl implements BoxInternetCafeService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private BoxInternetCafeDao boxInternetCafeDao;

    @Autowired
    private CustomerInternetCafeDao customerInternetCafeDao;

    @Autowired
    @Qualifier("zionBoxIF")
    private BoxIF boxIF;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private GlobalDictionaryService dictionaryService;

    private final String SPEED_UP_DEFAULT = "box.speed.up";
    private final String SPEED_DOWN_DEFAULT = "box.speed.down";

    public List<BoxInternetCafeVo> listBox(String sn, String customerInternetCafeName, String sort) {
        List<BoxInternetCafeVo> boxVoList = new LinkedList<>();
        List<BoxInternetCafeEntity> boxEntityList = boxInternetCafeDao.listBox(sn, customerInternetCafeName, sort);

        boxEntityList.forEach(boxEntity -> boxVoList.add(toVo(boxEntity)));
        return boxVoList;
    }


    public BoxInternetCafeVo getById(long id) {
        BoxInternetCafeEntity boxEntity = boxInternetCafeDao.findOne(id);
        Validator.notNull(boxEntity, ResourceException.error(CustomerErrorConstants.BOX_INTERNET_CAFE_NOT_EXIST));

        return toVo(boxEntity);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveBox(BoxInternetCafeBean boxBean) {
        // 序列号check
        assertSnValid(null, boxBean.getSn());
        BoxInternetCafeEntity boxEntity = new BoxInternetCafeEntity(
                boxBean.getCreateUid(),
                boxBean.getManufacturer(),
                boxBean.getModel(),
                boxBean.getSn(),
                boxBean.getWanCount(),
                boxBean.getHardwareVersion(),
                boxBean.getSoftwareVersion(),
                boxBean.getLanIP(),
                boxBean.getLanNetmask(),
                boxBean.getIpipBoxMaster(),
                boxBean.getIpipEntryMaster(),
                boxBean.getIpipBoxSlave(),
                boxBean.getIpipEntrySlave(),
                Integer.parseInt(dictionaryService.fromKey(SPEED_UP_DEFAULT)),
                Integer.parseInt(dictionaryService.fromKey(SPEED_DOWN_DEFAULT))
        );
        boxEntity.setStatus(BoxStatusEnum.STORE.getCode());
        boxEntity.setCreateUid(UserInterceptor.getLocalUser().getId());
        boxEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boxInternetCafeDao.saveOrUpdate(boxEntity);

        // zion上新建盒子记录
        updateBoxOfZion(boxEntity);
    }


//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    public void updateBox(BoxInternetCafeBean boxBean) {
//        BoxInternetCafeEntity boxEntity = boxInternetCafeDao.findOne(boxBean.getId());
//        Validator.notNull(boxEntity, BusinessException.error(CustomerErrorConstants.BOX_INTERNET_CAFE_NOT_EXIST));
//
//        assertSnValid(boxEntity.getSerialNumber(), boxBean.getSn());
//
//        boxEntity.setSpeedUp(boxBean.getSpeedUp());
//        boxEntity.setSpeedDown(boxBean.getSpeedDown());
//        boxEntity.setLastUid(boxBean.getLastUid());
//        boxEntity.setLastTime(new Timestamp(System.currentTimeMillis()));
//        boxInternetCafeDao.saveOrUpdate(boxEntity);
//
//        updateBoxOfZion(boxEntity);
//    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateBoxPart(BoxInternetCafeBean boxBean) {
        BoxInternetCafeEntity boxEntity = boxInternetCafeDao.findOne(boxBean.getId());
        Validator.notNull(boxEntity, BusinessException.error(CustomerErrorConstants.BOX_INTERNET_CAFE_NOT_EXIST));

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
        if (null != boxBean.getEntryId()) {
            boxEntity.setEntryId(boxBean.getEntryId());
        }
        if (null != boxBean.getCustomerId()) {
            boxEntity.setCustomerId(boxBean.getCustomerId());
        }
        boxInternetCafeDao.saveOrUpdate(boxEntity);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeBox(long id) {
        BoxInternetCafeEntity boxEntity = boxInternetCafeDao.findOne(id);
        if (null != boxEntity) {
            resourceService.check(boxEntity);

            String delTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            boxEntity.setSerialNumber(boxEntity.getSerialNumber() + "_" + delTime);
            boxEntity.setRemoved(true);
            boxInternetCafeDao.saveOrUpdate(boxEntity);

            removeBoxOfZion(boxEntity.getId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateBoxPart(List<BoxInternetCafeBean> boxInternetCafeBeanList) {
        boxInternetCafeDao.updateAllStatus(BoxStatusEnum.OFFLINE);
        for (BoxInternetCafeBean boxInternetCafeBean : boxInternetCafeBeanList) {
            try {
                updateBoxPart(boxInternetCafeBean);
            } catch (Exception e) {
                logger.error("盒子信息更新错误：盒子id=" + boxInternetCafeBean.getId(), e);
            }
        }
    }


    // 序列号重复抛异常
    private void assertSnValid(String oldSn, String newSn) throws BusinessException {
        if (StringUtils.equals(oldSn, newSn)) {
            return;
        }
        int count = boxInternetCafeDao.countBySn(newSn);
        if (count > 0) {
            throw new BusinessException(CustomerErrorConstants.BOX_INTERNET_CAFE_SN_REPEAT);
        }
    }


    // Zion盒子更新
    private void updateBoxOfZion(BoxInternetCafeEntity boxEntity) {
        BoxReq boxReq = new BoxReq();

        boxReq.setSn(boxEntity.getSerialNumber());
        boxReq.setHardwareVersion(boxEntity.getHardwareVersion());
        boxReq.setSoftwareVersion(boxEntity.getSoftwareVersion());
        boxReq.setWanCount(boxEntity.getWanCount());
        boxReq.setSpeedUp(boxEntity.getSpeedUp());
        boxReq.setSpeedDown(boxEntity.getSpeedDown());
        boxReq.setLanIP(boxEntity.getLanIP());
        boxReq.setLanNetmask(boxEntity.getLanNetmask());
        boxReq.setIpipBoxMaster(boxEntity.getIpipBoxMaster());
        boxReq.setIpipBoxSlave(boxEntity.getIpipBoxSlave());
        boxReq.setIpipEntryMaster(boxEntity.getIpipEntryMaster());
        boxReq.setIpipEntrySlave(boxEntity.getIpipEntrySlave());
        boxReq.setFlowControlMode(boxEntity.getFlowControlMode());

        BoxRes boxRes = boxIF.updateBox(boxEntity.getId(), boxReq);
        if (!boxRes.isSuccess()) {
            logger.error(String.format("Zion-更新盒子-同步盒子失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_INTERNET_CAFE_SYNCHRONIZATION_FAIL);
        }
    }


    // Zion盒子删除
    public void removeBoxOfZion(long id) {
        BoxRes boxRes = boxIF.removeBox(id);
        if (!boxRes.isSuccess()) {
            logger.error(String.format("Zion-删除盒子-同步盒子失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_INTERNET_CAFE_SYNCHRONIZATION_FAIL);
        }
    }


    private BoxInternetCafeVo toVo(BoxInternetCafeEntity entity) {
        BoxInternetCafeVo vo = new BoxInternetCafeVo(
                entity.getId(),
                entity.getManufacturer(),
                entity.getModel(),
                entity.getSerialNumber(),
                entity.getWanCount(),
                entity.getHardwareVersion(),
                entity.getSoftwareVersion(),
                entity.getStatus(),
                entity.getLanIP(),
                entity.getLanNetmask(),
                entity.getIpipBoxMaster(),
                entity.getIpipEntryMaster(),
                entity.getIpipBoxSlave(),
                entity.getIpipEntrySlave(),
                entity.getFlowControlMode()
        );

        CustomerInternetCafeEntity customerInternetCafeEntity = null;
        if (null != entity.getCustomerId()) {
            customerInternetCafeEntity = customerInternetCafeDao.findOne(entity.getCustomerId());
        }

        if (customerInternetCafeEntity != null) {
            vo.setCustomer(new CustomerInternetCafeVo(
                    customerInternetCafeEntity.getId(),
                    customerInternetCafeEntity.getName()));
        } else {
            vo.setCustomer(new CustomerInternetCafeVo(0l, ""));
        }

        return vo;
    }

}