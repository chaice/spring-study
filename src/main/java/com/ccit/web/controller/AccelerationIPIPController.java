package com.ccit.web.controller;

import com.ccit.bean.BoxInternetCafeBean;
import com.ccit.service.AccelerationIPIPService;
import com.ccit.vo.BoxInternetCafeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/acceleration/internet_cafe/boxes")
public class AccelerationIPIPController extends BaseController {

    @Autowired
    private AccelerationIPIPService accelerationIPIPService;


    /**
     * 盒子加速业务列表
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        List<BoxInternetCafeVo> boxVos = accelerationIPIPService.listAcceleration();
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
        return ResponseEntity.ok(accelerationIPIPService.getAcceleration(boxId));
    }


    /**
     * 更新盒子加速业务
     *
     * @param boxId
     * @param boxBean
     * @return
     */
    @RequestMapping(value = "/{boxId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long boxId, @RequestBody BoxInternetCafeBean boxBean) {
        verifyAccelerationIPIP(boxBean);
        boxBean.setId(boxId);
        accelerationIPIPService.updateAcceleration(boxBean);
        return ResponseEntity.ok(null);
    }
}