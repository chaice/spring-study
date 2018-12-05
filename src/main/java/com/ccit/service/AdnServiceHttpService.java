package com.ccit.service;

import com.ccit.bean.AdnHttpServiceBean;
import com.ccit.bean.AdnHttpDomainBean;
import com.ccit.vo.AdnHttpDomainVo;
import com.ccit.vo.AdnHttpServiceVo;

import java.util.List;

public interface AdnServiceHttpService {

    List<AdnHttpServiceVo> listHttpServices();

    AdnHttpServiceVo getHttpService(long serviceId);

    void modifyHttpService(AdnHttpServiceBean adnHttpServiceBean);

    void removeHttpService(long serviceId);

    List<AdnHttpDomainVo> listHttpServiceDomain(long serviceId);

    void modifyHttpServiceDomain(AdnHttpDomainBean adnHttpDomainBean);

    void modifyAdnServiceEntry(AdnHttpServiceBean adnHttpServiceBean);

    void removeHttpServiceDomain(long serviceId, long domainId);
}