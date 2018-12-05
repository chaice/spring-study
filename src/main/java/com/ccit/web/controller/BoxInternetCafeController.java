package com.ccit.web.controller;

import com.ccit.bean.BoxInternetCafeBean;
import com.ccit.service.BoxInternetCafeService;
import com.ccit.vo.BoxInternetCafeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/internet_cafe/boxes")
public class BoxInternetCafeController extends BaseController {

    @Autowired
    private BoxInternetCafeService boxInternetCafeService;


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
        List<BoxInternetCafeVo> boxVoList = boxInternetCafeService.listBox(sn, customerName, sort);

        return ResponseEntity.ok(boxVoList);
    }


    /**
     * 创建盒子
     * @param boxBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody BoxInternetCafeBean boxBean) {
        verifyBoxInternetCafe(boxBean);
        boxInternetCafeService.saveBox(boxBean);
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
        return ResponseEntity.ok(boxInternetCafeService.getById(id));
    }


//    /**
//     * 更新盒子
//     * @param id
//     * @param boxBean
//     * @return
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody BoxInternetCafeBean boxBean) {
//        verifyBoxInternetCafe(boxBean);
//        boxBean.setId(id);
//        boxBean.setLastUid(UserInterceptor.getLocalUser().getId());
//
//        boxInternetCafeService.updateBox(boxBean);
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
    public ResponseEntity<Object> updatePart(@PathVariable long id, @RequestBody BoxInternetCafeBean boxBean) {
        boxBean.setId(id);
        boxInternetCafeService.updateBoxPart(boxBean);
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
        boxInternetCafeService.removeBox(id);
        return ResponseEntity.ok(null);
    }

    /**
     * 更新盒子部分数据
     * @param boxInternetCafeBeanList
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<Object> updatePart(@RequestBody List<BoxInternetCafeBean> boxInternetCafeBeanList) {
        boxInternetCafeService.updateBoxPart(boxInternetCafeBeanList);
        return ResponseEntity.ok(null);
    }

}