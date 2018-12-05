package com.ccit.web.controller;

import com.ccit.bean.AdnL3ServiceBean;
import com.ccit.service.AdnL3ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@Controller
@RequestMapping(value = "/adn/L3/services")
public class AdnL3ServiceController extends BaseController {

    @Autowired
    private AdnL3ServiceService adnL3ServiceService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> list3LayerServices() {
        return ResponseEntity.ok(adnL3ServiceService.list3LayerServices());
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get3LayerService(@PathVariable long serviceId) {
        return ResponseEntity.ok(adnL3ServiceService.get3LayerService(serviceId));
    }

    @RequestMapping(value = "/{serviceId}/zones/{zoneId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createAdnServiceEntry(@PathVariable long serviceId, @PathVariable long zoneId, @RequestBody AdnL3ServiceBean adnL3ServiceBean) {
        adnL3ServiceBean.setId(serviceId);
        adnL3ServiceBean.setZoneList(new LinkedList<>());
        adnL3ServiceBean.getZoneList().add(zoneId);
        adnL3ServiceService.modifyAdnServiceEntry(adnL3ServiceBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> modify3LayerService(@PathVariable long serviceId, @RequestBody AdnL3ServiceBean adnL3ServiceBean) {
        adnL3ServiceBean.setId(serviceId);
        adnL3ServiceService.modify3LayerService(adnL3ServiceBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> remove3LayerService(@PathVariable long serviceId) {
        adnL3ServiceService.remove3LayerService(serviceId);
        return ResponseEntity.ok(null);
    }
}