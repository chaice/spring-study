package com.ccit.service;

import com.ccit.bean.AdnCustomerDomainBean;
import com.ccit.vo.AdnCustomerDomainVo;

import java.util.List;

public interface AdnCustomerDomainService {

    List<AdnCustomerDomainVo> listCustomerDomain(long customerId);

    void removeCustomerDomain(long domainId);

    void addCustomerDomain(AdnCustomerDomainBean adnCustomerDomainBean);

    void modifyCustomerDomain(AdnCustomerDomainBean adnCustomerDomainBean);
}