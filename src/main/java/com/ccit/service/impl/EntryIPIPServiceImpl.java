package com.ccit.service.impl;

import com.ccit.assembler.EntryIPIPAssembler;
import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.EntryIPIPBean;
import com.ccit.dao.BoxEntryIPIPDao;
import com.ccit.dao.EntryIPIPDao;
import com.ccit.entity.EntryIPIPEntity;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.zion.interfaces.EntryIF;
import com.ccit.rest.zion.request.EntryReq;
import com.ccit.rest.zion.response.EntryRes;
import com.ccit.service.EntryIPIPService;
import com.ccit.service.ResourceService;
import com.ccit.util.StringUtils;
import com.ccit.vo.EntryIPIPVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class EntryIPIPServiceImpl implements EntryIPIPService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private EntryIPIPDao businessEntryDao;

    @Autowired
    private BoxEntryIPIPDao boxEntryIPIPDao;

    @Autowired
    private EntryIPIPAssembler businessEntryAssembler;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    @Qualifier("zionEntryIF")
    private EntryIF zionEntryIF;


    public List<EntryIPIPVo> searchEntry() {
        List<EntryIPIPEntity> entryEntities = businessEntryDao.searchEntry();
        List<EntryIPIPVo> entryVos = businessEntryAssembler.toVos(entryEntities);
        return entryVos;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createEntry(EntryIPIPBean entryBean) {
        assertEntryValid(entryBean, null);

        EntryIPIPEntity entryEntity = businessEntryAssembler.fromBean(entryBean);
        businessEntryDao.saveOrUpdate(entryEntity);

        entryBean.setId(entryEntity.getId());
        updateEntryOfZion(entryBean);
    }


    public EntryIPIPVo getById(long id) {
        EntryIPIPEntity entryEntity = businessEntryDao.findOne(id);
        Validator.notNull(entryEntity, ResourceException.error(CustomerErrorConstants.ENTRY_IPIP_NOT_EXIST));

        return businessEntryAssembler.toVo(entryEntity);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateEntry(EntryIPIPBean entryBean) {
        EntryIPIPEntity entryEntity = businessEntryDao.findOne(entryBean.getId());
        Validator.notNull(entryEntity, BusinessException.error(CustomerErrorConstants.ENTRY_IPIP_NOT_EXIST));
        assertEntryValid(entryBean, entryEntity);

        entryEntity.setName(entryBean.getName());
        entryEntity.setMasterIP(entryBean.getMasterIP());
        entryEntity.setSlaveIP(entryBean.getSlaveIP());
        businessEntryDao.saveOrUpdate(entryEntity);

        updateEntryOfZion(entryBean);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeEntry(long id) {
        EntryIPIPEntity entryEntity = businessEntryDao.findOne(id);
        if (null != entryEntity) {
            resourceService.check(entryEntity);
            businessEntryDao.delete(entryEntity);
            boxEntryIPIPDao.deleteByEntryId(id);
            removeEntryOfZion(id);
        }
    }


    private void updateEntryOfZion(EntryIPIPBean entryBean) {
        EntryReq entryReq = new EntryReq();

        entryReq.setEntryId(entryBean.getId());
        entryReq.setEntryName(entryBean.getName());
        entryReq.setMasterIP(entryBean.getMasterIP());
        entryReq.setSlaveIP(entryBean.getSlaveIP());

        EntryRes entryRes = zionEntryIF.updateEntry(entryBean.getId(), entryReq);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Zion-更新加速入口-同步加速入口失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_IPIP_SYNCHRONIZATION_FAIL);
        }
    }


    private void removeEntryOfZion(long entryId) {
        EntryRes entryRes = zionEntryIF.removeEntry(entryId);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Zion-更新加速入口-同步加速入口失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_IPIP_SYNCHRONIZATION_FAIL);
        }
    }


    private void assertEntryValid(EntryIPIPBean bean, EntryIPIPEntity entity) {
        if (entity != null) {
            if (!(StringUtils.equals(bean.getMasterIP(), entity.getMasterIP()))) {
                Validator.isZero(businessEntryDao.countByMasterIP(bean.getMasterIP()), BusinessException.error(CustomerErrorConstants.ENTRY_IPIP_REPEAT));
            }
            if (!StringUtils.equals(bean.getName(), entity.getName())) {
                Validator.isZero(businessEntryDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_IPIP_NAME_REPEAT));
            }
        } else {
            Validator.isZero(businessEntryDao.countByMasterIP(bean.getMasterIP()), BusinessException.error(CustomerErrorConstants.ENTRY_IPIP_REPEAT));
            Validator.isZero(businessEntryDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_IPIP_NAME_REPEAT));
        }
    }
}