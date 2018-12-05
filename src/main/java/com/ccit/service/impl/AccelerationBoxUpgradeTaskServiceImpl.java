package com.ccit.service.impl;

import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.AccelerationBoxUpgradeTaskBean;
import com.ccit.dao.AccelerationBoxUpgradePackageDao;
import com.ccit.dao.AccelerationBoxUpgradeTaskDao;
import com.ccit.dao.BoxEnterpriseDao;
import com.ccit.entity.AccelerationBoxUpgradePackageEntity;
import com.ccit.entity.AccelerationBoxUpgradeTaskEntity;
import com.ccit.entity.BoxEnterpriseEntity;
import com.ccit.enums.BoxUpgradeTaskStatus;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.sextant.interfaces.UpgradeIF;
import com.ccit.rest.sextant.request.BoxUpgradeTaskReq;
import com.ccit.rest.sextant.response.BoxUpgradeRes;
import com.ccit.service.BoxEnterpriseService;
import com.ccit.service.AccelerationBoxUpgradeTaskService;
import com.ccit.util.StringUtils;
import com.ccit.vo.AccelerationBoxUpgradePackageVo;
import com.ccit.vo.AccelerationBoxUpgradeTaskVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AccelerationBoxUpgradeTaskServiceImpl implements AccelerationBoxUpgradeTaskService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AccelerationBoxUpgradePackageDao accelerationBoxUpgradePackageDao;

    @Autowired
    private AccelerationBoxUpgradeTaskDao accelerationBoxUpgradeTaskDao;

    @Autowired
    private BoxEnterpriseService boxEnterpriseService;

    @Autowired
    private BoxEnterpriseDao boxEnterpriseDao;

    @Autowired
    @Qualifier("sextantUpgradeIF")
    private UpgradeIF upgradeIF;

    @Override
    public List<AccelerationBoxUpgradeTaskVo> listUpgradeTask() {
        List<AccelerationBoxUpgradeTaskEntity> accelerationBoxUpgradeTaskEntityList = accelerationBoxUpgradeTaskDao.findAll();

        List<AccelerationBoxUpgradeTaskVo> accelerationBoxUpgradeTaskVoList = new LinkedList<>();

        accelerationBoxUpgradeTaskEntityList.forEach(accelerationBoxUpgradeTaskEntity -> {
            accelerationBoxUpgradeTaskVoList.add(toVo(accelerationBoxUpgradeTaskEntity));
        });

        return accelerationBoxUpgradeTaskVoList;
    }

    @Override
    public AccelerationBoxUpgradeTaskVo getUpgradeTask(long taskId) {
        AccelerationBoxUpgradeTaskEntity accelerationBoxUpgradeTaskEntity = accelerationBoxUpgradeTaskDao.findOne(taskId);

        if (accelerationBoxUpgradeTaskEntity != null) {
            return toVo(accelerationBoxUpgradeTaskEntity);
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createUpgradeTask(AccelerationBoxUpgradeTaskBean accelerationBoxUpgradeTaskBean) {

        AccelerationBoxUpgradePackageEntity accelerationBoxUpgradePackageEntity = accelerationBoxUpgradePackageDao.findOne(accelerationBoxUpgradeTaskBean.getPackageId());

        Validator.notNull(accelerationBoxUpgradePackageEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_NOT_EXIST));

        long startTime = accelerationBoxUpgradeTaskBean.getStartTime();
        long endTime = accelerationBoxUpgradeTaskBean.getEndTime();

        Validator.isTrue((startTime - System.currentTimeMillis()) > 0, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_START_TIME_ILLEGAL));
        Validator.isTrue((endTime - startTime) >= 1000 * 60 * 60, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_START_END_TIME_ILLEGAL));

        List<AccelerationBoxUpgradeTaskEntity> boxUpgradeTaskEntityList = new LinkedList<>();

        for (Long boxId : accelerationBoxUpgradeTaskBean.getBoxIdList()) {
            BoxEnterpriseEntity enterpriseEntity = boxEnterpriseDao.findOne(boxId);
            if (enterpriseEntity != null) {
                AccelerationBoxUpgradeTaskEntity accelerationBoxUpgradeTaskEntity = accelerationBoxUpgradeTaskDao.findByBoxIdPackageId(boxId, accelerationBoxUpgradeTaskBean.getPackageId());

                if (accelerationBoxUpgradeTaskEntity == null) {
                    accelerationBoxUpgradeTaskEntity = new AccelerationBoxUpgradeTaskEntity();
                    accelerationBoxUpgradeTaskEntity.setBoxId(boxId);
                    accelerationBoxUpgradeTaskEntity.setPackageId(accelerationBoxUpgradeTaskBean.getPackageId());
                    accelerationBoxUpgradeTaskEntity.setStartTime(new Timestamp(startTime));
                    accelerationBoxUpgradeTaskEntity.setEndTime(new Timestamp(endTime));
                    accelerationBoxUpgradeTaskEntity.setStatus(BoxUpgradeTaskStatus.WAIT_UPGRADE.getStatus());
                } else if (accelerationBoxUpgradeTaskEntity != null && BoxUpgradeTaskStatus.UPGRADE_FAIL.getStatus().equals(accelerationBoxUpgradeTaskEntity.getStatus())) {
                    accelerationBoxUpgradeTaskEntity.setStartTime(new Timestamp(startTime));
                    accelerationBoxUpgradeTaskEntity.setEndTime(new Timestamp(endTime));
                    accelerationBoxUpgradeTaskEntity.setStatus(BoxUpgradeTaskStatus.WAIT_UPGRADE.getStatus());
                } else {
                    continue;
                }
                accelerationBoxUpgradeTaskDao.saveOrUpdate(accelerationBoxUpgradeTaskEntity);
                boxUpgradeTaskEntityList.add(accelerationBoxUpgradeTaskEntity);

                startTime += TimeUnit.MINUTES.toMillis(5);
                endTime += TimeUnit.MINUTES.toMillis(5);
            }
        }

        updateUpgradeTaskOfSextant(boxUpgradeTaskEntityList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateUpgradeTask(AccelerationBoxUpgradeTaskBean accelerationBoxUpgradeTaskBean) {
        AccelerationBoxUpgradeTaskEntity accelerationBoxUpgradeTaskEntity = accelerationBoxUpgradeTaskDao.findOne(accelerationBoxUpgradeTaskBean.getId());
        Validator.notNull(accelerationBoxUpgradeTaskEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_NOT_EXIST));

        Validator.isTrue(!BoxUpgradeTaskStatus.UPGRADING.getStatus().equals(accelerationBoxUpgradeTaskEntity.getStatus()), ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_UPGRADING));
        Validator.isTrue(!BoxUpgradeTaskStatus.UPGRADE_SUCCESS.getStatus().equals(accelerationBoxUpgradeTaskEntity.getStatus()), ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_UPGRADE_SUCCESS));

        BoxEnterpriseEntity boxEnterpriseEntity = boxEnterpriseDao.findOne(accelerationBoxUpgradeTaskBean.getBoxIdList().get(0));
        Validator.notNull(boxEnterpriseEntity, ResourceException.error(CustomerErrorConstants.BOX_ENTERPRISE_NOT_EXIST));

        AccelerationBoxUpgradePackageEntity accelerationBoxUpgradePackageEntity = accelerationBoxUpgradePackageDao.findOne(accelerationBoxUpgradeTaskBean.getPackageId());

        Validator.notNull(accelerationBoxUpgradePackageEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_NOT_EXIST));

        Validator.isTrue((accelerationBoxUpgradeTaskBean.getStartTime() - System.currentTimeMillis()) > 0, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_START_TIME_ILLEGAL));
        Validator.isTrue((accelerationBoxUpgradeTaskBean.getEndTime() - accelerationBoxUpgradeTaskBean.getStartTime()) >= 1000 * 60 * 60, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_START_END_TIME_ILLEGAL));

        accelerationBoxUpgradeTaskEntity.setPackageId(accelerationBoxUpgradePackageEntity.getId());
        accelerationBoxUpgradeTaskEntity.setStartTime(new Timestamp(accelerationBoxUpgradeTaskBean.getStartTime()));
        accelerationBoxUpgradeTaskEntity.setEndTime(new Timestamp(accelerationBoxUpgradeTaskBean.getEndTime()));

        if (BoxUpgradeTaskStatus.UPGRADE_FAIL.getStatus().equals(accelerationBoxUpgradeTaskEntity.getStatus())) {
            accelerationBoxUpgradeTaskEntity.setStatus(BoxUpgradeTaskStatus.WAIT_UPGRADE.getStatus());
        }

        accelerationBoxUpgradeTaskDao.saveOrUpdate(accelerationBoxUpgradeTaskEntity);

        List<AccelerationBoxUpgradeTaskEntity> boxUpgradeTaskEntityList = new LinkedList<>();
        boxUpgradeTaskEntityList.add(accelerationBoxUpgradeTaskEntity);
        updateUpgradeTaskOfSextant(boxUpgradeTaskEntityList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateUpgradeTaskStatus(AccelerationBoxUpgradeTaskBean accelerationBoxUpgradeTaskBean) {
        AccelerationBoxUpgradeTaskEntity accelerationBoxUpgradeTaskEntity = accelerationBoxUpgradeTaskDao.findOne(accelerationBoxUpgradeTaskBean.getId());
        Validator.notNull(accelerationBoxUpgradeTaskEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_NOT_EXIST));

        accelerationBoxUpgradeTaskEntity.setStatus(accelerationBoxUpgradeTaskBean.getStatus());
        accelerationBoxUpgradeTaskDao.saveOrUpdate(accelerationBoxUpgradeTaskEntity);

        if (!StringUtils.isNull(accelerationBoxUpgradeTaskBean.getVersion())) {
            BoxEnterpriseEntity boxEnterpriseEntity = boxEnterpriseDao.findOne(accelerationBoxUpgradeTaskEntity.getBoxId());
            Validator.notNull(boxEnterpriseEntity, ResourceException.error(CustomerErrorConstants.BOX_ENTERPRISE_NOT_EXIST));

            boxEnterpriseEntity.setSoftwareVersion(accelerationBoxUpgradeTaskBean.getVersion());
            boxEnterpriseDao.saveOrUpdate(boxEnterpriseEntity);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeUpgradeTask(long taskId) {
        AccelerationBoxUpgradeTaskEntity accelerationBoxUpgradeTaskEntity = accelerationBoxUpgradeTaskDao.findOne(taskId);
        if (accelerationBoxUpgradeTaskEntity != null) {
            accelerationBoxUpgradeTaskDao.deleteById(taskId);
            removeUpgradeTaskOfSextant(taskId);
        }
    }

    private AccelerationBoxUpgradeTaskVo toVo(AccelerationBoxUpgradeTaskEntity accelerationBoxUpgradeTaskEntity) {
        AccelerationBoxUpgradeTaskVo accelerationBoxUpgradeTaskVo = new AccelerationBoxUpgradeTaskVo(
                accelerationBoxUpgradeTaskEntity.getId(),
                accelerationBoxUpgradeTaskEntity.getStatus(),
                accelerationBoxUpgradeTaskEntity.getTaskType(),
                accelerationBoxUpgradeTaskEntity.getStartTime(),
                accelerationBoxUpgradeTaskEntity.getEndTime()
        );

        AccelerationBoxUpgradePackageEntity accelerationBoxUpgradePackageEntity = accelerationBoxUpgradePackageDao.findOne(accelerationBoxUpgradeTaskEntity.getPackageId());

        if (accelerationBoxUpgradePackageEntity != null) {
            AccelerationBoxUpgradePackageVo packageVo = new AccelerationBoxUpgradePackageVo(
                    accelerationBoxUpgradePackageEntity.getId(),
                    accelerationBoxUpgradePackageEntity.getPackageVersion(),
                    accelerationBoxUpgradePackageEntity.getSuitableVersion(),
                    accelerationBoxUpgradePackageEntity.getFileName(),
                    accelerationBoxUpgradePackageEntity.getCreateTime()
            );
            accelerationBoxUpgradeTaskVo.setPackageVo(packageVo);
        }

        accelerationBoxUpgradeTaskVo.setBoxEnterpriseVo(boxEnterpriseService.getById(accelerationBoxUpgradeTaskEntity.getBoxId()));

        return accelerationBoxUpgradeTaskVo;
    }

    private void updateUpgradeTaskOfSextant(List<AccelerationBoxUpgradeTaskEntity> boxUpgradeTaskEntityList) {
        List<BoxUpgradeTaskReq> upgradeReqList = new LinkedList<>();

        boxUpgradeTaskEntityList.forEach(boxUpgradeTaskEntity -> {
            upgradeReqList.add(
                    new BoxUpgradeTaskReq(boxUpgradeTaskEntity)
            );
        });

        BoxUpgradeRes boxUpgradeRes = upgradeIF.updateUpgradeTask(upgradeReqList);
        if (!boxUpgradeRes.isSuccess()) {
            logger.error(String.format("Sextant-更新企业升级任务-同步失败，错误码：%s，错误信息：%s",
                    boxUpgradeRes.getErrorBody().getErrorCode(),
                    boxUpgradeRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_ENTERPRISE_SYNCHRONIZATION_FAIL);
        }
    }

    private void removeUpgradeTaskOfSextant(long id) {
        BoxUpgradeRes boxUpgradeRes = upgradeIF.removeUpgradeTask(id);
        if (!boxUpgradeRes.isSuccess()) {
            logger.error(String.format("Sextant-删除企业升级任务-同步失败，错误码：%s，错误信息：%s",
                    boxUpgradeRes.getErrorBody().getErrorCode(),
                    boxUpgradeRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_ENTERPRISE_SYNCHRONIZATION_FAIL);
        }
    }
}