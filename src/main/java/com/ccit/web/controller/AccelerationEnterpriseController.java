package com.ccit.web.controller;

import com.ccit.bean.BoxEnterpriseBean;
import com.ccit.service.AccelerationEnterpriseService;
import com.ccit.vo.BoxEnterpriseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/acceleration/enterprise/boxes")
public class AccelerationEnterpriseController extends BaseController {

    @Autowired
    private AccelerationEnterpriseService accelerationEnterpriseService;


    /**
     * 盒子加速业务列表
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        List<BoxEnterpriseVo> boxVos = accelerationEnterpriseService.listAcceleration();
        return ResponseEntity.ok(boxVos);
    }


    /**
     * 盒子加速业务详情
     *
     * @param boxId
     * @return
     */
    @RequestMapping(value = "/{boxId}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable long boxId) {
        return ResponseEntity.ok(accelerationEnterpriseService.getAcceleration(boxId));
    }


    /**
     * 更新盒子加速业务
     *
     * @param boxId
     * @param boxBean
     * @return
     */
    @RequestMapping(value = "/{boxId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long boxId, @RequestBody BoxEnterpriseBean boxBean) {
        verifyAccelerationShadowsocks(boxBean);
        boxBean.setId(boxId);
        accelerationEnterpriseService.updateAcceleration(boxBean);
        return ResponseEntity.ok(null);
    }
}