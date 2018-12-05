package com.ccit.web.controller;

import com.ccit.bean.AdnCustomerDomainBean;
import com.ccit.bean.AdnCustomerZoneBean;
import com.ccit.service.AdnCustomerDomainService;
import com.ccit.service.AdnEntryService;
import com.ccit.service.AdnZoneService;
import com.ccit.vo.AdnCustomerDomainVo;
import com.ccit.vo.AdnZoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/adn/customers/{customerId}")
public class AdnCustomerController extends BaseController {

    @Autowired
    private AdnZoneService adnZoneService;

    @Autowired
    private AdnEntryService adnEntryService;

    @Autowired
    private AdnCustomerDomainService adnCustomerDomainService;

    @RequestMapping(value = "/zones", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> modifyCustomerZone(@PathVariable long customerId, @RequestBody AdnCustomerZoneBean adnCustomerZoneBean) {
        adnCustomerZoneBean.setCustomerId(customerId);
        adnZoneService.modifyCustomerZone(adnCustomerZoneBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/zones", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getCustomerZone(@PathVariable long customerId) {
        List<AdnZoneVo> adnZoneVos = adnZoneService.getZones(customerId);
        return ResponseEntity.ok(adnZoneVos);
    }

    @RequestMapping(value = "/domains", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listCustomerDomain(@PathVariable long customerId) {
        List<AdnCustomerDomainVo> adnCustomerDomainVos = adnCustomerDomainService.listCustomerDomain(customerId);
        return ResponseEntity.ok(adnCustomerDomainVos);
    }

    @RequestMapping(value = "/domains", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addCustomerDomain(@PathVariable long customerId, @RequestBody AdnCustomerDomainBean adnCustomerDomainBean){
        verifyAdnCustomerDomainBean(adnCustomerDomainBean);
        adnCustomerDomainBean.setCustomerId(customerId);
        adnCustomerDomainService.addCustomerDomain(adnCustomerDomainBean);
        return ResponseEntity.ok(null);
    }

}
