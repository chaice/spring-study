package com.ccit.service.impl;


import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.BoxInternetCafeBean;
import com.ccit.bean.EntryIPIPBean;
import com.ccit.dao.BoxEntryIPIPDao;
import com.ccit.dao.BoxInternetCafeDao;
import com.ccit.dao.CustomerInternetCafeDao;
import com.ccit.dao.EntryIPIPDao;
import com.ccit.entity.BoxEntryIPIPEntity;
import com.ccit.entity.BoxInternetCafeEntity;
import com.ccit.entity.CustomerInternetCafeEntity;
import com.ccit.entity.EntryIPIPEntity;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.zion.interfaces.BoxIF;
import com.ccit.rest.zion.request.BoxReq;
import com.ccit.rest.zion.request.EntryReq;
import com.ccit.rest.zion.response.BoxRes;
import com.ccit.service.AccelerationIPIPService;
import com.ccit.service.GlobalDictionaryService;
import com.ccit.vo.BoxInternetCafeVo;
import com.ccit.vo.CustomerInternetCafeVo;
import com.ccit.vo.EntryIPIPVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AccelerationIPIPServiceImpl implements AccelerationIPIPService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private BoxInternetCafeDao boxInternetCafeDao;

    @Autowired
    private BoxEntryIPIPDao boxEntryIPIPDao;

    @Autowired
    private EntryIPIPDao entryIPIPDao;

    @Autowired
    private CustomerInternetCafeDao customerInternetCafeDao;

    @Autowired
    private GlobalDictionaryService dictionaryService;

    @Autowired
    @Qualifier("zionBoxIF")
    private BoxIF boxIF;


    public List<BoxInternetCafeVo> listAcceleration() {
        List<BoxInternetCafeVo> boxVos = new LinkedList<>();
        List<BoxInternetCafeEntity> boxEntities = boxInternetCafeDao.listBox(null, null, null);
        boxEntities.forEach(boxEntity -> {
            List<BoxEntryIPIPEntity> boxEntryIPIPEntities = boxEntryIPIPDao.listByBoxId(boxEntity.getId());
            List<EntryIPIPVo> activeEntryList = new LinkedList<>();
            boxEntryIPIPEntities.forEach(boxEntryIPIPEntity -> {
                EntryIPIPEntity entryEntity = entryIPIPDao.findOne(boxEntryIPIPEntity.getEntryId());
                activeEntryList.add(
                        new EntryIPIPVo(
                                entryEntity.getId(),
                                entryEntity.getName(),
                                entryEntity.getMasterIP(),
                                entryEntity.getSlaveIP())
                );
            });
            BoxInternetCafeVo boxVo = toVo(boxEntity);
            boxVo.setActiveEntryList(activeEntryList);

            boxVos.add(boxVo);
        });
        return boxVos;
    }


    public BoxInternetCafeVo getAcceleration(long boxId) {
        BoxInternetCafeEntity boxEntity = boxInternetCafeDao.findOne(boxId);
        Validator.notNull(boxEntity, ResourceException.error(CustomerErrorConstants.BOX_INTERNET_CAFE_NOT_EXIST));

        //盒子已有入口列表
        List<BoxEntryIPIPEntity> boxEntryIPIPEntities = boxEntryIPIPDao.listByBoxId(boxId);
        List<EntryIPIPVo> activeEntryList = new LinkedList<>();
        boxEntryIPIPEntities.forEach(boxEntryIPIPEntity -> {
            EntryIPIPEntity entryEntity = entryIPIPDao.findOne(boxEntryIPIPEntity.getEntryId());
            activeEntryList.add(
                    new EntryIPIPVo(
                            entryEntity.getId(),
                            entryEntity.getName(),
                            entryEntity.getMasterIP(),
                            entryEntity.getSlaveIP())
            );
        });

        BoxInternetCafeVo boxVo = toVo(boxEntity);
        boxVo.setActiveEntryList(activeEntryList);
        return boxVo;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateAcceleration(BoxInternetCafeBean boxBean) {
        BoxInternetCafeEntity boxEntity = boxInternetCafeDao.findOne(boxBean.getId());
        Validator.notNull(boxEntity, BusinessException.error(CustomerErrorConstants.BOX_INTERNET_CAFE_NOT_EXIST));

        boxEntity.setSpeedUp(boxBean.getSpeedUp());
        boxEntity.setSpeedDown(boxBean.getSpeedDown());
        boxEntity.setEnable(Boolean.TRUE);
        boxEntity.setFlowControlMode(boxBean.getFlowControlMode());
        boxInternetCafeDao.saveOrUpdate(boxEntity);

        boxEntryIPIPDao.deleteByBoxId(boxBean.getId());

        boolean entryCheck = false;
        for (EntryIPIPBean entryBean : boxBean.getActiveEntryList()) {
            BoxEntryIPIPEntity boxEntryIPIPEntity = new BoxEntryIPIPEntity();
            boxEntryIPIPEntity.setEntryId(entryBean.getId());
            boxEntryIPIPEntity.setBoxId(boxEntity.getId());
            boxEntryIPIPEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boxEntryIPIPDao.saveOrUpdate(boxEntryIPIPEntity);

            if (boxEntity.getEntryId() != null && boxEntity.getEntryId() != 0L && boxEntity.getEntryId().equals(boxEntryIPIPEntity.getEntryId())) {
                entryCheck = true;
            }
        }

        if (boxEntity.getEntryId() != null && boxEntity.getEntryId() != 0L && !entryCheck) {
            throw BusinessException.error(CustomerErrorConstants.ACCELERATION_USED_ENTRY_SHOULD_NOT_BE_REMOVED);
        }

        updateAccelerationOfZion(boxBean);
    }


    private BoxInternetCafeVo toVo(BoxInternetCafeEntity boxEntity) {
        BoxInternetCafeVo boxVo = new BoxInternetCafeVo(
                boxEntity.getId(),
                boxEntity.getSerialNumber(),
                boxEntity.getAlias(),
                boxEntity.getSpeedUp(),
                boxEntity.getSpeedDown(),
                boxEntity.getEnable(),
                boxEntity.getFlowControlMode()
        );

        if (null != boxEntity.getCustomerId()) {
            CustomerInternetCafeEntity customerInternetCafeEntity = customerInternetCafeDao.findOne(boxEntity.getCustomerId());
            if (null != customerInternetCafeEntity) {
                boxVo.setCustomer(new CustomerInternetCafeVo(
                        customerInternetCafeEntity.getId(),
                        customerInternetCafeEntity.getName()));
            }
        }

        if (null != boxEntity.getEntryId()) {
            EntryIPIPEntity entryEntity = entryIPIPDao.findOne(boxEntity.getEntryId());
            if (null != entryEntity) {
                boxVo.setUsedEntry(
                        new EntryIPIPVo(
                                entryEntity.getId(),
                                entryEntity.getName(),
                                entryEntity.getMasterIP(),
                                entryEntity.getSlaveIP()
                        )
                );
            }
        }

        return boxVo;
    }


    private void updateAccelerationOfZion(BoxInternetCafeBean boxBean) {
        BoxReq boxReq = new BoxReq();
        boxReq.setSpeedUp(boxBean.getSpeedUp());
        boxReq.setSpeedDown(boxBean.getSpeedDown());
        boxReq.setFlowControlMode(boxBean.getFlowControlMode());
        boxReq.setEntryList(new LinkedList<>());
        boxBean.getActiveEntryList().forEach(entryBean -> {
            EntryReq entryReq = new EntryReq();
            entryReq.setEntryId(entryBean.getId());
            boxReq.getEntryList().add(entryReq);
        });

        BoxRes boxRes = boxIF.updateAccelerate(boxBean.getId(), boxReq);
        if (!boxRes.isSuccess()) {
            logger.error(String.format("Zion-变更加速业务-变更加速业务失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ACCELERATION_CHANGE_FAIL);
        }
    }

}