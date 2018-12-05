package com.ccit.service.impl;

import com.ccit.dao.BoxEntryNetworkDao;
import com.ccit.dao.BoxNetworkDao;
import com.ccit.dao.CustomerEnterpriseDao;
import com.ccit.dao.EntryNetworkDao;
import com.ccit.entity.BoxEntryNetworkEntity;
import com.ccit.entity.BoxNetworkEntity;
import com.ccit.entity.CustomerEnterpriseEntity;
import com.ccit.entity.EntryNetworkEntity;
import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.BoxNetworkBean;
import com.ccit.bean.EntryNetworkBean;
import com.ccit.vo.BoxNetworkVo;
import com.ccit.vo.CustomerEnterpriseVo;
import com.ccit.vo.EntryNetworkVo;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.axis.interfaces.BoxIF;
import com.ccit.rest.axis.request.BoxReq;
import com.ccit.rest.axis.request.EntryReq;
import com.ccit.rest.axis.response.BoxRes;
import com.ccit.service.NetworkBusinessService;
import com.ccit.vo.*;
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
public class NetworkBusinessServiceImpl implements NetworkBusinessService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private BoxNetworkDao boxNetworkDao;

    @Autowired
    private BoxEntryNetworkDao boxEntryNetworkDao;

    @Autowired
    private EntryNetworkDao entryNetworkDao;

    @Autowired
    private CustomerEnterpriseDao customerEnterpriseDao;

    @Autowired
    @Qualifier("axisBoxIF")
    private BoxIF boxIF;

    public List<BoxNetworkVo> listBusiness() {
        List<BoxNetworkVo> boxVos = new LinkedList<>();
        List<BoxNetworkEntity> boxEntities = boxNetworkDao.listBox(null, null, null);
        boxEntities.forEach(boxEntity -> {
            List<BoxEntryNetworkEntity> boxEntryNetworkEntityList = boxEntryNetworkDao.listByBoxId(boxEntity.getId());
            List<EntryNetworkVo> activeEntryList = new LinkedList<>();
            boxEntryNetworkEntityList.forEach(boxEntryNetworkEntity -> {
                EntryNetworkEntity entryEntity = entryNetworkDao.findOne(boxEntryNetworkEntity.getEntryId());
                activeEntryList.add(
                        new EntryNetworkVo(
                                entryEntity.getId(),
                                entryEntity.getName(),
                                entryEntity.getMasterIP(),
                                entryEntity.getSlaveIP())
                );
            });
            BoxNetworkVo boxVo = toVo(boxEntity);
            boxVo.setActiveEntryList(activeEntryList);

            boxVos.add(boxVo);
        });
        return boxVos;
    }


    public BoxNetworkVo getBusiness(long boxId) {
        BoxNetworkEntity boxEntity = boxNetworkDao.findOne(boxId);
        Validator.notNull(boxEntity, ResourceException.error(CustomerErrorConstants.BOX_NETWORK_NOT_EXIST));

        //盒子已有入口列表
        List<BoxEntryNetworkEntity> boxEntryNetworkEntityList = boxEntryNetworkDao.listByBoxId(boxId);
        List<EntryNetworkVo> activeEntryList = new LinkedList<>();
        boxEntryNetworkEntityList.forEach(boxEntryNetworkEntity -> {
            EntryNetworkEntity entryEntity = entryNetworkDao.findOne(boxEntryNetworkEntity.getEntryId());
            activeEntryList.add(
                    new EntryNetworkVo(
                            entryEntity.getId(),
                            entryEntity.getName(),
                            entryEntity.getMasterIP(),
                            entryEntity.getSlaveIP())
            );
        });

        BoxNetworkVo boxVo = toVo(boxEntity);
        boxVo.setActiveEntryList(activeEntryList);
        return boxVo;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateBusiness(BoxNetworkBean boxBean) {
        BoxNetworkEntity boxEntity = boxNetworkDao.findOne(boxBean.getId());
        Validator.notNull(boxEntity, BusinessException.error(CustomerErrorConstants.BOX_NETWORK_NOT_EXIST));

        boxEntity.setEnable(Boolean.TRUE);
        boxEntity.setEntryId(boxBean.getUsedEntry().getId());
        boxEntity.setAccessUserName(boxBean.getAccessUserName());
        boxEntity.setAccessPassword(boxBean.getAccessPassword());

        boxNetworkDao.saveOrUpdate(boxEntity);

        boxEntryNetworkDao.deleteByBoxId(boxBean.getId());

        boolean entryCheck = false;
        for (EntryNetworkBean entryBean : boxBean.getActiveEntryList()) {
            BoxEntryNetworkEntity boxEntryNetworkEntity = new BoxEntryNetworkEntity();
            boxEntryNetworkEntity.setEntryId(entryBean.getId());
            boxEntryNetworkEntity.setBoxId(boxEntity.getId());
            boxEntryNetworkEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            boxEntryNetworkDao.saveOrUpdate(boxEntryNetworkEntity);

            if (boxEntity.getEntryId() != null && boxEntity.getEntryId() != 0L && boxEntity.getEntryId().equals(boxEntryNetworkEntity.getEntryId())) {
                entryCheck = true;
            }
        }

        if (boxEntity.getEntryId() != null && boxEntity.getEntryId() != 0L && !entryCheck) {
            throw BusinessException.error(CustomerErrorConstants.NETWORK_USED_ENTRY_SHOULD_NOT_BE_REMOVED);
        }

        updateBusinessOfAxis(boxBean);
    }


    private BoxNetworkVo toVo(BoxNetworkEntity boxEntity) {
        BoxNetworkVo boxVo = new BoxNetworkVo(
                boxEntity.getId(),
                boxEntity.getSerialNumber(),
                boxEntity.getAlias(),
                boxEntity.getSpeedUp(),
                boxEntity.getSpeedDown(),
                boxEntity.getEnable()
        );
        boxVo.setModel(boxEntity.getModel());
        boxVo.setAccessUserName(boxEntity.getAccessUserName());
        boxVo.setAccessPassword(boxEntity.getAccessPassword());


        if (null != boxEntity.getCustomerId()) {
            CustomerEnterpriseEntity customerEnterpriseEntity = customerEnterpriseDao.findOne(boxEntity.getCustomerId());
            if (null != customerEnterpriseEntity) {
                boxVo.setCustomer(new CustomerEnterpriseVo(
                        customerEnterpriseEntity.getId(),
                        customerEnterpriseEntity.getName()));
            } else {
                boxVo.setCustomer(new CustomerEnterpriseVo(
                        0l, ""
                ));
            }
        }

        if (null != boxEntity.getEntryId()) {
            EntryNetworkEntity entryEntity = entryNetworkDao.findOne(boxEntity.getEntryId());
            if (null != entryEntity) {
                boxVo.setUsedEntry(
                        new EntryNetworkVo(
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


    private void updateBusinessOfAxis(BoxNetworkBean boxBean) {
        BoxReq boxReq = new BoxReq();
        boxReq.setEntryList(new LinkedList<>());
        boxReq.setAccessUserName(boxBean.getAccessUserName());
        boxReq.setEntryId(boxBean.getUsedEntry().getId());
        boxReq.setAccessPassword(boxBean.getAccessPassword());

        boxBean.getActiveEntryList().forEach(entryBean -> {
            EntryReq entryReq = new EntryReq();
            entryReq.setEntryId(entryBean.getId());
            boxReq.getEntryList().add(entryReq);
        });

        BoxRes boxRes = boxIF.updateBusinessBox(boxBean.getId(), boxReq);
        if (!boxRes.isSuccess()) {
            logger.error(String.format("Axis-变更组网业务-变更组网业务失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.NETWORK_CHANGE_FAIL);
        }
    }

}