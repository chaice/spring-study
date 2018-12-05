package com.ccit.service.impl;


import com.ccit.dao.AdnEntryDao;
import com.ccit.dao.AdnServiceEntryDao;
import com.ccit.entity.AdnEntryEntity;
import com.ccit.entity.AdnServiceEntryEntity;
import com.ccit.service.AdnServiceEntryService;
import com.ccit.vo.AdnServiceEntryVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AdnServiceEntryServiceImpl implements AdnServiceEntryService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AdnServiceEntryDao adnServiceEntryDao;

    @Autowired
    private AdnEntryDao adnEntryDao;


    @Override
    public List<AdnServiceEntryVo> getServiceEntry(long serviceId, String serviceType, long zoneId) {
        List<AdnServiceEntryEntity> adnServiceEntryEntityList = adnServiceEntryDao.findByServiceTypeServiceIdZoneId(serviceType, serviceId, zoneId);
        List<AdnServiceEntryVo> adnServiceEntryVos = new LinkedList<>();
        adnServiceEntryEntityList.forEach(adnServiceEntryEntity -> {
            AdnEntryEntity adnEntryEntity = adnEntryDao.findOne(adnServiceEntryEntity.getEntryId());
            adnServiceEntryVos.add(new AdnServiceEntryVo(adnEntryEntity));

        });

        return adnServiceEntryVos;
    }
}
