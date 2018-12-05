package com.ccit.service.impl;
import com.ccit.bean.PerformanceQueryBean;
import com.ccit.entity.PerformanceAdnEntryTrafficEntity;
import com.ccit.service.ConvertService;
import com.ccit.vo.PerformanceTrafficVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 公共业务转换类
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ConvertServiceImpl implements ConvertService {

    public List<PerformanceTrafficVo> convertTrafficEntityToVo(List<PerformanceAdnEntryTrafficEntity> trafficList, PerformanceQueryBean performanceQueryBean) {
        Map<Timestamp, PerformanceTrafficVo> trafficVoMap = new ConcurrentHashMap<>();
        for (PerformanceAdnEntryTrafficEntity trafficEntity : trafficList) {
            PerformanceTrafficVo trafficVo = new PerformanceTrafficVo();
            trafficVo.setSamplingTime(trafficEntity.getSamplingTime());
            trafficVo.setThroughputTX(trafficEntity.getDifferenceThroughputTx());
            trafficVo.setThroughputRX(trafficEntity.getDifferenceThroughputRx());
            trafficVo.setBandwidthTX(trafficEntity.getBandwidthTx());
            trafficVo.setBandwidthRX(trafficEntity.getBandwidthRx());

            trafficVoMap.put(trafficEntity.getSamplingTime(), trafficVo);
        }
        return fillTrafficGap(trafficVoMap, performanceQueryBean);
    }

    private List<PerformanceTrafficVo> fillTrafficGap(Map<Timestamp, PerformanceTrafficVo> trafficVoMap, PerformanceQueryBean performanceQueryBean) {
        List<PerformanceTrafficVo> trafficVoList = new LinkedList<>();

        long period = performanceQueryBean.getInterval() * 1000;
        long timeIndex;

        if ((performanceQueryBean.getBeginTime().getTime() + PerformanceQueryBean.tz.getRawOffset()) % period == 0) {
            timeIndex = performanceQueryBean.getBeginTime().getTime();
        } else {
            timeIndex = ((performanceQueryBean.getBeginTime().getTime() + PerformanceQueryBean.tz.getRawOffset()) / period + 1) * period - PerformanceQueryBean.tz.getRawOffset();
        }

        while (timeIndex < performanceQueryBean.getEndTime().getTime()) {
            Timestamp samplingTime = new Timestamp(timeIndex);
            PerformanceTrafficVo trafficVo = trafficVoMap.get(samplingTime);
            if (trafficVo == null) {
                trafficVo = new PerformanceTrafficVo();
                trafficVo.setSamplingTime(samplingTime);
            }
            trafficVoList.add(trafficVo);
            timeIndex += period;
        }

        return trafficVoList;
    }

}