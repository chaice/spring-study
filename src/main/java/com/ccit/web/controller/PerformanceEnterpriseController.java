package com.ccit.web.controller;

import com.ccit.bean.PerformanceEnterpriseComputeBean;
import com.ccit.bean.PerformanceEnterpriseTrafficBean;
import com.ccit.service.PerformanceEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PerformanceEnterpriseController extends BaseController {

    @Autowired
    private PerformanceEnterpriseService performanceEnterpriseService;

    @RequestMapping(value = "/enterprise/performances/compute", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> syncCompute(@RequestBody List<PerformanceEnterpriseComputeBean> computeBeanList){
        performanceEnterpriseService.syncCompute(computeBeanList);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/enterprise/performances/traffics", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> syncTraffic(@RequestBody List<PerformanceEnterpriseTrafficBean> trafficBeanList){
        performanceEnterpriseService.syncTraffic(trafficBeanList);
        return ResponseEntity.ok(null);
    }
}
