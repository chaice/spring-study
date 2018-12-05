package com.ccit.web.controller;

import com.ccit.bean.AdnEntryBean;
import com.ccit.service.AdnEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/adn/entries")
public class AdnEntryController extends BaseController {

    @Autowired
    private AdnEntryService adnEntryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listEntry(@RequestParam(value = "entryType", required = false) String entryType, @RequestParam(value = "serviceId", required = false) Long serviceId, @RequestParam(value = "zoneId", required = false) Long zoneId) {
        if (entryType != null) {
            return ResponseEntity.ok(adnEntryService.listUsableEntry(entryType, serviceId, zoneId));
        }
        return ResponseEntity.ok(adnEntryService.listEntry());
    }

    @RequestMapping(value = "/{entryId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getEntry(@PathVariable long entryId) {
        return ResponseEntity.ok(adnEntryService.getEntry(entryId));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createEntry(@RequestBody AdnEntryBean adnEntryBean) {
        verifyAdnEntryBean(adnEntryBean);
        adnEntryService.createEntry(adnEntryBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{entryId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> modifyEntry(@PathVariable long entryId, @RequestBody AdnEntryBean adnEntryBean) {
        adnEntryBean.setId(entryId);
        verifyAdnEntryBean(adnEntryBean);
        adnEntryService.modifyEntry(adnEntryBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{entryId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeEntry(@PathVariable long entryId) {
        adnEntryService.removeEntry(entryId);
        return ResponseEntity.ok(null);
    }

}
