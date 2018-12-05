package com.ccit.web.controller;

import com.ccit.service.AdnServiceZoneService;
import com.ccit.vo.AdnServiceZoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/adn")
public class AdnServiceZoneController extends BaseController {

    @Autowired
    private AdnServiceZoneService adnServiceZoneService;

    @RequestMapping(value = "/services/{serviceType}/{serviceId}/zones", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getServiceZone(@PathVariable long serviceId, @PathVariable String serviceType) {
        List<AdnServiceZoneVo> serviceZoneVos = adnServiceZoneService.getServiceZone(serviceId, serviceType);
        return ResponseEntity.ok(serviceZoneVos);
    }


}
