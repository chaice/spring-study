package com.ccit.web.controller;

import com.ccit.bean.PerformanceBean;
import com.ccit.bean.PerformanceQueryBean;
import com.ccit.service.PerformanceAdnTrafficService;
import com.ccit.vo.PerformanceTrafficVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/adn/performances")
public class PerformanceAdnTrafficController extends BaseController {

    @Autowired
    private PerformanceAdnTrafficService performanceAdnTrafficService;

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getCustomerBandwidth(@PathVariable long customerId,
                                                           PerformanceQueryBean performanceQueryBean) {
        performanceQueryBean = verifyPerformanceQueryBean(performanceQueryBean);
        List<PerformanceTrafficVo> performanceTrafficVos = performanceAdnTrafficService.getCustomerBandwidth(customerId, performanceQueryBean);
        return ResponseEntity.ok(performanceTrafficVos);
    }


    @RequestMapping(value = "/zones/{zoneId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getCurZoneBandwidth(@PathVariable long zoneId,
                                                      PerformanceQueryBean performanceQueryBean) {
        performanceQueryBean = verifyPerformanceQueryBean(performanceQueryBean);
        List<PerformanceTrafficVo> performanceTrafficVos = performanceAdnTrafficService.getCurZoneBandwidth(zoneId, performanceQueryBean);
        return ResponseEntity.ok(performanceTrafficVos);
    }

    @RequestMapping(value = "/entries/{entryId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getZoneEntryBandwidth(@PathVariable long entryId,
                                                        PerformanceQueryBean performanceQueryBean) {
        performanceQueryBean = verifyPerformanceQueryBean(performanceQueryBean);
        List<PerformanceTrafficVo> performanceTrafficVos = performanceAdnTrafficService.getZoneEntryBandwidth(entryId, performanceQueryBean);
        return ResponseEntity.ok(performanceTrafficVos);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createPerformanceAdnTraffic(@RequestBody PerformanceBean performanceBean) {
        performanceAdnTrafficService.createPerformanceAdnTraffic(performanceBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/services/{serviceType}/{serviceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getServiceTraffic(
            @PathVariable long serviceId,
            @PathVariable String serviceType,
            PerformanceQueryBean performanceQueryBean) throws InterruptedException {
        performanceQueryBean = verifyPerformanceQueryBean(performanceQueryBean);
        List<PerformanceTrafficVo> performanceTrafficVos = performanceAdnTrafficService.getServiceTraffic(serviceId, serviceType, performanceQueryBean);
        return ResponseEntity.ok(performanceTrafficVos);
    }

    @RequestMapping(value = "/services/{serviceType}/{serviceId}/zones/{zoneId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getZoneTraffic(
            @PathVariable long serviceId,
            @PathVariable long zoneId,
            @PathVariable String serviceType,
            PerformanceQueryBean performanceQueryBean) throws InterruptedException {
        performanceQueryBean = verifyPerformanceQueryBean(performanceQueryBean);
        List<PerformanceTrafficVo> performanceTrafficVos = performanceAdnTrafficService.getZoneTraffic(serviceId, zoneId, serviceType, performanceQueryBean);
        return ResponseEntity.ok(performanceTrafficVos);
    }

    @RequestMapping(value = "/services/{serviceType}/{serviceId}/zones/{zoneId}/entries/{entryId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getEntryTraffic(
            @PathVariable long serviceId,
            @PathVariable long zoneId,
            @PathVariable String serviceType,
            @PathVariable long entryId,
            PerformanceQueryBean performanceQueryBean) throws InterruptedException {
        performanceQueryBean = verifyPerformanceQueryBean(performanceQueryBean);
        List<PerformanceTrafficVo> performanceTrafficVos = performanceAdnTrafficService.getEntryTraffic(serviceId, zoneId,entryId, serviceType, performanceQueryBean);
        return ResponseEntity.ok(performanceTrafficVos);
    }



}
