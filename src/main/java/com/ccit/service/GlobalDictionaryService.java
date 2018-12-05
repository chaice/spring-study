package com.ccit.service;


import com.ccit.vo.DictionaryVo;

public interface GlobalDictionaryService {

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    String fromKey(String key);


    /**
     * 清楚缓存
     */
    void clear();


    /**
     * 根据id查询
     * @param id
     * @return
     */
    DictionaryVo getById(long id);

}
