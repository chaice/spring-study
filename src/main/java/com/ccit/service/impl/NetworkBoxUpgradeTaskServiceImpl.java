package com.ccit.service.impl;

import com.ccit.dao.BoxNetworkDao;
import com.ccit.dao.NetworkBoxUpgradePackageDao;
import com.ccit.dao.NetworkBoxUpgradeTaskDao;
import com.ccit.entity.BoxNetworkEntity;
import com.ccit.entity.NetworkBoxUpgradePackageEntity;
import com.ccit.entity.NetworkBoxUpgradeTaskEntity;
import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.NetworkBoxUpgradeTaskBean;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.enums.BoxUpgradeTaskStatus;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.axis.request.BoxUpgradeTaskReq;
import com.ccit.rest.axis.interfaces.UpgradeIF;
import com.ccit.rest.axis.response.BoxUpgradeRes;
import com.ccit.service.BoxNetworkService;
import com.ccit.service.NetworkBoxUpgradeTaskService;
import com.ccit.util.StringUtils;
import com.ccit.vo.NetworkBoxUpgradePackageVo;
import com.ccit.vo.NetworkBoxUpgradeTaskVo;
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
public class NetworkBoxUpgradeTaskServiceImpl implements NetworkBoxUpgradeTaskService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private NetworkBoxUpgradePackageDao networkBoxUpgradePackageDao;

    @Autowired
    private NetworkBoxUpgradeTaskDao networkBoxUpgradeTaskDao;

    @Autowired
    private BoxNetworkService boxNetworkService;

    @Autowired
    private BoxNetworkDao boxNetworkDao;

    @Autowired
    @Qualifier("axisUpgradeIF")
    private UpgradeIF upgradeIF;

    @Override
    public List<NetworkBoxUpgradeTaskVo> listUpgradeTask() {
        List<NetworkBoxUpgradeTaskEntity> networkBoxUpgradeTaskEntityList = networkBoxUpgradeTaskDao.findAll();

        List<NetworkBoxUpgradeTaskVo> accelerationBoxUpgradeTaskVoList = new LinkedList<>();

        networkBoxUpgradeTaskEntityList.forEach(networkBoxUpgradeTaskEntity -> {
            accelerationBoxUpgradeTaskVoList.add(toVo(networkBoxUpgradeTaskEntity));
        });

        return accelerationBoxUpgradeTaskVoList;
    }

    @Override
    public NetworkBoxUpgradeTaskVo getUpgradeTask(long taskId) {
        NetworkBoxUpgradeTaskEntity networkBoxUpgradeTaskEntity = networkBoxUpgradeTaskDao.findOne(taskId);

        if (networkBoxUpgradeTaskEntity != null) {
            return toVo(networkBoxUpgradeTaskEntity);
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createUpgradeTask(NetworkBoxUpgradeTaskBean networkBoxUpgradeTaskBean) {

        NetworkBoxUpgradePackageEntity networkBoxUpgradePackageEntity = networkBoxUpgradePackageDao.findOne(networkBoxUpgradeTaskBean.getPackageId());

        Validator.notNull(networkBoxUpgradePackageEntity, ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_PACKAGE_NOT_EXIST));

        long startTime = networkBoxUpgradeTaskBean.getStartTime();
        long endTime = networkBoxUpgradeTaskBean.getEndTime();

        Validator.isTrue((startTime - System.currentTimeMillis()) > 0, ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_START_TIME_ILLEGAL));
        Validator.isTrue((endTime - startTime) >= 1000 * 60 * 60, ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_START_END_TIME_ILLEGAL));

        List<NetworkBoxUpgradeTaskEntity> boxUpgradeTaskEntityList = new LinkedList<>();

        for (Long boxId : networkBoxUpgradeTaskBean.getBoxIdList()) {
            BoxNetworkEntity boxNetworkEntity = boxNetworkDao.findOne(boxId);
            if (boxNetworkEntity != null) {
                NetworkBoxUpgradeTaskEntity networkBoxUpgradeTaskEntity = networkBoxUpgradeTaskDao.findByBoxIdPackageId(boxId, networkBoxUpgradeTaskBean.getPackageId());

                if (networkBoxUpgradeTaskEntity == null) {
                    networkBoxUpgradeTaskEntity = new NetworkBoxUpgradeTaskEntity();
                    networkBoxUpgradeTaskEntity.setBoxId(boxId);
                    networkBoxUpgradeTaskEntity.setPackageId(networkBoxUpgradeTaskBean.getPackageId());
                    networkBoxUpgradeTaskEntity.setStartTime(new Timestamp(startTime));
                    networkBoxUpgradeTaskEntity.setEndTime(new Timestamp(endTime));
                    networkBoxUpgradeTaskEntity.setStatus(BoxUpgradeTaskStatus.WAIT_UPGRADE.getStatus());
                } else if (networkBoxUpgradeTaskEntity != null && BoxUpgradeTaskStatus.UPGRADE_FAIL.getStatus().equals(networkBoxUpgradeTaskEntity.getStatus())) {
                    networkBoxUpgradeTaskEntity.setStartTime(new Timestamp(startTime));
                    networkBoxUpgradeTaskEntity.setEndTime(new Timestamp(endTime));
                    networkBoxUpgradeTaskEntity.setStatus(BoxUpgradeTaskStatus.WAIT_UPGRADE.getStatus());
                } else {
                    continue;
                }
                networkBoxUpgradeTaskDao.saveOrUpdate(networkBoxUpgradeTaskEntity);
                boxUpgradeTaskEntityList.add(networkBoxUpgradeTaskEntity);

                startTime += TimeUnit.MINUTES.toMillis(5);
                endTime += TimeUnit.MINUTES.toMillis(5);
            }
        }

        updateUpgradeTaskOfAxis(boxUpgradeTaskEntityList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateUpgradeTask(NetworkBoxUpgradeTaskBean networkBoxUpgradeTaskBean) {
        NetworkBoxUpgradeTaskEntity networkBoxUpgradeTaskEntity = networkBoxUpgradeTaskDao.findOne(networkBoxUpgradeTaskBean.getId());
        Validator.notNull(networkBoxUpgradeTaskEntity, ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_NOT_EXIST));

        Validator.isTrue(!BoxUpgradeTaskStatus.UPGRADING.getStatus().equals(networkBoxUpgradeTaskEntity.getStatus()), ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_UPGRADING));
        Validator.isTrue(!BoxUpgradeTaskStatus.UPGRADE_SUCCESS.getStatus().equals(networkBoxUpgradeTaskEntity.getStatus()), ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_UPGRADE_SUCCESS));

        BoxNetworkEntity boxNetworkEntity = boxNetworkDao.findOne(networkBoxUpgradeTaskBean.getBoxIdList().get(0));
        Validator.notNull(boxNetworkEntity, ResourceException.error(CustomerErrorConstants.BOX_NETWORK_NOT_EXIST));

        NetworkBoxUpgradePackageEntity networkBoxUpgradePackageEntity = networkBoxUpgradePackageDao.findOne(networkBoxUpgradeTaskBean.getPackageId());

        Validator.notNull(networkBoxUpgradePackageEntity, ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_PACKAGE_NOT_EXIST));

        Validator.isTrue((networkBoxUpgradeTaskBean.getStartTime() - System.currentTimeMillis()) > 0, ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_START_TIME_ILLEGAL));
        Validator.isTrue((networkBoxUpgradeTaskBean.getEndTime() - networkBoxUpgradeTaskBean.getStartTime()) >= 1000 * 60 * 60, ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_START_END_TIME_ILLEGAL));

        networkBoxUpgradeTaskEntity.setPackageId(networkBoxUpgradeTaskBean.getPackageId());
        networkBoxUpgradeTaskEntity.setStartTime(new Timestamp(networkBoxUpgradeTaskBean.getStartTime()));
        networkBoxUpgradeTaskEntity.setEndTime(new Timestamp(networkBoxUpgradeTaskBean.getEndTime()));

        if (BoxUpgradeTaskStatus.UPGRADE_FAIL.getStatus().equals(networkBoxUpgradeTaskEntity.getStatus())) {
            networkBoxUpgradeTaskEntity.setStatus(BoxUpgradeTaskStatus.WAIT_UPGRADE.getStatus());
        }

        networkBoxUpgradeTaskDao.saveOrUpdate(networkBoxUpgradeTaskEntity);

        List<NetworkBoxUpgradeTaskEntity> boxUpgradeTaskEntityList = new LinkedList<>();
        boxUpgradeTaskEntityList.add(networkBoxUpgradeTaskEntity);
        updateUpgradeTaskOfAxis(boxUpgradeTaskEntityList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateUpgradeTaskStatus(NetworkBoxUpgradeTaskBean networkBoxUpgradeTaskBean) {
        NetworkBoxUpgradeTaskEntity networkBoxUpgradeTaskEntity = networkBoxUpgradeTaskDao.findOne(networkBoxUpgradeTaskBean.getId());
        Validator.notNull(networkBoxUpgradeTaskEntity, ResourceException.error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_NOT_EXIST));

        networkBoxUpgradeTaskEntity.setStatus(networkBoxUpgradeTaskBean.getStatus());
        networkBoxUpgradeTaskDao.saveOrUpdate(networkBoxUpgradeTaskEntity);

        if (!StringUtils.isNull(networkBoxUpgradeTaskBean.getVersion())) {
            BoxNetworkEntity boxNetworkEntity = boxNetworkDao.findOne(networkBoxUpgradeTaskEntity.getBoxId());
            Validator.notNull(boxNetworkEntity, ResourceException.error(CustomerErrorConstants.BOX_NETWORK_NOT_EXIST));

            boxNetworkEntity.setSoftwareVersion(networkBoxUpgradeTaskBean.getVersion());
            boxNetworkDao.saveOrUpdate(boxNetworkEntity);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeUpgradeTask(long taskId) {
        NetworkBoxUpgradeTaskEntity networkBoxUpgradeTaskEntity = networkBoxUpgradeTaskDao.findOne(taskId);
        if (networkBoxUpgradeTaskEntity != null) {
            networkBoxUpgradeTaskDao.deleteById(taskId);
            removeUpgradeTaskOfAxis(taskId);
        }
    }

    private NetworkBoxUpgradeTaskVo toVo(NetworkBoxUpgradeTaskEntity networkBoxUpgradeTaskEntity) {
        NetworkBoxUpgradeTaskVo networkBoxUpgradeTaskVo = new NetworkBoxUpgradeTaskVo(
                networkBoxUpgradeTaskEntity.getId(),
                networkBoxUpgradeTaskEntity.getStatus(),
                networkBoxUpgradeTaskEntity.getTaskType(),
                networkBoxUpgradeTaskEntity.getStartTime(),
                networkBoxUpgradeTaskEntity.getEndTime()
        );

        NetworkBoxUpgradePackageEntity networkBoxUpgradePackageEntity = networkBoxUpgradePackageDao.findOne(networkBoxUpgradeTaskEntity.getPackageId());

        if (networkBoxUpgradePackageEntity != null) {
            NetworkBoxUpgradePackageVo packageVo = new NetworkBoxUpgradePackageVo(
                    networkBoxUpgradePackageEntity.getId(),
                    networkBoxUpgradePackageEntity.getPackageVersion(),
                    networkBoxUpgradePackageEntity.getSuitableVersion(),
                    networkBoxUpgradePackageEntity.getFileName(),
                    networkBoxUpgradePackageEntity.getCreateTime()
            );
            networkBoxUpgradeTaskVo.setPackageVo(packageVo);
        }

        networkBoxUpgradeTaskVo.setBoxNetworkVo(boxNetworkService.getById(networkBoxUpgradeTaskEntity.getBoxId()));

        return networkBoxUpgradeTaskVo;
    }

    private void updateUpgradeTaskOfAxis(List<NetworkBoxUpgradeTaskEntity> boxUpgradeTaskEntityList) {
        List<BoxUpgradeTaskReq> upgradeReqList = new LinkedList<>();

        boxUpgradeTaskEntityList.forEach(boxUpgradeTaskEntity -> {
            upgradeReqList.add(
                    new BoxUpgradeTaskReq(boxUpgradeTaskEntity)
            );
        });

        BoxUpgradeRes boxUpgradeRes = upgradeIF.updateUpgradeTask(upgradeReqList);
        if (!boxUpgradeRes.isSuccess()) {
            logger.error(String.format("Axis-更新组网升级任务-同步失败，错误码：%s，错误信息：%s",
                    boxUpgradeRes.getErrorBody().getErrorCode(),
                    boxUpgradeRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_NETWORK_SYNCHRONIZATION_FAIL);
        }
    }

    private void removeUpgradeTaskOfAxis(long id) {
        BoxUpgradeRes boxUpgradeRes = upgradeIF.removeUpgradeTask(id);
        if (!boxUpgradeRes.isSuccess()) {
            logger.error(String.format("Axis-删除组网升级任务-同步失败，错误码：%s，错误信息：%s",
                    boxUpgradeRes.getErrorBody().getErrorCode(),
                    boxUpgradeRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.BOX_NETWORK_SYNCHRONIZATION_FAIL);
        }
    }
}