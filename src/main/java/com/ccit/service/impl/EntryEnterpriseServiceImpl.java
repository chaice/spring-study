package com.ccit.service.impl;

import com.ccit.assembler.EntryEnterpriseL2tpAssembler;
import com.ccit.assembler.EntryEnterpriseSSAssembler;
import com.ccit.dao.*;
import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.EntryEnterpriseSSGroupBean;
import com.ccit.bean.EntryEnterpriseL2TPBean;
import com.ccit.bean.EntryEnterpriseSSBean;
import com.ccit.dao.*;
import com.ccit.entity.EntryEnterpriseSSGroupEntity;
import com.ccit.entity.EntryEnterpriseL2tpEntity;
import com.ccit.entity.EntryEnterpriseSSEntity;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.sextant.interfaces.EntryIF;
import com.ccit.rest.sextant.request.EntryGroupReq;
import com.ccit.rest.sextant.request.EntryReq;
import com.ccit.rest.sextant.request.L2tpEntryReq;
import com.ccit.rest.sextant.response.EntryRes;
import com.ccit.service.EntryEnterpriseService;
import com.ccit.service.ResourceService;
import com.ccit.util.StringUtils;
import com.ccit.vo.EntryEnterpriseSSGroupVo;
import com.ccit.vo.EntryEnterpriseSSVo;
import com.ccit.vo.EntryEnterpriseL2TPVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class EntryEnterpriseServiceImpl implements EntryEnterpriseService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private EntryEnterpriseSSDao entryEnterpriseSSDao;

    @Autowired
    private BoxEntryEnterpriseSSDao boxEntryEnterpriseSSDao;

    @Autowired
    private EntryEnterpriseL2tpDao entryEnterpriseL2tpDao;

    @Autowired
    private BoxEntryEnterpriseL2tpDao boxEntryEnterpriseL2tpDao;

    @Autowired
    private EntryEnterpriseSSGroupDao entryEnterpriseGroupSSDao;

    @Autowired
    private EntryEnterpriseSSAssembler entryEnterpriseSSAssembler;

    @Autowired
    private EntryEnterpriseL2tpAssembler entryEnterpriseL2tpAssembler;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    @Qualifier("sextantEntryIF")
    private EntryIF sextantEntryIF;

    @Override
    public List<EntryEnterpriseSSGroupVo> listSSEntryGroup() {
        List<EntryEnterpriseSSGroupVo> entryEnterpriseSSGroupVoList = new LinkedList<>();
        List<EntryEnterpriseSSGroupEntity> entryEnterpriseSSGroupEntityList = entryEnterpriseGroupSSDao.findAll();
        entryEnterpriseSSGroupEntityList.forEach(entryEnterpriseGroupSSEntity -> {
            entryEnterpriseSSGroupVoList.add(
                    new EntryEnterpriseSSGroupVo(
                            entryEnterpriseGroupSSEntity.getId(),
                            entryEnterpriseGroupSSEntity.getName(),
                            entryEnterpriseGroupSSEntity.getDescription(),
                            toVo(entryEnterpriseGroupSSEntity.getCommonMasterId()),
                            toVo(entryEnterpriseGroupSSEntity.getCommonSlaveId()),
                            toVo(entryEnterpriseGroupSSEntity.getSpecialMasterId()),
                            toVo(entryEnterpriseGroupSSEntity.getSpecialSlaveId()),
                            entryEnterpriseGroupSSEntity.getNeedSync()
                    )
            );
        });
        return entryEnterpriseSSGroupVoList;
    }

    @Override
    public EntryEnterpriseSSGroupVo getSSEntryGroup(long groupId) {
        EntryEnterpriseSSGroupEntity entryEnterpriseSSGroupEntity = entryEnterpriseGroupSSDao.findOne(groupId);
        Validator.notNull(entryEnterpriseSSGroupEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_NOT_EXIST));

        EntryEnterpriseSSGroupVo entryEnterpriseSSGroupVo = new EntryEnterpriseSSGroupVo(
                entryEnterpriseSSGroupEntity.getId(),
                entryEnterpriseSSGroupEntity.getName(),
                entryEnterpriseSSGroupEntity.getDescription(),
                toVo(entryEnterpriseSSGroupEntity.getCommonMasterId()),
                toVo(entryEnterpriseSSGroupEntity.getCommonSlaveId()),
                toVo(entryEnterpriseSSGroupEntity.getSpecialMasterId()),
                toVo(entryEnterpriseSSGroupEntity.getSpecialSlaveId()),
                entryEnterpriseSSGroupEntity.getNeedSync()
        );

        return entryEnterpriseSSGroupVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createSSEntryGroup(EntryEnterpriseSSGroupBean groupSSBean) {

        EntryEnterpriseSSEntity commonMasterSSEntity = entryEnterpriseSSDao.findOne(groupSSBean.getCommonMasterId());
        Validator.notNull(commonMasterSSEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_COMMON_MASTER_ENTRY_NOT_EXIST));

        EntryEnterpriseSSEntity commonSlaveSSEntity = entryEnterpriseSSDao.findOne(groupSSBean.getCommonSlaveId());
        Validator.notNull(commonSlaveSSEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_COMMON_SLAVE_ENTRY_NOT_EXIST));

        EntryEnterpriseSSEntity specialMasterSSEntity = entryEnterpriseSSDao.findOne(groupSSBean.getSpecialMasterId());
        Validator.notNull(specialMasterSSEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SPECIAL_MASTER_ENTRY_NOT_EXIST));

        EntryEnterpriseSSEntity specialSlaveSSEntity = entryEnterpriseSSDao.findOne(groupSSBean.getSpecialSlaveId());
        Validator.notNull(specialSlaveSSEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SPECIAL_SLAVE_ENTRY_NOT_EXIST));

        assertSSEntryGroupValid(groupSSBean, null);

        EntryEnterpriseSSGroupEntity entryEnterpriseSSGroupEntity = new EntryEnterpriseSSGroupEntity();
        entryEnterpriseSSGroupEntity.setName(groupSSBean.getName());
        entryEnterpriseSSGroupEntity.setDescription(groupSSBean.getDescription());
        entryEnterpriseSSGroupEntity.setCommonMasterId(groupSSBean.getCommonMasterId());
        entryEnterpriseSSGroupEntity.setCommonSlaveId(groupSSBean.getCommonSlaveId());
        entryEnterpriseSSGroupEntity.setSpecialMasterId(groupSSBean.getSpecialMasterId());
        entryEnterpriseSSGroupEntity.setSpecialSlaveId(groupSSBean.getSpecialSlaveId());
        entryEnterpriseSSGroupEntity.setNeedSync(false);

        entryEnterpriseGroupSSDao.saveOrUpdate(entryEnterpriseSSGroupEntity);

        groupSSBean.setId(entryEnterpriseSSGroupEntity.getId());

        updateSSGroupEntryOfSextant(groupSSBean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void syncSSEntryGroup(EntryEnterpriseSSGroupBean entryEnterpriseSSGroupBean) {
        EntryEnterpriseSSGroupEntity entryEnterpriseSSGroupEntity = entryEnterpriseGroupSSDao.findOne(entryEnterpriseSSGroupBean.getId());
        Validator.notNull(entryEnterpriseSSGroupEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_NOT_EXIST));

        Validator.isTrue(entryEnterpriseSSGroupEntity.getNeedSync(), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_NOT_SYNC));

        entryEnterpriseSSGroupBean.setCommonMasterId(entryEnterpriseSSGroupEntity.getCommonMasterId());
        entryEnterpriseSSGroupBean.setCommonSlaveId(entryEnterpriseSSGroupEntity.getCommonSlaveId());
        entryEnterpriseSSGroupBean.setName(entryEnterpriseSSGroupEntity.getName());
        entryEnterpriseSSGroupBean.setDescription(entryEnterpriseSSGroupEntity.getDescription());
        entryEnterpriseSSGroupBean.setSpecialMasterId(entryEnterpriseSSGroupEntity.getSpecialMasterId());
        entryEnterpriseSSGroupBean.setSpecialSlaveId(entryEnterpriseSSGroupEntity.getSpecialSlaveId());

        entryEnterpriseSSGroupEntity.setNeedSync(false);
        entryEnterpriseGroupSSDao.saveOrUpdate(entryEnterpriseSSGroupEntity);

        updateSSGroupEntryOfSextant(entryEnterpriseSSGroupBean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSSEntryGroup(EntryEnterpriseSSGroupBean groupSSBean) {
        EntryEnterpriseSSGroupEntity entryEnterpriseSSGroupEntity = entryEnterpriseGroupSSDao.findOne(groupSSBean.getId());
        Validator.notNull(entryEnterpriseSSGroupEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_NOT_EXIST));

        //验证入口组合法性
        assertSSEntryGroupValid(groupSSBean, entryEnterpriseSSGroupEntity);

        EntryEnterpriseSSEntity commonMasterSSEntity = entryEnterpriseSSDao.findOne(groupSSBean.getCommonMasterId());
        Validator.notNull(commonMasterSSEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_COMMON_MASTER_ENTRY_NOT_EXIST));

        EntryEnterpriseSSEntity commonSlaveSSEntity = entryEnterpriseSSDao.findOne(groupSSBean.getCommonSlaveId());
        Validator.notNull(commonSlaveSSEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_COMMON_SLAVE_ENTRY_NOT_EXIST));

        EntryEnterpriseSSEntity specialMasterSSEntity = entryEnterpriseSSDao.findOne(groupSSBean.getSpecialMasterId());
        Validator.notNull(specialMasterSSEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SPECIAL_MASTER_ENTRY_NOT_EXIST));

        EntryEnterpriseSSEntity specialSlaveSSEntity = entryEnterpriseSSDao.findOne(groupSSBean.getSpecialSlaveId());
        Validator.notNull(specialSlaveSSEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SPECIAL_SLAVE_ENTRY_NOT_EXIST));

        if (!(entryEnterpriseSSGroupEntity.getCommonMasterId().equals(groupSSBean.getCommonMasterId()))
                || !(entryEnterpriseSSGroupEntity.getCommonSlaveId().equals(groupSSBean.getCommonSlaveId()))
                || !(entryEnterpriseSSGroupEntity.getSpecialMasterId().equals(groupSSBean.getSpecialMasterId()))
                || !(entryEnterpriseSSGroupEntity.getSpecialSlaveId().equals(groupSSBean.getSpecialSlaveId()))) {
            entryEnterpriseSSGroupEntity.setNeedSync(true);
        }

        entryEnterpriseSSGroupEntity.setName(groupSSBean.getName());
        entryEnterpriseSSGroupEntity.setDescription(groupSSBean.getDescription());
        entryEnterpriseSSGroupEntity.setCommonMasterId(groupSSBean.getCommonMasterId());
        entryEnterpriseSSGroupEntity.setCommonSlaveId(groupSSBean.getCommonSlaveId());
        entryEnterpriseSSGroupEntity.setSpecialMasterId(groupSSBean.getSpecialMasterId());
        entryEnterpriseSSGroupEntity.setSpecialSlaveId(groupSSBean.getSpecialSlaveId());

        entryEnterpriseGroupSSDao.saveOrUpdate(entryEnterpriseSSGroupEntity);

        updateSSGroupEntryOfSextant(groupSSBean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeSSEntryGroup(long groupId) {
        EntryEnterpriseSSGroupEntity entryEnterpriseSSGroupEntity = entryEnterpriseGroupSSDao.findOne(groupId);
        if (entryEnterpriseSSGroupEntity != null) {
            resourceService.check(entryEnterpriseSSGroupEntity);
            entryEnterpriseGroupSSDao.deleteById(groupId);

            //根据入口组ID删除与盒子关联的记录
            boxEntryEnterpriseSSDao.deleteByEntryId(groupId);
            //同步到Sextant
            removeSSEntryGroupOfSextant(groupId);
        }
    }

    private void assertSSEntryGroupValid(EntryEnterpriseSSGroupBean bean, EntryEnterpriseSSGroupEntity entity) {
        if (entity != null) {
            if (!StringUtils.equals(bean.getName(), entity.getName())) {
                Validator.isZero(entryEnterpriseGroupSSDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_NAME_REPEAT));
            }
            if ((bean.getCommonMasterId() != entity.getCommonMasterId()) || (bean.getCommonSlaveId() != entity.getCommonSlaveId())
                    || (bean.getSpecialMasterId() != entity.getSpecialMasterId()) || (bean.getSpecialSlaveId() != entity.getSpecialSlaveId())) {
                Validator.isZero(entryEnterpriseGroupSSDao.countByEntryId(bean.getCommonMasterId(), bean.getCommonSlaveId(), bean.getSpecialMasterId(), bean.getSpecialSlaveId()), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_REPEAT));
            }
        } else {
            Validator.isZero(entryEnterpriseGroupSSDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_NAME_REPEAT));
            Validator.isZero(entryEnterpriseGroupSSDao.countByEntryId(bean.getCommonMasterId(), bean.getCommonSlaveId(), bean.getSpecialMasterId(), bean.getSpecialSlaveId()), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_REPEAT));
        }
    }

    private EntryEnterpriseSSVo toVo(Long entryId) {
        EntryEnterpriseSSEntity entryEnterpriseSSEntity = entryEnterpriseSSDao.findOne(entryId);
        if (entryEnterpriseSSEntity != null) {
            EntryEnterpriseSSVo enterpriseSSVo = new EntryEnterpriseSSVo();
            enterpriseSSVo.setId(entryEnterpriseSSEntity.getId());
            enterpriseSSVo.setName(entryEnterpriseSSEntity.getName());
            enterpriseSSVo.setIp(entryEnterpriseSSEntity.getIp());
            enterpriseSSVo.setPort(entryEnterpriseSSEntity.getPort());
            enterpriseSSVo.setDns(entryEnterpriseSSEntity.getDns());
            enterpriseSSVo.setPassword(entryEnterpriseSSEntity.getPassword());
            return enterpriseSSVo;
        }
        return null;
    }

    public List<EntryEnterpriseSSVo> searchSSEntry() {
        List<EntryEnterpriseSSEntity> entryEntities = entryEnterpriseSSDao.searchEntry();
        List<EntryEnterpriseSSVo> entryVos = entryEnterpriseSSAssembler.toVos(entryEntities);
        return entryVos;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createSSEntry(EntryEnterpriseSSBean entryBean) {
        assertSSEntryValid(entryBean, null);

        EntryEnterpriseSSEntity entryEntity = entryEnterpriseSSAssembler.fromBean(entryBean);
        entryEnterpriseSSDao.saveOrUpdate(entryEntity);

        entryBean.setId(entryEntity.getId());
        updateSSEntryOfSextant(entryBean);
    }

    public EntryEnterpriseSSVo getSSEntryById(long id) {
        EntryEnterpriseSSEntity entryEntity = entryEnterpriseSSDao.findOne(id);
        Validator.notNull(entryEntity, ResourceException.error(CustomerErrorConstants.ENTRY_SHADOWSOCKS_NOT_EXIST));

        return entryEnterpriseSSAssembler.toVo(entryEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSSEntry(EntryEnterpriseSSBean entryBean) {
        EntryEnterpriseSSEntity entryEntity = entryEnterpriseSSDao.findOne(entryBean.getId());
        Validator.notNull(entryEntity, BusinessException.error(CustomerErrorConstants.ENTRY_SHADOWSOCKS_NOT_EXIST));
        assertSSEntryValid(entryBean, entryEntity);

        if (!(entryBean.getIp().equals(entryEntity.getIp()))
                || !(entryBean.getPort().equals(entryEntity.getPort()))
                || !(entryBean.getPassword().equals(entryEntity.getPassword()))
                || !(entryBean.getDns().equals(entryEntity.getDns()))) {
            List<EntryEnterpriseSSGroupEntity> enterpriseGroupSSEntityList = entryEnterpriseGroupSSDao.findByEntryId(entryEntity.getId());
            enterpriseGroupSSEntityList.forEach(entryEnterpriseSSGroupEntity -> {
                entryEnterpriseSSGroupEntity.setNeedSync(true);
                entryEnterpriseGroupSSDao.saveOrUpdate(entryEnterpriseSSGroupEntity);
            });
        }

        entryEntity.setName(entryBean.getName());
        entryEntity.setIp(entryBean.getIp());
        entryEntity.setPort(entryBean.getPort());
        entryEntity.setDns(entryBean.getDns());
        entryEntity.setPassword(entryBean.getPassword());

        entryEnterpriseSSDao.saveOrUpdate(entryEntity);

        updateSSEntryOfSextant(entryBean);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeSSEntry(long id) {
        EntryEnterpriseSSEntity entryEntity = entryEnterpriseSSDao.findOne(id);
        if (null != entryEntity) {
            entryEnterpriseSSDao.delete(entryEntity);

            List<EntryEnterpriseSSGroupEntity> enterpriseGroupSSEntityList = entryEnterpriseGroupSSDao.findByEntryId(id);
            Validator.isZero(enterpriseGroupSSEntityList.size(), BusinessException.error(CustomerErrorConstants.ENTRY_SHADOWSOCKS_NOT_FREED));

            entryEnterpriseGroupSSDao.deleteByEntryId(id);
            removeSSEntryOfSextant(id);
        }
    }

    public List<EntryEnterpriseL2TPVo> searchL2tpEntry() {
        List<EntryEnterpriseL2tpEntity> entryEntities = entryEnterpriseL2tpDao.searchEntry();
        List<EntryEnterpriseL2TPVo> entryVos = entryEnterpriseL2tpAssembler.toVos(entryEntities);
        return entryVos;
    }

    public EntryEnterpriseL2TPVo getL2tpById(long id) {
        EntryEnterpriseL2tpEntity entryEntity = entryEnterpriseL2tpDao.findOne(id);
        Validator.notNull(entryEntity, ResourceException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_NOT_EXIST));
        return entryEnterpriseL2tpAssembler.toVo(entryEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeL2tpEntry(long id) {
        EntryEnterpriseL2tpEntity entryEntity = entryEnterpriseL2tpDao.findOne(id);
        if (null != entryEntity) {
            resourceService.check(entryEntity);
            entryEnterpriseL2tpDao.delete(entryEntity);
            boxEntryEnterpriseL2tpDao.deleteByEntryId(id);
            removeL2tpEntryOfSextant(id);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateL2tpEntry(EntryEnterpriseL2TPBean entryBean) {
        EntryEnterpriseL2tpEntity entryEntity = entryEnterpriseL2tpDao.findOne(entryBean.getId());
        Validator.notNull(entryEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_NOT_EXIST));
        assertL2tpEntryValid(entryBean, entryEntity);

        if (!(entryEntity.getMasterIP().equals(entryBean.getMasterIP())) || !(entryEntity.getSlaveIP().equals(entryBean.getSlaveIP()))) {
            entryEntity.setNeedSync(Boolean.TRUE);
        }

        entryEntity.setName(entryBean.getName());
        entryEntity.setMasterIP(entryBean.getMasterIP());
        entryEntity.setSlaveIP(entryBean.getSlaveIP());
        entryEnterpriseL2tpDao.saveOrUpdate(entryEntity);

        updateL2tpEntryOfSextant(entryBean);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void syncL2tpEntry(EntryEnterpriseL2TPBean entryEnterpriseL2TPBean) {
        EntryEnterpriseL2tpEntity entryEntity = entryEnterpriseL2tpDao.findOne(entryEnterpriseL2TPBean.getId());
        Validator.notNull(entryEntity, BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_NOT_EXIST));

        Validator.isTrue(entryEntity.getNeedSync(), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_NOT_SYNC));

        entryEnterpriseL2TPBean.setId(entryEntity.getId());
        entryEnterpriseL2TPBean.setName(entryEntity.getName());
        entryEnterpriseL2TPBean.setMasterIP(entryEntity.getMasterIP());
        entryEnterpriseL2TPBean.setSlaveIP(entryEntity.getSlaveIP());
        entryEnterpriseL2TPBean.setNeedSync(entryEntity.getNeedSync());

        entryEntity.setNeedSync(Boolean.FALSE);
        entryEnterpriseL2tpDao.saveOrUpdate(entryEntity);

        updateL2tpEntryOfSextant(entryEnterpriseL2TPBean);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createL2tpEntry(EntryEnterpriseL2TPBean entryBean) {
        assertL2tpEntryValid(entryBean, null);

        EntryEnterpriseL2tpEntity entryEntity = entryEnterpriseL2tpAssembler.fromBean(entryBean);
        entryEntity.setNeedSync(Boolean.FALSE);
        entryEnterpriseL2tpDao.saveOrUpdate(entryEntity);

        entryBean.setId(entryEntity.getId());
        updateL2tpEntryOfSextant(entryBean);
    }

    private void updateSSGroupEntryOfSextant(EntryEnterpriseSSGroupBean enterpriseGroupSSBean) {
        EntryGroupReq entryGroupReq = new EntryGroupReq(enterpriseGroupSSBean);

        if (enterpriseGroupSSBean.getNeedSync() != null) {
            entryGroupReq.setNeedSync(enterpriseGroupSSBean.getNeedSync());
        }

        EntryRes entryRes = sextantEntryIF.updateSSGroupEntry(enterpriseGroupSSBean.getId(), entryGroupReq);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Sextant-更新SS加速入口组-同步加速入口组失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SS_SYNCHRONIZATION_FAIL);
        }
    }

    private void removeSSEntryGroupOfSextant(long entryId) {
        EntryRes entryRes = sextantEntryIF.removeSSGroupEntry(entryId);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Sextant-删除SS加速入口组-同步加速入口组失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SS_SYNCHRONIZATION_FAIL);
        }
    }

    private void updateSSEntryOfSextant(EntryEnterpriseSSBean entryBean) {
        EntryReq entryReq = new EntryReq();

        entryReq.setEntryId(entryBean.getId());
        entryReq.setEntryName(entryBean.getName());

        entryReq.setIp(entryBean.getIp());
        entryReq.setPort(entryBean.getPort());
        entryReq.setDns(entryBean.getDns());
        entryReq.setPassword(entryBean.getPassword());

        EntryRes entryRes = sextantEntryIF.updateSSEntry(entryBean.getId(), entryReq);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Sextant-更新SS加速入口-同步加速入口失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_SHADOWSOCKS_SYNCHRONIZATION_FAIL);
        }
    }

    private void removeSSEntryOfSextant(long entryId) {
        EntryRes entryRes = sextantEntryIF.removeSSEntry(entryId);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Sextant-删除SS加速入口-同步加速入口失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_SHADOWSOCKS_SYNCHRONIZATION_FAIL);
        }
    }

    private void assertSSEntryValid(EntryEnterpriseSSBean bean, EntryEnterpriseSSEntity entity) {
        if (entity != null) {
            if (!(StringUtils.equals(bean.getIp(), entity.getIp()) && bean.getPort().equals(entity.getPort()))) {
                Validator.isZero(entryEnterpriseSSDao.countByIpPort(bean.getIp(), bean.getPort()), BusinessException.error(CustomerErrorConstants.ENTRY_SHADOWSOCKS_REPEAT));
            }
            if (!StringUtils.equals(bean.getName(), entity.getName())) {
                Validator.isZero(entryEnterpriseSSDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_SHADOWSOCKS_NAME_REPEAT));
            }
        } else {
            Validator.isZero(entryEnterpriseSSDao.countByIpPort(bean.getIp(), bean.getPort()), BusinessException.error(CustomerErrorConstants.ENTRY_SHADOWSOCKS_REPEAT));
            Validator.isZero(entryEnterpriseSSDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_SHADOWSOCKS_NAME_REPEAT));
        }
    }

    private void removeL2tpEntryOfSextant(long entryId) {
        EntryRes entryRes = sextantEntryIF.removeL2tpEntry(entryId);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Sextant-删除L2TP加速入口-同步加速入口失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_SYNCHRONIZATION_FAIL);
        }
    }

    private void assertL2tpEntryValid(EntryEnterpriseL2TPBean bean, EntryEnterpriseL2tpEntity entity) {
        if (entity != null) {
            if (!(StringUtils.equals(bean.getMasterIP(), entity.getMasterIP()))) {
                Validator.isZero(entryEnterpriseL2tpDao.countByMasterIP(bean.getMasterIP()), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_REPEAT));
            }
            if (!StringUtils.equals(bean.getName(), entity.getName())) {
                Validator.isZero(entryEnterpriseL2tpDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_NAME_REPEAT));
            }
        } else {
            Validator.isZero(entryEnterpriseL2tpDao.countByMasterIP(bean.getMasterIP()), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_REPEAT));
            Validator.isZero(entryEnterpriseL2tpDao.countByName(bean.getName()), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_NAME_REPEAT));
        }
        Validator.isTrue(!StringUtils.equals(bean.getMasterIP(), bean.getSlaveIP()), BusinessException.error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_IP_REPEAT));
    }

    private void updateL2tpEntryOfSextant(EntryEnterpriseL2TPBean entryBean) {
        L2tpEntryReq entryReq = new L2tpEntryReq();
        entryReq.setEntryId(entryBean.getId());
        entryReq.setEntryName(entryBean.getName());
        entryReq.setMasterIP(entryBean.getMasterIP());
        entryReq.setSlaveIP(entryBean.getSlaveIP());

        if (entryBean.getNeedSync() != null) {
            entryReq.setNeedSync(entryBean.getNeedSync());
        }

        EntryRes entryRes = sextantEntryIF.updateL2tpEntry(entryBean.getId(), entryReq);
        if (!entryRes.isSuccess()) {
            logger.error(String.format("Sextant-更新L2TP加速入口-同步加速入口失败，错误码：%s，错误信息：%s",
                    entryRes.getErrorBody().getErrorCode(),
                    entryRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_SYNCHRONIZATION_FAIL);
        }
    }

}