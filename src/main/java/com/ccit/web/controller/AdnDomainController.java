package com.ccit.web.controller;

import com.ccit.bean.AdnCustomerDomainBean;
import com.ccit.service.AdnCustomerDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/adn/domains")
public class AdnDomainController extends BaseController {

    @Autowired
    private AdnCustomerDomainService adnCustomerDomainService;

    @RequestMapping(value = "/{domainId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeCustomerDomain(@PathVariable long domainId) {
        adnCustomerDomainService.removeCustomerDomain(domainId);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{domainId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> modifyCustomerDomain(@PathVariable long domainId,@RequestBody AdnCustomerDomainBean adnCustomerDomainBean) {
        verifyAdnCustomerDomainBean(adnCustomerDomainBean);
        adnCustomerDomainBean.setId(domainId);
        adnCustomerDomainService.modifyCustomerDomain(adnCustomerDomainBean);
        return ResponseEntity.ok(null);
    }


}
