package com.ccit.web.controller;

import com.ccit.bean.EntryEnterpriseL2TPBean;
import com.ccit.service.EntryEnterpriseService;
import com.ccit.vo.EntryEnterpriseL2TPVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/enterprise/l2tp/entries")
public class EntryEnterpriseL2TPController extends BaseController {

    @Autowired
    private EntryEnterpriseService businessEntryService;

    /**
     * 加速入口列表
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        List<EntryEnterpriseL2TPVo> entryVos = businessEntryService.searchL2tpEntry();
        return ResponseEntity.ok(entryVos);
    }

    /**
     * 加速入口详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(businessEntryService.getL2tpById(id));
    }

    /**
     * 新增加速入口
     *
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody EntryEnterpriseL2TPBean entryBean) {
        verifyEntryEnterpriseL2TP(entryBean);
        businessEntryService.createL2tpEntry(entryBean);
        return ResponseEntity.ok(null);
    }

    /**
     * 更新加速入口
     *
     * @param id
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody EntryEnterpriseL2TPBean entryBean) {
        entryBean.setId(id);
        if (entryBean.getNeedSync() != null) {
            businessEntryService.syncL2tpEntry(entryBean);
        } else {
            verifyEntryEnterpriseL2TP(entryBean);
            businessEntryService.updateL2tpEntry(entryBean);
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 删除加速入口
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable long id) {
        businessEntryService.removeL2tpEntry(id);
        return ResponseEntity.ok(null);
    }

}