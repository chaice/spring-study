package com.ccit.web.controller;

import com.ccit.service.GlobalDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dictionaries")
public class DictionaryController {

    @Autowired
    private GlobalDictionaryService dictionaryService;


    /**
     * 数据字典详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable long id){
        return ResponseEntity.ok(dictionaryService.getById(id));
    }


    /**
     * 清除数据字典缓存
     * @return
     */
    @RequestMapping(value = "/service/clear", method = RequestMethod.PUT)
    public ResponseEntity<Object> clear(){
        dictionaryService.clear();
        return ResponseEntity.ok(null);
    }

}