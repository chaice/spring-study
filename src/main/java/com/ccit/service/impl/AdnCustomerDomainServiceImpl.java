package com.ccit.service.impl;


import com.ccit.exception.ResourceException;
import com.ccit.service.AdnCustomerDomainService;
import com.ccit.bean.AdnCustomerDomainBean;
import com.ccit.dao.AdnCustomerDomainDao;
import com.ccit.entity.AdnCustomerDomainEntity;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.vo.AdnCustomerDomainVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AdnCustomerDomainServiceImpl implements AdnCustomerDomainService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AdnCustomerDomainDao adnCustomerDomainDao;

    @Autowired
    Assembler<AdnCustomerDomainBean, AdnCustomerDomainEntity, AdnCustomerDomainVo> adnCustomerDomainAssembler;    

    @Override
    public List<AdnCustomerDomainVo> listCustomerDomain(long customerId) {
        List<AdnCustomerDomainVo> adnCustomerDomainVos = new ArrayList<>();

        List<AdnCustomerDomainEntity> adnCustomerDomainEntities = adnCustomerDomainDao.findByCustomerId(customerId);
        adnCustomerDomainEntities.forEach(adnCustomerDomainEntity -> {
            adnCustomerDomainVos.add(adnCustomerDomainAssembler.toVo(adnCustomerDomainEntity));
        });
        return adnCustomerDomainVos;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeCustomerDomain(long domainId) {
        adnCustomerDomainDao.deleteById(domainId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addCustomerDomain(AdnCustomerDomainBean adnCustomerDomainBean) {
        AdnCustomerDomainEntity adnCustomerDomainEntity = adnCustomerDomainAssembler.fromBean(adnCustomerDomainBean);
        adnCustomerDomainDao.saveOrUpdate(adnCustomerDomainEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyCustomerDomain(AdnCustomerDomainBean adnCustomerDomainBean) {
        AdnCustomerDomainEntity adnCustomerDomainEntity = adnCustomerDomainDao.findOne(adnCustomerDomainBean.getId());
        Validator.notNull(adnCustomerDomainEntity, ResourceException.error(CustomerErrorConstants.ADN_CUSTOMER_DOMAIN_NOT_EXIST));
        adnCustomerDomainEntity.setSecondLevelDomain(adnCustomerDomainBean.getSecondLevelDomain());
        adnCustomerDomainDao.saveOrUpdate(adnCustomerDomainEntity);
    }
}
