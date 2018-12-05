package com.ccit.service.impl;

import com.ccit.assembler.EntryNetworkAssembler;
import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.service.EntryNetworkService;
import com.ccit.bean.EntryNetworkBean;
import com.ccit.dao.EntryNetworkDao;
import com.ccit.entity.EntryNetworkEntity;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.axis.interfaces.EntryIF;
import com.ccit.rest.axis.request.EntryReq;
import com.ccit.rest.axis.response.EntryRes;
import com.ccit.service.ResourceService;
import com.ccit.util.StringUtils;
import com.ccit.vo.EntryNetworkVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class EntryNetworkServiceImpl implements EntryNetworkService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private EntryNetworkDao entryNetworkDao;

    @Autowired
    private EntryNetworkAssembler entryNetworkAssembler;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    @Qualifier("axisEntryIF")
    private EntryIF axisEntryIF;

    public List<EntryNetworkVo> searchEntry() {
        List<EntryNetworkEntity> entryEntities = entryNetworkDao.searchEntry();
        List<EntryNetworkVo> entryVos = entryNetworkAssembler.toVos(entryEntities);
        return entryVos;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createEntry(EntryNetworkBean entryBean) {
        assertEntryValid(entryBean, null);

        EntryNetworkEntity entryEntity = entryNetworkAssembler.fromBean(entryBean);
        entryEntity.setNeedSync(false);
        entryNetworkDao.saveOrUpdate(entryEntity);

        entryBean.setId(entryEntity.getId());
        updateEntryOfAxis(entryBean);
    }

    public EntryNetworkVo getById(long id) {
        EntryNetworkEntity entryEntity = entryNetworkDao.findOne(id);
        Validator.notNull(entryEntity, ResourceException.error(CustomerErrorConstants.ENTRY_NETWORK_NOT_EXIST));

        return entryNetworkAssembler.toVo(entryEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateEntry(EntryNetworkBean entryBean) {
        EntryNetworkEntity entryEntity = entryNetworkDao.findOne(entryBean.getId());
        Validator.notNull(entryEntity, BusinessException.error(CustomerErrorConstants.ENTRY_NETWORK_NOT_EXIST));
        assertEntryValid(entryBean, entryEntity);

        if (!(entryEntity.getMasterIP().equals(entryBean.getMasterIP())) || !(entryEntity.getSlaveIP().equals(entryBean.getSlaveIP()))) {
            entryEntity.setNeedSync(Boolean.TRUE);
        }

        entryEntity.setName(entryBean.getName());
        entryEntity.setMasterIP(entryBean.getMasterIP());
        entryEntity.setSlaveIP(entryBean.getSlaveIP());
        entryEntity.setOperators1(entryBean.getOperators1());
        entryEntity.setOperators2(entryBean.getOperators2());

        entryNetworkDao.saveOrUpdate(entryEntity);

        updateEntryOfAxis(entryBean);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeEntry(long id) {
        EntryNetworkEntity entryEntity = entryNetworkDao.findOne(id);
        if (null != entryEntity) {
            resourceService.check(entryEntity);
            entryNetworkDao.delete(entryEntity);
            removeEntryOfAxis(id);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void syncEntry(EntryNetworkBean entryBean) {
        EntryNetworkEntity entryEntity = entryNetworkDao.findOne(entryBean.getId());
        Validator.notNull(entryEntity, BusinessException.error(CustomerErrorConstants.ENTRY_NETWORK_NOT_EXIST));

        Validator.isTrue(entryEntity.getNeedSync(), BusinessException.error(CustomerErrorConstants.ENTRY_NETWORK_NOT_SYNC));

        entryBean.setName(entryEntity.getName());
        entryBean.setMasterIP(entryEntity.getMasterIP());
        entryBean.setSlaveIP(entryEntity.getSlaveIP());
        entryBean.setOperators1(entryEntity.getOperators1());
        entryBean.setOperators2(entryEntity.getOperators2());

        entryEntity.setNeedSync(false);
        entryNetworkDao.saveOrUpdate(entryEntity);

        updateEntryOfAxis(entryBean);
    }

    private void updateEntryOfAxis(EntryNetworkBean entryBean) {
        EntryReq entryReq = new EntryReq();

        entryReq.setEntryId(entryBean.getId());
        entryReq.setEntryName(entryBean.getName());
        entryReq.setMasterIP(entryBean.getMasterIP());
        entryReq.setSlaveIP(entryBean.getSlaveIP());
        entryReq.setOperators1(entryBean.getOperators1());
        entryReq.setOperators2(entryBean.getOperators2());

        if (entryBean.getNeedSync() != null) {
            entryReq.setNeedSync(entryBean.getNeedSync());
        }

        EntryRes entryRes = axisEntryIF.updateEntry(entryBean.getId(), entryReq);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Axis-更新加速入口-同步加速入口失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_NETWORK_SYNCHRONIZATION_FAIL);
        }
    }

    private void removeEntryOfAxis(long entryId) {
        EntryRes entryRes = axisEntryIF.removeEntry(entryId);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Axis-更新加速入口-同步加速入口失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_NETWORK_SYNCHRONIZATION_FAIL);
        }
    }

    private void assertEntryValid(EntryNetworkBean bean, EntryNetworkEntity entity) {
        Validator.isTrue(!StringUtils.equals(bean.getMasterIP(), bean.getSlaveIP()), BusinessException.error(CustomerErrorConstants.ENTRY_NETWORK_IP_REPEAT));
        if (entity != null) {
            if (!StringUtils.equals(bean.getName(), entity.getName())) {
                Validator.isZero(entryNetworkDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_NETWORK_NAME_REPEAT));
            }
            if (!StringUtils.equals(bean.getMasterIP(), entity.getMasterIP()) ||
                    !StringUtils.equals(bean.getSlaveIP(), entity.getSlaveIP())) {
                Validator.isZero(entryNetworkDao.countByMasterIPSlaveIP(bean.getMasterIP(), bean.getSlaveIP()), BusinessException.error(CustomerErrorConstants.ENTRY_NETWORK_REPEAT));
            }
        } else {
            Validator.isZero(entryNetworkDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_NETWORK_NAME_REPEAT));
            Validator.isZero(entryNetworkDao.countByMasterIPSlaveIP(bean.getMasterIP(), bean.getSlaveIP()), BusinessException.error(CustomerErrorConstants.ENTRY_NETWORK_REPEAT));
        }

    }
}