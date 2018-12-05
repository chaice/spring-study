package com.ccit.web.controller;

import com.ccit.bean.AccelerationTargetBean;
import com.ccit.service.AccelerationTargetService;
import com.ccit.vo.AccelerationTargetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/acceleration/targets")
public class AccelerationTargetController extends BaseController {

    @Autowired
    private AccelerationTargetService accelerationTargetService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> list() {
        List<AccelerationTargetVo> accelerationTargetVos = accelerationTargetService.listAccelerationTarget();
        return ResponseEntity.ok(accelerationTargetVos);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable long id) {
        AccelerationTargetVo accelerationTargetVo = accelerationTargetService.getById(id);
        return ResponseEntity.ok(accelerationTargetVo);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody List<AccelerationTargetBean> accelerationTargetBeanList) {
        for(AccelerationTargetBean accelerationTargetBean : accelerationTargetBeanList){
            verifyAccelerationTarget(accelerationTargetBean);
        }
        accelerationTargetService.updateAccelerationTargets(accelerationTargetBeanList);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody AccelerationTargetBean accelerationTargetBean) {
        verifyAccelerationTarget(accelerationTargetBean);
        accelerationTargetService.updateAccelerationTarget(accelerationTargetBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> remove(@PathVariable long id) {
        accelerationTargetService.removeAccelerationTarget(id);
        return ResponseEntity.ok(null);
    }

}
