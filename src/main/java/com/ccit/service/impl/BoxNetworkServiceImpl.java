package com.ccit.service.impl;

import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.BoxNetworkBean;
import com.ccit.dao.BoxNetworkDao;
import com.ccit.dao.CustomerEnterpriseDao;
import com.ccit.entity.BoxNetworkEntity;
import com.ccit.entity.CustomerEnterpriseEntity;
import com.ccit.enums.BoxStatusEnum;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.axis.interfaces.BoxIF;
import com.ccit.rest.axis.request.BoxReq;
import com.ccit.rest.axis.response.BoxRes;
import com.ccit.service.BoxNetworkService;
import com.ccit.service.ResourceService;
import com.ccit.util.StringUtils;
import com.ccit.vo.BoxNetworkVo;
import com.ccit.vo.CustomerEnterpriseVo;
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
public class BoxNetworkServiceImpl implements BoxNetworkService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private BoxNetworkDao boxNetworkDao;

    @Autowired
    private CustomerEnterpriseDao customerEnterpriseDao;

    @Autowired
    @Qualifier("axisBoxIF")
    private BoxIF boxIF;

    @Autowired
    private ResourceService resourceService;

    public List<BoxNetworkVo> listBox(String sn, String customerNetworkName, String sort) {
        List<BoxNetworkVo> boxVoList = new LinkedList<>();
        List<BoxNetworkEntity> boxEntityList = boxNetworkDao.listBox(sn, customerNetworkName, sort);

        boxEntityList.forEach(boxEntity -> {
            boxVoList.add(toVo(boxEntity));
        });
        return boxVoList;
    }

    public BoxNetworkVo getById(long id) {
        BoxNetworkEntity boxEntity = boxNetworkDao.findOne(id);
        Validator.notNull(boxEntity, ResourceException.error(CustomerErrorConstants.BOX_NETWORK_NOT_EXIST));

        return toVo(boxEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveBox(BoxNetworkBean boxBean) {
        // 序列号check
        assertSnValid(null, boxBean.getSn());
        BoxNetworkEntity boxEntity = new BoxNetworkEntity(
                boxBean.getCreateUid(),
                boxBean.getManufacturer(),
                boxBean.getModel(),
                boxBean.getSn(),
                boxBean.getWanCount(),
                boxBean.getHardwareVersion(),
                boxBean.getSoftwareVersion(),
                boxBean.getServerAddr(),
                boxBean.getServerPort()
        );
        boxEntity.setStatus(BoxStatusEnum.STORE.getCode());
        boxEntity.setCreateUid(UserInterceptor.getLocalUser().getId());
        boxEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        boxNetworkDao.saveOrUpdate(boxEntity);

        // axis上新建盒子记录
        updateBoxOfAxis(boxEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateBoxPart(BoxNetworkBean boxBean) {
        BoxNetworkEntity boxEntity = boxNetworkDao.findOne(boxBean.getId());
        Validator.notNull(boxEntity, BusinessException.error(CustomerErrorConstants.BOX_NETWORK_NOT_EXIST));

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
        if (StringUtils.isNotBlank(boxBean.getWan2IP())) {
            boxEntity.setWan2IP(boxBean.getWan2IP());
        }
        if (StringUtils.isNotBlank(boxBean.getWan2GW())) {
            boxEntity.setWan2GW(boxBean.getWan2GW());
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
        boxNetworkDao.saveOrUpdate(boxEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeBox(long id) {
        BoxNetworkEntity boxEntity = boxNetworkDao.findOne(id);
        if (null != boxEntity) {
            resourceService.check(boxEntity);

            String delTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            boxEntity.setSerialNumber(boxEntity.getSerialNumber() + "_" + delTime);
            boxEntity.setRemoved(true);
            boxNetworkDao.saveOrUpdate(boxEntity);

            removeBoxOfAxis(boxEntity.getId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateBoxPart(List<BoxNetworkBean> boxNetworkBeanList) {
        boxNetworkDao.updateAllStatus(BoxStatusEnum.OFFLINE);
        for (BoxNetworkBean boxNetworkBean : boxNetworkBeanList) {
            try {
                updateBoxPart(boxNetworkBean);
            } catch (Exception e) {
                logger.error("盒子信息更新错误：盒子id=" + boxNetworkBean.getId(), e);
            }
        }
    }

    // 序列号重复抛异常
    private void assertSnValid(String oldSn, String newSn) throws BusinessException {
        if (StringUtils.equals(oldSn, newSn)) {
            return;
        }
        int count = boxNetworkDao.countBySn(newSn);
        if (count > 0) {
            throw new BusinessException(CustomerErrorConstants.BOX_NETWORK_SN_REPEAT);
        }
    }

    // Axis盒子更新
    private void updateBoxOfAxis(BoxNetworkEntity boxEntity) {
        BoxReq boxReq = new BoxReq();

        boxReq.setSn(boxEntity.getSerialNumber());
        boxReq.setHardwareVersion(boxEntity.getHardwareVersion());
        boxReq.setSoftwareVersion(boxEntity.getSoftwareVersion());
        boxReq.setWanCount(boxEntity.getWanCount());
        boxReq.setServerAddr(boxEntity.getServerAddr());
        boxReq.setServerPort(boxEntity.getServerPort());

        BoxRes boxRes = boxIF.updateBox(boxEntity.getId(), boxReq);
        if (!boxRes.isSuccess()) {
            logger.error(String.format("Sextant-更新盒子-同步盒子失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_NETWORK_SYNCHRONIZATION_FAIL);
        }
    }

    // Axis盒子删除
    public void removeBoxOfAxis(long id) {
        BoxRes boxRes = boxIF.removeBox(id);
        if (!boxRes.isSuccess()) {
            logger.error(String.format("Sextant-删除盒子-同步盒子失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_NETWORK_SYNCHRONIZATION_FAIL);
        }
    }

    private BoxNetworkVo toVo(BoxNetworkEntity entity) {
        BoxNetworkVo vo = new BoxNetworkVo(
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
                entity.getServerPort(),
                entity.getWan2IP(),
                entity.getWan2GW()
        );

        CustomerEnterpriseEntity customerEnterpriseEntity = null;
        if (null != entity.getCustomerId()) {
            customerEnterpriseEntity = customerEnterpriseDao.findOne(entity.getCustomerId());
        }

        if (customerEnterpriseEntity != null) {
            vo.setCustomer(new CustomerEnterpriseVo(customerEnterpriseEntity.getId(), customerEnterpriseEntity.getSextantName()));
        } else {
            vo.setCustomer(new CustomerEnterpriseVo(0l, ""));
        }
        return vo;
    }

}