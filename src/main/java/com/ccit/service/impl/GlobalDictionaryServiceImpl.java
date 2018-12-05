package com.ccit.service.impl;

import com.ccit.exception.ResourceException;
import com.ccit.dao.GlobalDictionaryDao;
import com.ccit.entity.GlobalDictionaryEntity;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.service.GlobalDictionaryService;
import com.ccit.util.StringUtils;
import com.ccit.vo.DictionaryVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class GlobalDictionaryServiceImpl implements GlobalDictionaryService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private GlobalDictionaryDao globalDictionaryDao;

    // 缓存名称
    private final String CACHE_KEY = "dictionary";


    @Cacheable(value = CACHE_KEY, key = "'info.value.'+#key")
    public String fromKey(String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }

        GlobalDictionaryEntity dictionaryEntity = globalDictionaryDao.findByKey(key);
        return null == dictionaryEntity ? "" : dictionaryEntity.getValue();
    }


    @CacheEvict(value = CACHE_KEY, allEntries = true)
    public void clear() {
        logger.info("清除缓存" + CACHE_KEY);
    }


    @Cacheable(value = CACHE_KEY, key = "'info.detail.'+#id")
    public DictionaryVo getById(long id) {
        logger.info("查询数据字典by id:" + id);

        GlobalDictionaryEntity dictionaryEntity = globalDictionaryDao.findOne(id);
        Validator.notNull(dictionaryEntity, ResourceException.error(CustomerErrorConstants.DICTIONARY_NOT_EXIST));

        DictionaryVo dictionaryVo = new DictionaryVo();
        dictionaryVo.setId(dictionaryEntity.getId());
        dictionaryVo.setKey(dictionaryEntity.getKey());
        dictionaryVo.setValue(dictionaryEntity.getValue());
        dictionaryVo.setRemark(dictionaryEntity.getRemark());

        return dictionaryVo;
    }



}
