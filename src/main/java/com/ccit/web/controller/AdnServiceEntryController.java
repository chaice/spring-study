package com.ccit.web.controller;

import com.ccit.service.AdnServiceEntryService;
import com.ccit.vo.AdnServiceEntryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/adn/services")
public class AdnServiceEntryController extends BaseController {

    @Autowired
    private AdnServiceEntryService adnServiceEntryService;

    @RequestMapping(value = "/{serviceType}/{serviceId}/zones/{zoneId}/entries", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getEntry(
            @PathVariable long serviceId,
            @PathVariable String serviceType,
            @PathVariable long zoneId) {
        List<AdnServiceEntryVo> serviceEntryVos = adnServiceEntryService.getServiceEntry(serviceId, serviceType, zoneId);
        return ResponseEntity.ok(serviceEntryVos);
    }


}
