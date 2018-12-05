package com.ccit.task;

import com.ccit.service.AccelerationCollectionEnterpriseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CollectionTask {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AccelerationCollectionEnterpriseService accelerationCollectionEnterpriseService;

    @Scheduled(fixedRate = 60000)
    public void modifyCidrByCountry() {
//        accelerationCollectionEnterpriseService.listCidrByNullCountry(10).forEach(accelerationCidrEnterpriseVo -> {
//            accelerationCollectionEnterpriseService.modifyCidrByCountry(accelerationCidrEnterpriseVo.getId());
//
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                logger.error("Thread Sleep Error", e);
//            }
//        });
    }
}
