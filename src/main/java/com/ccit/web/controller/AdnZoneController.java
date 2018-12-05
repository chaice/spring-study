package com.ccit.web.controller;

import com.ccit.bean.AdnZoneBean;
import com.ccit.service.AdnZoneService;
import com.ccit.vo.AdnZoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/adn/zones")
public class AdnZoneController extends BaseController {

    @Autowired
    private AdnZoneService adnZoneService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listZone() {
        List<AdnZoneVo> adnZoneVos = adnZoneService.listZone();
        return ResponseEntity.ok(adnZoneVos);
    }

    @RequestMapping(value = "/{zoneId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getZone(@PathVariable long zoneId) {
        AdnZoneVo adnZoneVo = adnZoneService.getZone(zoneId);
        return ResponseEntity.ok(adnZoneVo);
    }

    @RequestMapping(value = "/{zoneId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeZone(@PathVariable long zoneId) {
        adnZoneService.removeZone(zoneId);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> addZone(@RequestBody AdnZoneBean adnZoneBean) {
        verifyAdnZoneBean(adnZoneBean);
        adnZoneService.addZone(adnZoneBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{zoneId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> modifyZone(@PathVariable long zoneId, @RequestBody AdnZoneBean adnZoneBean) throws IOException {
        verifyAdnZoneBean(adnZoneBean);
        adnZoneBean.setId(zoneId);
        adnZoneService.modifyZone(adnZoneBean);
        return ResponseEntity.ok(null);
    }

}
