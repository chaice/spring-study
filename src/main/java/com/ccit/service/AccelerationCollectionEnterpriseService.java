package com.ccit.service;


import com.ccit.bean.AccelerationCidrEnterpriseBean;
import com.ccit.bean.AccelerationCollectionEnterpriseBean;
import com.ccit.bean.AccelerationDomainEnterpriseBean;
import com.ccit.bean.AccelerationIpsetEnterpriseBean;
import com.ccit.vo.AccelerationCidrEnterpriseVo;
import com.ccit.vo.AccelerationCollectionEnterpriseVo;
import com.ccit.vo.AccelerationDomainEnterpriseVo;
import com.ccit.vo.AccelerationIpsetEnterpriseVo;

import java.util.List;

public interface AccelerationCollectionEnterpriseService {

    List<AccelerationCollectionEnterpriseVo> listAllCollection();

    List<AccelerationCollectionEnterpriseVo> listCollectionByAccelerateMode(String accelerateMode);

    AccelerationCollectionEnterpriseVo getCollectionById(long id);

    void createCollection(AccelerationCollectionEnterpriseBean collectionBean);

    void modifyCollection(AccelerationCollectionEnterpriseBean collectionBean);

    void removeCollection(long id);

    void createCidr(AccelerationCidrEnterpriseBean cidrBean);

    void removeCidr(long collectionId, long ipsetId, long cidrId);

    void createDomain(AccelerationDomainEnterpriseBean domainBean);

    void removeDomain(long collectionId, long ipsetId, long domainId);

    void syncCidr(long collectionId, long ipsetId, List<AccelerationCidrEnterpriseBean> cidrBeanList);

    void syncDomain(long collectionId, long ipsetId, List<AccelerationDomainEnterpriseBean> domainBeanList);

    List<AccelerationCidrEnterpriseVo> listCidrByIpsetId(long ipsetId);

    List<AccelerationDomainEnterpriseVo> listDomainByIpsetId(long ipsetId);

    void modifyCidrByCountry(long cidrId);

    List<AccelerationCidrEnterpriseVo> listCidrByNullCountry(int limit);

    AccelerationIpsetEnterpriseVo getIpsetById(long ipsetId);

    List<AccelerationIpsetEnterpriseVo> listIpsetByCollectionId(long collectionId);

    long createIpset(AccelerationIpsetEnterpriseBean ipsetBean);

    void modifyIpset(AccelerationIpsetEnterpriseBean ipsetBean);

    void removeIpset(long collectionId, long ipsetId);

    void availableIpset(AccelerationIpsetEnterpriseBean ipsetBean);

    List<AccelerationCollectionEnterpriseVo> listCommonCollectionByAccelerateMode(String accelerateMode);
}