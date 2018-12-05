package com.ccit.web.controller;

import com.ccit.bean.AdnL4ServiceBean;
import com.ccit.service.AdnL4ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@Controller
@RequestMapping(value = "/adn/L4/services")
public class AdnL4ServiceController extends BaseController {

    @Autowired
    private AdnL4ServiceService adnL4ServiceService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> list4LayerServices() {
        return ResponseEntity.ok(adnL4ServiceService.list4LayerServices());
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get4LayerService(@PathVariable long serviceId) {
        return ResponseEntity.ok(adnL4ServiceService.get4LayerService(serviceId));
    }

    @RequestMapping(value = "/{serviceId}/zones/{zoneId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createAdnServiceEntry(@PathVariable long serviceId, @PathVariable long zoneId, @RequestBody AdnL4ServiceBean adnL4ServiceBean) {
        adnL4ServiceBean.setId(serviceId);
        adnL4ServiceBean.setZoneList(new LinkedList<>());
        adnL4ServiceBean.getZoneList().add(zoneId);
        adnL4ServiceService.modifyAdnServiceEntry(adnL4ServiceBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> modify4LayerService(@PathVariable long serviceId, @RequestBody AdnL4ServiceBean adnL4ServiceBean) {
        adnL4ServiceBean.setId(serviceId);
        adnL4ServiceService.modify4LayerService(adnL4ServiceBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{serviceId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> remove4LayerService(@PathVariable long serviceId) {
        adnL4ServiceService.remove4LayerService(serviceId);
        return ResponseEntity.ok(null);
    }
}