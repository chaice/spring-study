package com.ccit.service.impl;

import com.ccit.bean.*;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.vo.*;
import com.ccit.bean.*;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.enums.AccelerateMode;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.sextant.interfaces.BoxIF;
import com.ccit.rest.sextant.request.BoxReq;
import com.ccit.rest.sextant.request.EntryReq;
import com.ccit.rest.sextant.response.BoxRes;
import com.ccit.service.AccelerationEnterpriseService;
import com.ccit.vo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AccelerationEnterpriseServiceImpl implements AccelerationEnterpriseService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private BoxEnterpriseDao boxEnterpriseDao;

    @Autowired
    private BoxEntryEnterpriseSSDao boxEntryEnterpriseSSDao;

    @Autowired
    private BoxEntryEnterpriseL2tpDao boxEntryEnterpriseL2tpDao;

    @Autowired
    private EntryEnterpriseSSDao entryEnterpriseSSDao;

    @Autowired
    private EntryEnterpriseL2tpDao entryEnterpriseL2tpDao;

    @Autowired
    private CustomerEnterpriseDao customerEnterpriseDao;

    @Autowired
    private BoxCollectionDao boxCollectionDao;

    @Autowired
    private OrderEnterpriseDao orderEnterpriseDao;

    @Autowired
    private AccelerationCollectionEnterpriseDao accelerationCollectionEnterpriseDao;

    @Autowired
    private EntryEnterpriseSSGroupDao entryEnterpriseGroupSSDao;

    @Autowired
    private Assembler<OrderCheckEnterpriseBean, OrderEnterpriseEntity, OrderEnterpriseVo> orderEnterpriseAssembler;

    @Autowired
    @Qualifier("sextantBoxIF")
    private BoxIF boxIF;

    public List<BoxEnterpriseVo> listAcceleration() {
        List<BoxEnterpriseVo> boxVos = new LinkedList<>();
        List<BoxEnterpriseEntity> boxEntities = boxEnterpriseDao.listBox(null, null, null);
        boxEntities.forEach(boxEntity -> {
            BoxEnterpriseVo boxVo = toVo(boxEntity);

            if (boxEntity.getOrderId() != null) {
                OrderEnterpriseEntity enterpriseEntity = orderEnterpriseDao.findOne(boxEntity.getOrderId());
                if (enterpriseEntity != null) {
                    OrderEnterpriseVo enterpriseVo = orderEnterpriseAssembler.toVo(enterpriseEntity);
                    boxVo.setOrderVo(enterpriseVo);
                    boxVo.setEnterpriseName(enterpriseEntity.getEnterpriseName());
                } else {
                    boxVo.setEnterpriseName("");
                }
            }

            boxVos.add(boxVo);
        });
        return boxVos;
    }

    public BoxEnterpriseVo getAcceleration(long boxId) {
        BoxEnterpriseEntity boxEntity = boxEnterpriseDao.findOne(boxId);
        Validator.notNull(boxEntity, ResourceException.error(CustomerErrorConstants.BOX_INTERNET_CAFE_NOT_EXIST));

        //盒子已有入口列表
        List<BoxEntryShadowsocksEntity> boxEntryShadowsocksEntities = boxEntryEnterpriseSSDao.listByBoxId(boxId);
        List<EntryEnterpriseSSGroupVo> advancedEntryList = new LinkedList<>();
        boxEntryShadowsocksEntities.forEach(boxEntryShadowsocksEntity -> {
            EntryEnterpriseSSGroupEntity enterpriseGroupSSEntity = entryEnterpriseGroupSSDao.findOne(boxEntryShadowsocksEntity.getEntryId());
            if (enterpriseGroupSSEntity != null) {
                advancedEntryList.add(
                        new EntryEnterpriseSSGroupVo(
                                enterpriseGroupSSEntity.getId(),
                                enterpriseGroupSSEntity.getName(),
                                enterpriseGroupSSEntity.getDescription()
                        )
                );
            }
        });

        //l2tp入口列表
        List<BoxEntryL2tpEntity> boxEntryL2tpEntities = boxEntryEnterpriseL2tpDao.listByBoxId(boxId);
        List<EntryEnterpriseL2TPVo> simpleEntryList = new LinkedList<>();
        boxEntryL2tpEntities.forEach(boxEntryL2tpEntity -> {
            EntryEnterpriseL2tpEntity entryEntity = entryEnterpriseL2tpDao.findOne(boxEntryL2tpEntity.getEntryId());
            if (entryEntity != null) {
                simpleEntryList.add(
                        new EntryEnterpriseL2TPVo(
                                entryEntity.getId(),
                                entryEntity.getName(),
                                entryEntity.getMasterIP(),
                                entryEntity.getSlaveIP()
                        )
                );
            }
        });

        //盒子已有加速集合列表
        List<BoxCollectionEntity> boxCollectionEntityList = boxCollectionDao.listByBoxId(boxId);
        //获取advanced模式下的集合列表
        List<AccelerationCollectionEnterpriseVo> advancedCollectionEnterpriseVoList = new LinkedList<>();
        //获取simple模式下的集合列表
        List<AccelerationCollectionEnterpriseVo> simpleCollectionEnterpriseVoList = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boxCollectionEntityList.forEach(boxCollectionEntity -> {

            AccelerationCollectionEnterpriseEntity collectionEnterpriseEntity = accelerationCollectionEnterpriseDao.findOne(boxCollectionEntity.getCollectionId());

            AccelerationCollectionEnterpriseVo collectionEnterpriseVo = new AccelerationCollectionEnterpriseVo();
            collectionEnterpriseVo.setId(collectionEnterpriseEntity.getId());
            collectionEnterpriseVo.setCollectionName(collectionEnterpriseEntity.getCollectionName());
            collectionEnterpriseVo.setCollectionDescription(collectionEnterpriseEntity.getCollectionDescription());
            collectionEnterpriseVo.setCreateTime(sdf.format(collectionEnterpriseEntity.getCreateTime()));

            if (AccelerateMode.SIMPLE_MODE.getMode().equals(collectionEnterpriseEntity.getAccelerateMode())) {
                simpleCollectionEnterpriseVoList.add(collectionEnterpriseVo);
            } else if (AccelerateMode.ADVANCED_MODE.getMode().equals(collectionEnterpriseEntity.getAccelerateMode())) {
                advancedCollectionEnterpriseVoList.add(collectionEnterpriseVo);
            }
        });

        BoxEnterpriseVo boxVo = toVo(boxEntity);

        if (boxEntity.getOrderId() != null) {
            OrderEnterpriseEntity enterpriseEntity = orderEnterpriseDao.findOne(boxEntity.getOrderId());
            if (enterpriseEntity != null) {
                OrderEnterpriseVo enterpriseVo = orderEnterpriseAssembler.toVo(enterpriseEntity);
                boxVo.setOrderVo(enterpriseVo);
            }
        }

        boxVo.setAdvancedEntryList(advancedEntryList);
        boxVo.setSimpleEntryList(simpleEntryList);
        boxVo.setAdvancedCollectionEntityList(advancedCollectionEnterpriseVoList);
        boxVo.setSimpleCollectionEntityList(simpleCollectionEnterpriseVoList);
        return boxVo;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateAcceleration(BoxEnterpriseBean boxBean) {
        BoxEnterpriseEntity boxEntity = boxEnterpriseDao.findOne(boxBean.getId());
        Validator.notNull(boxEntity, BusinessException.error(CustomerErrorConstants.BOX_INTERNET_CAFE_NOT_EXIST));

        boxEntity.setSpeedUp(boxBean.getSpeedUp());
        boxEntity.setSpeedDown(boxBean.getSpeedDown());
        boxEntity.setEnable(Boolean.TRUE);
        boxEntity.setOrderId(boxBean.getOrderId());
        if (boxBean.getBoxAuth() != null) {
            boxEntity.setBoxAuth(boxBean.getBoxAuth());
        }
        boxEntity.setAccessUserName(boxBean.getAccessUserName());
        boxEntity.setAccessPassword(boxBean.getAccessPassword());
        boxEnterpriseDao.saveOrUpdate(boxEntity);

        boxEntryEnterpriseSSDao.deleteByBoxId(boxBean.getId());
        boxEntryEnterpriseL2tpDao.deleteByBoxId(boxBean.getId());

        boxCollectionDao.deleteByBoxId(boxBean.getId());

        boolean entryCheck = false;
        for (EntryEnterpriseSSGroupBean entryBean : boxBean.getAdvancedEntryList()) {
            BoxEntryShadowsocksEntity boxEntryShadowsocksEntity = new BoxEntryShadowsocksEntity();
            boxEntryShadowsocksEntity.setEntryId(entryBean.getId());
            boxEntryShadowsocksEntity.setBoxId(boxEntity.getId());
            boxEntryShadowsocksEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boxEntryEnterpriseSSDao.saveOrUpdate(boxEntryShadowsocksEntity);

            if (AccelerateMode.ADVANCED_MODE.getMode().equals(boxEntity.getAccelerateMode())) {
                if (boxEntity.getEntryId() != null && boxEntity.getEntryId() != 0L && boxEntity.getEntryId().equals(entryBean.getId())) {
                    entryCheck = true;
                }
            }
        }

        for (EntryEnterpriseL2TPBean entryBean : boxBean.getSimpleEntryList()) {
            BoxEntryL2tpEntity boxEntryL2tpEntity = new BoxEntryL2tpEntity();
            boxEntryL2tpEntity.setEntryId(entryBean.getId());
            boxEntryL2tpEntity.setBoxId(boxEntity.getId());
            boxEntryL2tpEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boxEntryEnterpriseL2tpDao.saveOrUpdate(boxEntryL2tpEntity);

            if (AccelerateMode.SIMPLE_MODE.getMode().equals(boxEntity.getAccelerateMode())) {
                if (boxEntity.getEntryId() != null && boxEntity.getEntryId() != 0L && boxEntity.getEntryId().equals(entryBean.getId())) {
                    entryCheck = true;
                }
            }
        }

        for (BoxCollectionBean collectionBean : boxBean.getActiveCollectionList()) {
            BoxCollectionEntity collectionEntity = new BoxCollectionEntity();
            collectionEntity.setBoxId(boxEntity.getId());
            collectionEntity.setCollectionId(collectionBean.getCollectionId());
            collectionEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boxCollectionDao.saveOrUpdate(collectionEntity);
        }

        if (boxEntity.getEntryId() != null && boxEntity.getEntryId() != 0L && !entryCheck) {
            throw BusinessException.error(CustomerErrorConstants.ACCELERATION_USED_ENTRY_SHOULD_NOT_BE_REMOVED);
        }

        updateAccelerationOfSextant(boxBean);
    }

    private BoxEnterpriseVo toVo(BoxEnterpriseEntity boxEntity) {
        BoxEnterpriseVo boxVo = new BoxEnterpriseVo(
                boxEntity.getId(),
                boxEntity.getSerialNumber(),
                boxEntity.getAlias(),
                boxEntity.getSpeedUp(),
                boxEntity.getSpeedDown(),
                boxEntity.getEnable(),
                boxEntity.getAccessUserName(),
                boxEntity.getAccessPassword()
        );

        boxVo.setBoxAuth(boxEntity.getBoxAuth());

        if (null != boxEntity.getCustomerId()) {
            CustomerEnterpriseEntity customerEnterpriseEntity = customerEnterpriseDao.findOne(boxEntity.getCustomerId());
            if (null != customerEnterpriseEntity) {
                boxVo.setCustomer(new CustomerEnterpriseVo(
                        customerEnterpriseEntity.getId(),
                        customerEnterpriseEntity.getName()));
                boxVo.setCustomerName(customerEnterpriseEntity.getName());
            } else {
                boxVo.setCustomer(new CustomerEnterpriseVo(
                        0l, ""
                ));
                boxVo.setCustomerName("");
            }
        }

        if ((AccelerateMode.SIMPLE_MODE.getMode().equals(boxEntity.getAccelerateMode())) && (boxEntity.getEntryId() != null)) {
            EntryEnterpriseL2tpEntity l2tpEntity = entryEnterpriseL2tpDao.findOne(boxEntity.getEntryId());
            if (l2tpEntity != null) {
                boxVo.setUsedEntry(
                        new EntryEnterpriseSSGroupVo(
                                l2tpEntity.getId(),
                                l2tpEntity.getName(),
                                l2tpEntity.getName()
                        )
                );
            }
        } else if ((AccelerateMode.ADVANCED_MODE.getMode().equals(boxEntity.getAccelerateMode())) && (boxEntity.getEntryId() != null)) {
            EntryEnterpriseSSGroupEntity entryEntity = entryEnterpriseGroupSSDao.findOne(boxEntity.getEntryId());
            if (entryEntity != null) {
                boxVo.setUsedEntry(
                        new EntryEnterpriseSSGroupVo(
                                entryEntity.getId(),
                                entryEntity.getName(),
                                entryEntity.getDescription()
                        )
                );
            }
        }
        return boxVo;
    }

    private void updateAccelerationOfSextant(BoxEnterpriseBean boxBean) {
        BoxReq boxReq = new BoxReq();
        boxReq.setSpeedUp(boxBean.getSpeedUp());
        boxReq.setSpeedDown(boxBean.getSpeedDown());
        boxReq.setAccessUserName(boxBean.getAccessUserName());
        boxReq.setAccessPassword(boxBean.getAccessPassword());
        if (boxBean.getBoxAuth() != null) {
            boxReq.setBoxAuth(boxBean.getBoxAuth());
        }
        boxReq.setActiveCollectionList(boxBean.getActiveCollectionList());

        boxReq.setSsEntryList(new LinkedList<>());
        boxBean.getAdvancedEntryList().forEach(entryBean -> {
            EntryReq entryReq = new EntryReq();
            entryReq.setEntryId(entryBean.getId());
            boxReq.getSsEntryList().add(entryReq);
        });

        boxReq.setL2tpEntryList(new LinkedList<>());
        boxBean.getSimpleEntryList().forEach(entryEnterpriseL2TPBean -> {
            EntryReq entryReq = new EntryReq();
            entryReq.setEntryId(entryEnterpriseL2TPBean.getId());
            boxReq.getL2tpEntryList().add(entryReq);
        });

        BoxRes boxRes = boxIF.updateAccelerate(boxBean.getId(), boxReq);
        if (!boxRes.isSuccess()) {
            logger.error(String.format("Sextant-变更加速业务-变更加速业务失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ACCELERATION_CHANGE_FAIL);
        }
    }

}