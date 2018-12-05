package com.ccit.service.impl;

import com.ccit.dao.BoxEnterpriseDao;
import com.ccit.dao.BoxInternetCafeDao;
import com.ccit.dao.BoxNetworkDao;
import com.ccit.entity.*;
import com.ccit.exception.BusinessException;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.enums.AccelerateMode;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

//    @Autowired
//    private EntryIPIPDao businessEntryDao;
//
//    @Autowired
//    private ServerDao serverDao;

    @Autowired
    private BoxInternetCafeDao boxInternetCafeDao;

    @Autowired
    private BoxEnterpriseDao boxEnterpriseDao;

    @Autowired
    private BoxNetworkDao boxNetworkDao;

    public void check(Object instance) {

        // 系统用户
        if (instance instanceof SecurityUserEntity) {
        }
        // 服务器
//        else if (instance instanceof ServerEntity) {
//            checkServer((ServerEntity) instance);
//        }
//        // 加速入口
//        else if (instance instanceof EntryIPIPEntity) {
//        }
        else if (instance instanceof EntryIPIPEntity) {
            checkEntryIPIP((EntryIPIPEntity) instance);
        }
        else if(instance instanceof EntryNetworkEntity){
            checkEntryNetwork((EntryNetworkEntity) instance);
        }
        else if (instance instanceof EntryEnterpriseSSGroupEntity) {
            checkEntrySSGroup((EntryEnterpriseSSGroupEntity) instance);
        }
        else if (instance instanceof EntryEnterpriseL2tpEntity) {
            checkEntryL2TP((EntryEnterpriseL2tpEntity) instance);
        }

    }
//    private void checkServer(ServerEntity entity) throws BusinessException {
//        if (businessEntryDao.countByServerId(entity.getId()) > 0) {
//            throw new BusinessException(CustomerErrorConstants.SERVER_NOT_FREED);
//        }
//    }

    private void checkEntryIPIP(EntryIPIPEntity entity) throws BusinessException {
        if (boxInternetCafeDao.countByEntryId(entity.getId()) > 0) {
            throw new BusinessException(CustomerErrorConstants.ENTRY_IPIP_NOT_FREED);
        }
    }

    private void checkEntrySSGroup(EntryEnterpriseSSGroupEntity entity) {
        if (boxEnterpriseDao.countByEntryIdMode(entity.getId(), AccelerateMode.ADVANCED_MODE.getMode()) > 0) {
            throw new BusinessException(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SS_NOT_FREED);
        }
    }

    private void checkEntryNetwork(EntryNetworkEntity entity) {
        if (boxNetworkDao.countByEntryId(entity.getId()) > 0) {
            throw new BusinessException(CustomerErrorConstants.ENTRY_NETWORK_NOT_FREED);
        }
    }

    private void checkEntryL2TP(EntryEnterpriseL2tpEntity entity) {
        if (boxEnterpriseDao.countByEntryIdMode(entity.getId(),AccelerateMode.SIMPLE_MODE.getMode()) > 0) {
            throw new BusinessException(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_NOT_FREED);
        }
    }
}
