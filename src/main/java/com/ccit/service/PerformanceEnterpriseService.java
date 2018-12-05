package com.ccit.service;

import com.ccit.bean.PerformanceEnterpriseComputeBean;
import com.ccit.bean.PerformanceEnterpriseTrafficBean;
import java.util.List;

public interface PerformanceEnterpriseService {

    void syncCompute(List<PerformanceEnterpriseComputeBean> computeBeanList);

    void syncTraffic(List<PerformanceEnterpriseTrafficBean> trafficBeanList);
}
