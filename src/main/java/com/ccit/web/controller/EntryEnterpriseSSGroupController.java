package com.ccit.web.controller;

import com.ccit.bean.EntryEnterpriseSSGroupBean;
import com.ccit.service.EntryEnterpriseService;
import com.ccit.vo.EntryEnterpriseSSGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/enterprise/ss/groups")
public class EntryEnterpriseSSGroupController extends BaseController {

    @Autowired
    private EntryEnterpriseService businessEntryService;

    /**
     * 加速入口列表
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        List<EntryEnterpriseSSGroupVo> entryVos = businessEntryService.listSSEntryGroup();
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
        return ResponseEntity.ok(businessEntryService.getSSEntryGroup(id));
    }

    /**
     * 新增加速入口
     *
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody EntryEnterpriseSSGroupBean entryBean) {
        verifyEntryEnterpriseGroupSS(entryBean);
        businessEntryService.createSSEntryGroup(entryBean);
        return ResponseEntity.ok(null);
    }

    /**
     * 更新加速入口
     *
     * @param groupId
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "/{groupId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long groupId, @RequestBody EntryEnterpriseSSGroupBean entryBean) {
        entryBean.setId(groupId);
        if (entryBean.getNeedSync() != null) {
            businessEntryService.syncSSEntryGroup(entryBean);
        } else {
            verifyEntryEnterpriseGroupSS(entryBean);
            businessEntryService.updateSSEntryGroup(entryBean);
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 删除加速入口
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable long groupId) {
        businessEntryService.removeSSEntryGroup(groupId);
        return ResponseEntity.ok(null);
    }

}