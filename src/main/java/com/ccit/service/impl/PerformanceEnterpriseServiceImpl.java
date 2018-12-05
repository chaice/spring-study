package com.ccit.service.impl;

import com.ccit.bean.PerformanceEnterpriseComputeBean;
import com.ccit.bean.PerformanceEnterpriseTrafficBean;
import com.ccit.dao.PerformanceEnterpriseComputeDao;
import com.ccit.dao.PerformanceEnterpriseTrafficDao;
import com.ccit.entity.PerformanceEnterpriseComputeEntity;
import com.ccit.entity.PerformanceEnterpriseTrafficEntity;
import com.ccit.service.PerformanceEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class PerformanceEnterpriseServiceImpl implements PerformanceEnterpriseService {

    @Autowired
    private PerformanceEnterpriseComputeDao performanceEnterpriseComputeDao;

    @Autowired
    private PerformanceEnterpriseTrafficDao performanceEnterpriseTrafficDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void syncCompute(List<PerformanceEnterpriseComputeBean> computeBeanList) {
        computeBeanList.forEach( computeBean -> {
            performanceEnterpriseComputeDao.saveOrUpdate(new PerformanceEnterpriseComputeEntity(computeBean));
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void syncTraffic(List<PerformanceEnterpriseTrafficBean> trafficBeanList) {
        trafficBeanList.forEach( trafficBean -> {
            performanceEnterpriseTrafficDao.saveOrUpdate(new PerformanceEnterpriseTrafficEntity(trafficBean));
        });
    }
}
