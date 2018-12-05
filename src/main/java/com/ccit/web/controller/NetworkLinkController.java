package com.ccit.web.controller;

import com.ccit.bean.NetworkLinkBean;
import com.ccit.service.NetworkLinkService;
import com.ccit.vo.NetworkLinkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/network/links")
public class NetworkLinkController extends BaseController {

    @Autowired
    private NetworkLinkService networkLinkService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> list() {
        List<NetworkLinkVo> networkLinkVoList = networkLinkService.listNetworkLink();
        return ResponseEntity.ok(networkLinkVoList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(networkLinkService.getNetworkLink(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> syncLink(@PathVariable long id, @RequestBody NetworkLinkBean networkLinkBean) {
        verifyNetworkLink(networkLinkBean);
        networkLinkBean.setId(id);
        networkLinkService.syncLink(networkLinkBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<Object> syncLinksStatus(@RequestBody List<NetworkLinkBean> linkBeanList) {
        verifyLinkBeanList(linkBeanList);
        networkLinkService.syncLinksStatus(linkBeanList);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<Object> modifyPart(@PathVariable long id,@RequestBody NetworkLinkBean networkLinkBean) {
        verifyNetworkLinkBean(networkLinkBean);
        networkLinkBean.setId(id);
        networkLinkService.modifyPartLink(networkLinkBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> remove(@PathVariable long id) {
        networkLinkService.removeLink(id);
        return ResponseEntity.ok(null);
    }

}
