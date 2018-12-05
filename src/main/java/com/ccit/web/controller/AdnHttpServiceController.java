package com.ccit.web.controller;

import com.ccit.bean.AdnHttpDomainBean;
import com.ccit.bean.AdnHttpServiceBean;
import com.ccit.service.AdnServiceHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@Controller
@RequestMapping(value = "/adn/http/services")
public class AdnHttpServiceController extends BaseController {

    @Autowired
    private AdnServiceHttpService adnServiceHttpService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listHttpServices() {
        return ResponseEntity.ok(adnServiceHttpService.listHttpServices());
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getHttpService(@PathVariable long serviceId) {
        return ResponseEntity.ok(adnServiceHttpService.getHttpService(serviceId));
    }

    @RequestMapping(value = "/{serviceId}/zones/{zoneId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createAdnServiceEntry(@PathVariable long serviceId, @PathVariable long zoneId, @RequestBody AdnHttpServiceBean adnHttpServiceBean) {
        adnHttpServiceBean.setId(serviceId);
        adnHttpServiceBean.setZoneList(new LinkedList<>());
        adnHttpServiceBean.getZoneList().add(zoneId);
        adnServiceHttpService.modifyAdnServiceEntry(adnHttpServiceBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> modifyHttpService(@PathVariable long serviceId, @RequestBody AdnHttpServiceBean adnHttpServiceBean) {
        adnHttpServiceBean.setId(serviceId);
        adnServiceHttpService.modifyHttpService(adnHttpServiceBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeHttpService(@PathVariable long serviceId) {
        adnServiceHttpService.removeHttpService(serviceId);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{serviceId}/domains", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listHttpServiceDomain(@PathVariable long serviceId) {
        return ResponseEntity.ok(adnServiceHttpService.listHttpServiceDomain(serviceId));
    }

    @RequestMapping(value = "/{serviceId}/domains/{domainId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> modifyHttpServiceDomain(@PathVariable long serviceId, @PathVariable long domainId, @RequestBody AdnHttpDomainBean adnHttpDomainBean) {
        adnHttpDomainBean.setId(domainId);
        adnHttpDomainBean.setServiceId(serviceId);
        adnServiceHttpService.modifyHttpServiceDomain(adnHttpDomainBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{serviceId}/domains/{domainId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeHttpServiceDomain(@PathVariable long serviceId, @PathVariable long domainId) {
        adnServiceHttpService.removeHttpServiceDomain(serviceId, domainId);
        return ResponseEntity.ok(null);
    }
}