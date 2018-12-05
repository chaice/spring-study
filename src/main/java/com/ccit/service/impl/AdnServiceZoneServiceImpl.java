package com.ccit.service.impl;


import com.ccit.dao.AdnServiceZoneDao;
import com.ccit.dao.AdnZoneDao;
import com.ccit.entity.AdnServiceZoneEntity;
import com.ccit.entity.AdnZoneEntity;
import com.ccit.service.AdnServiceZoneService;
import com.ccit.vo.AdnServiceZoneVo;
import com.ccit.vo.AdnZoneVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AdnServiceZoneServiceImpl implements AdnServiceZoneService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AdnServiceZoneDao adnServiceZoneDao;

    @Autowired
    private AdnZoneDao adnZoneDao;

    @Override
    public List<AdnServiceZoneVo> getServiceZone(long serviceId, String serviceType) {
        List<AdnServiceZoneVo> vos = new ArrayList<>();
        List<AdnServiceZoneEntity> adnServiceZoneEntities = adnServiceZoneDao.findByServiceId(serviceId, serviceType);
        adnServiceZoneEntities.forEach(adnServiceZoneEntity -> {
            AdnServiceZoneVo vo = new AdnServiceZoneVo(adnServiceZoneEntity);

            AdnZoneEntity zoneEntity = adnZoneDao.findOne(adnServiceZoneEntity.getZoneId());
            vo.setAdnZone(new AdnZoneVo(zoneEntity.getId(),zoneEntity.getZoneName(),zoneEntity.getLongitude(),zoneEntity.getLatitude()));
            vos.add(vo);

        });
        return vos;
    }
}
