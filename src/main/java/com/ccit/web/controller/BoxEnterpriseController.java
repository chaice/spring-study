package com.ccit.web.controller;

import com.ccit.bean.BoxEnterpriseBean;
import com.ccit.service.BoxEnterpriseService;
import com.ccit.vo.BoxEnterpriseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/enterprise/boxes")
public class BoxEnterpriseController extends BaseController {

    @Autowired
    private BoxEnterpriseService boxEnterpriseService;


    /**
     * 盒子列表
     * @param sn
     * @param customerName
     * @param sort
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> list(String sn, String customerName, String sort) {
        List<BoxEnterpriseVo> boxVoList = boxEnterpriseService.listBox(sn, customerName, sort);

        return ResponseEntity.ok(boxVoList);
    }


    /**
     * 创建盒子
     * @param boxBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody BoxEnterpriseBean boxBean) {
        verifyBoxEnterprise(boxBean);
        boxEnterpriseService.saveBox(boxBean);
        return ResponseEntity.ok(null);
    }


    /**
     * 盒子详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(boxEnterpriseService.getById(id));
    }


//    /**
//     * 更新盒子
//     * @param id
//     * @param boxBean
//     * @return
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody BoxEnterpriseBean boxBean) {
//        verifyBoxEnterprise(boxBean);
//        boxBean.setId(id);
//        boxBean.setLastUid(UserInterceptor.getLocalUser().getId());
//
//        boxEnterpriseService.updateBox(boxBean);
//
//        return ResponseEntity.ok(null);
//    }


    /**
     * 更新盒子部分数据
     * @param id
     * @param boxBean
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<Object> updatePart(@PathVariable long id, @RequestBody BoxEnterpriseBean boxBean) {
        boxBean.setId(id);
        boxEnterpriseService.updateBoxPart(boxBean);
        return ResponseEntity.ok(null);
    }


    /**
     * 删除盒子
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> remove(@PathVariable long id) {
        boxEnterpriseService.removeBox(id);
        return ResponseEntity.ok(null);
    }

    /**
     * 更新盒子部分数据
     * @param boxEnterpriseBeanList
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<Object> updatePart(@RequestBody List<BoxEnterpriseBean> boxEnterpriseBeanList) {
        boxEnterpriseService.updateBoxPart(boxEnterpriseBeanList);
        return ResponseEntity.ok(null);
    }

}