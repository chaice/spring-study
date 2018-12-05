package com.ccit.service.impl;

import com.ccit.dao.*;
import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.google.gson.Gson;
import com.ccit.bean.AccelerationCidrEnterpriseBean;
import com.ccit.bean.AccelerationCollectionEnterpriseBean;
import com.ccit.bean.AccelerationDomainEnterpriseBean;
import com.ccit.bean.AccelerationIpsetEnterpriseBean;
import com.ccit.dao.*;
import com.ccit.entity.AccelerationCidrEnterpriseEntity;
import com.ccit.entity.AccelerationCollectionEnterpriseEntity;
import com.ccit.entity.AccelerationDomainEnterpriseEntity;
import com.ccit.entity.AccelerationIpsetEnterpriseEntity;
import com.ccit.enums.IpsetAccelerateMode;
import com.ccit.enums.TransportProtocolEnum;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.CommonRequest;
import com.ccit.rest.RestClient;
import com.ccit.rest.RestReturn;
import com.ccit.rest.sextant.interfaces.CollectionIF;
import com.ccit.rest.sextant.request.CollectionReq;
import com.ccit.rest.sextant.request.IpsetReq;
import com.ccit.rest.sextant.response.BoxRes;
import com.ccit.service.AccelerationCollectionEnterpriseService;
import com.ccit.util.StringUtils;
import com.ccit.vo.AccelerationCidrEnterpriseVo;
import com.ccit.vo.AccelerationCollectionEnterpriseVo;
import com.ccit.vo.AccelerationDomainEnterpriseVo;
import com.ccit.vo.AccelerationIpsetEnterpriseVo;
import com.ccit.web.interceptor.UserInterceptor;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;


@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AccelerationCollectionEnterpriseServiceImpl implements AccelerationCollectionEnterpriseService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AccelerationCollectionEnterpriseDao accelerationCollectionEnterpriseDao;

    @Autowired
    private AccelerationIpsetEnterpriseDao accelerationIpsetEnterpriseDao;

    @Autowired
    private AccelerationCidrEnterpriseDao accelerationCidrEnterpriseDao;

    @Autowired
    private AccelerationDomainEnterpriseDao accelerationDomainEnterpriseDao;

    @Autowired
    private BoxCollectionDao boxCollectionDao;

    @Autowired
    private Gson gson;

    @Autowired
    private Assembler<AccelerationCollectionEnterpriseBean, AccelerationCollectionEnterpriseEntity, AccelerationCollectionEnterpriseVo> collectionEnterpriseAssembler;

    @Autowired
    private Assembler<AccelerationIpsetEnterpriseBean, AccelerationIpsetEnterpriseEntity, AccelerationIpsetEnterpriseVo> ipsetEnterpriseAssembler;

    @Autowired
    private Assembler<AccelerationCidrEnterpriseBean, AccelerationCidrEnterpriseEntity, AccelerationCidrEnterpriseVo> cidrEnterpriseAssembler;

    @Autowired
    private Assembler<AccelerationDomainEnterpriseBean, AccelerationDomainEnterpriseEntity, AccelerationDomainEnterpriseVo> domainEnterpriseAssembler;

    @Autowired
    @Qualifier("sextantCollectionIF")
    private CollectionIF collectionIF;

    @Override
    public List<AccelerationCollectionEnterpriseVo> listAllCollection() {
        List<AccelerationCollectionEnterpriseEntity> accelerationCollectionEnterpriseEntityList = accelerationCollectionEnterpriseDao.findAll();

        List<AccelerationCollectionEnterpriseVo> collectionEnterpriseVoList = new LinkedList<>();

        if (accelerationCollectionEnterpriseEntityList != null) {
            for (AccelerationCollectionEnterpriseEntity accelerationCollectionEnterpriseEntity : accelerationCollectionEnterpriseEntityList) {
                List<AccelerationIpsetEnterpriseEntity> ipsetEnterpriseEntityList = accelerationIpsetEnterpriseDao.findByCollectionId(accelerationCollectionEnterpriseEntity.getId());

                AccelerationCollectionEnterpriseVo collectionEnterpriseVo = collectionEnterpriseAssembler.toVo(accelerationCollectionEnterpriseEntity);
                collectionEnterpriseVo.setIpsetList(ipsetEnterpriseAssembler.toVos(ipsetEnterpriseEntityList));
                collectionEnterpriseVoList.add(collectionEnterpriseVo);
            }
        }
        return collectionEnterpriseVoList;
    }

    @Override
    public List<AccelerationCollectionEnterpriseVo> listCollectionByAccelerateMode(String accelerateMode) {
        List<AccelerationCollectionEnterpriseEntity> accelerationCollectionEnterpriseEntityList = accelerationCollectionEnterpriseDao.findByAccelerateMode(accelerateMode);

        List<AccelerationCollectionEnterpriseVo> collectionEnterpriseVoList = new LinkedList<>();

        if (accelerationCollectionEnterpriseEntityList != null) {
            for (AccelerationCollectionEnterpriseEntity accelerationCollectionEnterpriseEntity : accelerationCollectionEnterpriseEntityList) {
                List<AccelerationIpsetEnterpriseEntity> ipsetEnterpriseEntityList = accelerationIpsetEnterpriseDao.findByCollectionId(accelerationCollectionEnterpriseEntity.getId());

                AccelerationCollectionEnterpriseVo collectionEnterpriseVo = collectionEnterpriseAssembler.toVo(accelerationCollectionEnterpriseEntity);
                collectionEnterpriseVo.setIpsetList(ipsetEnterpriseAssembler.toVos(ipsetEnterpriseEntityList));
                collectionEnterpriseVoList.add(collectionEnterpriseVo);
            }
        }
        return collectionEnterpriseVoList;
    }


    @Override
    public List<AccelerationCollectionEnterpriseVo> listCommonCollectionByAccelerateMode(String accelerateMode) {
        List<AccelerationCollectionEnterpriseEntity> accelerationCollectionEnterpriseEntityList = accelerationCollectionEnterpriseDao.findByCollectionTypeAndAccelerateMode("common", accelerateMode);

        List<AccelerationCollectionEnterpriseVo> collectionEnterpriseVoList = new LinkedList<>();

        accelerationCollectionEnterpriseEntityList.forEach(accelerationCollectionEnterpriseEntity -> {
            List<AccelerationIpsetEnterpriseEntity> ipsetEnterpriseEntityList = accelerationIpsetEnterpriseDao.findByCollectionId(accelerationCollectionEnterpriseEntity.getId());

            AccelerationCollectionEnterpriseVo collectionEnterpriseVo = collectionEnterpriseAssembler.toVo(accelerationCollectionEnterpriseEntity);
            collectionEnterpriseVo.setIpsetList(ipsetEnterpriseAssembler.toVos(ipsetEnterpriseEntityList));
            collectionEnterpriseVoList.add(collectionEnterpriseVo);
        });
        return collectionEnterpriseVoList;
    }

    @Override
    public AccelerationCollectionEnterpriseVo getCollectionById(long id) {
        AccelerationCollectionEnterpriseEntity collectionEnterpriseEntity = accelerationCollectionEnterpriseDao.findOne(id);

        Validator.notNull(collectionEnterpriseEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_COLLECTION_NOT_EXIST));

        List<AccelerationIpsetEnterpriseEntity> ipsetEnterpriseEntityList = accelerationIpsetEnterpriseDao.findByCollectionId(collectionEnterpriseEntity.getId());
        AccelerationCollectionEnterpriseVo collectionEnterpriseVo = collectionEnterpriseAssembler.toVo(collectionEnterpriseEntity);
        collectionEnterpriseVo.setIpsetList(ipsetEnterpriseAssembler.toVos(ipsetEnterpriseEntityList));

        return collectionEnterpriseVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createCollection(AccelerationCollectionEnterpriseBean collectionBean) {
        AccelerationCollectionEnterpriseEntity collectionEnterpriseEntity = accelerationCollectionEnterpriseDao.findByCollectionNameAndAccelerateMode(collectionBean.getCollectionName(), collectionBean.getAccelerateMode());
        if (collectionEnterpriseEntity == null) {
            collectionEnterpriseEntity = collectionEnterpriseAssembler.fromBean(collectionBean);
            accelerationCollectionEnterpriseDao.saveOrUpdate(collectionEnterpriseEntity);
            //同步数据到sextant
            updateCollectionOfSextant(collectionEnterpriseEntity);
        } else {
            throw new BusinessException(CustomerErrorConstants.ACCELERATION_COLLECTION_REPEAT);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyCollection(AccelerationCollectionEnterpriseBean collectionBean) {
        //先验证传过来的bean中的id是否存在
        AccelerationCollectionEnterpriseEntity collectionEnterpriseEntity = accelerationCollectionEnterpriseDao.findOne(collectionBean.getId());
        Validator.notNull(collectionEnterpriseEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_COLLECTION_NOT_EXIST));
        AccelerationCollectionEnterpriseEntity enterpriseEntity = accelerationCollectionEnterpriseDao.findByCollectionNameAndAccelerateMode(collectionBean.getCollectionName(), collectionEnterpriseEntity.getAccelerateMode());
        Validator.isTrue(enterpriseEntity == null || enterpriseEntity.getId() == collectionEnterpriseEntity.getId(),
                BusinessException.error(CustomerErrorConstants.ACCELERATION_COLLECTION_REPEAT)
        );

        collectionEnterpriseEntity.setCollectionName(collectionBean.getCollectionName());
        collectionEnterpriseEntity.setCollectionDescription(collectionBean.getCollectionDescription());
        accelerationCollectionEnterpriseDao.saveOrUpdate(collectionEnterpriseEntity);
        //同步数据到sextant
        updateCollectionOfSextant(collectionEnterpriseEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeCollection(long id) {
        accelerationCollectionEnterpriseDao.deleteById(id);
        accelerationIpsetEnterpriseDao.deleteByCollectionId(id);
        accelerationDomainEnterpriseDao.deleteByCollectonId(id);
        accelerationCidrEnterpriseDao.deleteByCollectionId(id);
        boxCollectionDao.deleteByCollectionId(id);
        //同步数据到sextant
        deleteCollectionOfSextant(id);
    }

    public AccelerationIpsetEnterpriseVo getIpsetById(long ipsetId) {
        AccelerationIpsetEnterpriseEntity ipsetEnterpriseEntity = accelerationIpsetEnterpriseDao.findOne(ipsetId);

        Validator.notNull(ipsetEnterpriseEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_IPSET_NOT_EXIST));

        //根据集合id获取域名和加速地址段
        List<AccelerationCidrEnterpriseEntity> cidrEnterpriseEntityList = accelerationCidrEnterpriseDao.findByIpsetId(ipsetEnterpriseEntity.getId());
        List<AccelerationDomainEnterpriseEntity> domainEnterpriseEntities = accelerationDomainEnterpriseDao.findByIpsetId(ipsetEnterpriseEntity.getId());

        AccelerationIpsetEnterpriseVo ipsetEnterpriseVo = ipsetEnterpriseAssembler.toVo(ipsetEnterpriseEntity);

        ipsetEnterpriseVo.setCidrList(cidrEnterpriseAssembler.toVos(cidrEnterpriseEntityList));
        ipsetEnterpriseVo.setDomainList(domainEnterpriseAssembler.toVos(domainEnterpriseEntities));

        return ipsetEnterpriseVo;
    }

    public List<AccelerationIpsetEnterpriseVo> listIpsetByCollectionId(long collectionId) {
        List<AccelerationIpsetEnterpriseEntity> accelerationIpsetEnterpriseEntityList = accelerationIpsetEnterpriseDao.findByCollectionId(collectionId);

        List<AccelerationIpsetEnterpriseVo> ipsetEnterpriseVoList = new LinkedList<>();

        if (accelerationIpsetEnterpriseEntityList != null) {
            for (AccelerationIpsetEnterpriseEntity accelerationIpsetEnterpriseEntity : accelerationIpsetEnterpriseEntityList) {
                //根据ipsetId获取域名和加速地址段
                List<AccelerationCidrEnterpriseEntity> cidrEnterpriseEntityList = accelerationCidrEnterpriseDao.findByIpsetId(accelerationIpsetEnterpriseEntity.getId());
                List<AccelerationDomainEnterpriseEntity> domainEnterpriseEntities = accelerationDomainEnterpriseDao.findByIpsetId(accelerationIpsetEnterpriseEntity.getId());

                AccelerationIpsetEnterpriseVo ipsetEnterpriseVo = ipsetEnterpriseAssembler.toVo(accelerationIpsetEnterpriseEntity);
                ipsetEnterpriseVo.setCidrList(cidrEnterpriseAssembler.toVos(cidrEnterpriseEntityList));
                ipsetEnterpriseVo.setDomainList(domainEnterpriseAssembler.toVos(domainEnterpriseEntities));
                ipsetEnterpriseVoList.add(ipsetEnterpriseVo);
            }
        }
        return ipsetEnterpriseVoList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public long createIpset(AccelerationIpsetEnterpriseBean ipsetBean) {
        AccelerationIpsetEnterpriseEntity ipsetEntity = accelerationIpsetEnterpriseDao.findByNameCollectionId(
                ipsetBean.getIpsetName(),
                ipsetBean.getCollectionId()
        );
        if (ipsetEntity != null) {
            throw BusinessException.error(CustomerErrorConstants.ACCELERATION_CIDR_REPEAT);
        } else {
            ipsetEntity = ipsetEnterpriseAssembler.fromBean(ipsetBean);
            ipsetEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            ipsetEntity.setEnable(true);
            accelerationIpsetEnterpriseDao.saveOrUpdate(ipsetEntity);

            ipsetEntity.setSequence(ipsetEntity.getId());
            accelerationIpsetEnterpriseDao.saveOrUpdate(ipsetEntity);
        }

        //同步数据到sextant
        updateIpsetOfSextant(ipsetEntity.getId());
        return ipsetEntity.getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyIpset(AccelerationIpsetEnterpriseBean ipsetBean) {
        AccelerationIpsetEnterpriseEntity ipsetEnterpriseEntity = accelerationIpsetEnterpriseDao.findOne(ipsetBean.getId());
        Validator.notNull(ipsetEnterpriseEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_IPSET_NOT_EXIST));

        AccelerationIpsetEnterpriseEntity enterpriseEntity = accelerationIpsetEnterpriseDao.findByNameCollectionId(ipsetBean.getIpsetName(), ipsetBean.getCollectionId());
        Validator.isTrue(enterpriseEntity == null || enterpriseEntity.getId() == ipsetEnterpriseEntity.getId(),
                BusinessException.error(CustomerErrorConstants.ACCELERATION_IPSET_REPEAT)
        );

        ipsetEnterpriseEntity.setIpsetName(ipsetBean.getIpsetName());
        ipsetEnterpriseEntity.setPortTotal(ipsetBean.getPortTotal());
        ipsetEnterpriseEntity.setAccelerateMode(ipsetBean.getAccelerateMode());
        accelerationIpsetEnterpriseDao.saveOrUpdate(ipsetEnterpriseEntity);
        //同步数据到sextant
        updateIpsetOfSextant(ipsetEnterpriseEntity.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void availableIpset(AccelerationIpsetEnterpriseBean ipsetBean) {
        AccelerationIpsetEnterpriseEntity ipsetEnterpriseEntity = accelerationIpsetEnterpriseDao.findOne(ipsetBean.getId());
        Validator.notNull(ipsetEnterpriseEntity, ResourceException.error(CustomerErrorConstants.ACCELERATION_IPSET_NOT_EXIST));

        ipsetEnterpriseEntity.setEnable(ipsetBean.getEnable());
        accelerationIpsetEnterpriseDao.saveOrUpdate(ipsetEnterpriseEntity);
        //同步数据到sextant
        updateIpsetOfSextant(ipsetEnterpriseEntity.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeIpset(long collectionId, long ipsetId) {
        AccelerationIpsetEnterpriseEntity ipsetEntity = accelerationIpsetEnterpriseDao.findOne(ipsetId);
        if (ipsetEntity != null) {
            accelerationIpsetEnterpriseDao.delete(ipsetEntity);
            accelerationCidrEnterpriseDao.deleteByIpsetId(ipsetId);
            accelerationDomainEnterpriseDao.deleteByIpsetId(ipsetId);
            //同步数据到sextant
            deleteIpsetOfSextant(collectionId, ipsetId);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createCidr(AccelerationCidrEnterpriseBean cidrBean) {
        if (cidrBean.getIpsetId() == null) {
            long ipsetId = 0;
            for (AccelerationIpsetEnterpriseEntity ipsetEnterpriseEntity : accelerationIpsetEnterpriseDao.findByCollectionId(cidrBean.getCollectionId())) {
                if (ipsetEnterpriseEntity.getTransportProtocol().equals(TransportProtocolEnum.ALL.getCode())) {
                    ipsetId = ipsetEnterpriseEntity.getId();
                } else {
                    Set<Integer> portSet = new HashSet<>();
                    for (String portStr : ipsetEnterpriseEntity.getPortTotal().split(",")) {
                        if (StringUtils.isPort(portStr)) {
                            portSet.add(Integer.valueOf(portStr));
                        } else {
                            String[] portTmp = portStr.split(":");
                            if (portTmp.length == 2 && StringUtils.isPort(portTmp[0]) && StringUtils.isPort(portTmp[1])) {
                                for (int startPort = Integer.valueOf(portTmp[0]); startPort <= Integer.valueOf(portTmp[1]); startPort++) {
                                    portSet.add(startPort);
                                }
                            }
                        }
                    }
                    if (ipsetEnterpriseEntity.getTransportProtocol().equals(cidrBean.getTransportProtocol()) && portSet.contains(cidrBean.getPortSingle())) {
                        ipsetId = ipsetEnterpriseEntity.getId();
                    }
                }
                if (ipsetId > 0) {
                    break;
                }
            }
            if (ipsetId == 0) {
                UserInterceptor.getLocalSecondLevelCache().set(CacheMode.REFRESH);
                AccelerationIpsetEnterpriseBean ipsetEnterpriseBean = new AccelerationIpsetEnterpriseBean();
                ipsetEnterpriseBean.setIpsetName(cidrBean.getTransportProtocol() + cidrBean.getPortSingle());
                ipsetEnterpriseBean.setTransportProtocol(cidrBean.getTransportProtocol());
                ipsetEnterpriseBean.setPortTotal(String.valueOf(cidrBean.getPortSingle()));
                ipsetEnterpriseBean.setCollectionId(cidrBean.getCollectionId());
                ipsetEnterpriseBean.setAccelerateMode(IpsetAccelerateMode.COMMON.getName());
                ipsetId = createIpset(ipsetEnterpriseBean);
            }

            AccelerationCidrEnterpriseEntity cidrEnterpriseEntity = accelerationCidrEnterpriseDao.findByCidrIpsetId(cidrBean.getCidr(), ipsetId);
            if (cidrEnterpriseEntity != null) {
//                cidrEnterpriseEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
//                accelerationCidrEnterpriseDao.saveOrUpdate(cidrEnterpriseEntity);
            } else {
                UserInterceptor.getLocalSecondLevelCache().set(CacheMode.REFRESH);
                cidrBean.setIpsetId(ipsetId);
                createCidr(cidrBean);
            }
        } else {
            if (accelerationCidrEnterpriseDao.findByCidrIpsetId(cidrBean.getCidr(), cidrBean.getIpsetId()) != null) {
                throw BusinessException.error(CustomerErrorConstants.ACCELERATION_CIDR_REPEAT);
            }
            AccelerationCidrEnterpriseEntity cidrEntity = cidrEnterpriseAssembler.fromBean(cidrBean);
            cidrEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

            accelerationCidrEnterpriseDao.saveOrUpdate(cidrEntity);
            //同步数据到sextant
            updateIpsetOfSextant(cidrEntity.getIpsetId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void syncCidr(long collectionId, long ipsetId, List<AccelerationCidrEnterpriseBean> cidrBeanList) {
        accelerationCidrEnterpriseDao.deleteByIpsetId(ipsetId);

        int total = cidrBeanList.size();
        int interval = 500;
        int i = 0;

        while (true) {
            List<AccelerationCidrEnterpriseBean> cidrEnterpriseBeanList = new LinkedList<>();

            if (total - i < interval) {
                cidrEnterpriseBeanList = cidrBeanList.subList(i, total);
                if (!cidrEnterpriseBeanList.isEmpty()) {
                    syncCidr(ipsetId, cidrEnterpriseBeanList);
                }
                break;
            } else {
                cidrEnterpriseBeanList = cidrBeanList.subList(i, i + interval);
                syncCidr(ipsetId, cidrEnterpriseBeanList);
                i += interval;
            }
        }

        updateIpsetOfSextant(ipsetId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void syncCidr(long ipsetId, List<AccelerationCidrEnterpriseBean> cidrBeanList) {
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        cidrBeanList.forEach(cidrBean -> {
            if (accelerationCidrEnterpriseDao.findByCidrIpsetId(cidrBean.getCidr(), ipsetId) != null) {
                return;
            }

            AccelerationCidrEnterpriseEntity cidrEntity = cidrEnterpriseAssembler.fromBean(cidrBean);
            cidrEntity.setCreateTime(createTime);

            accelerationCidrEnterpriseDao.saveOrUpdate(cidrEntity);
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void syncDomain(long collectionId, long ipsetId, List<AccelerationDomainEnterpriseBean> domainBeanList) {
        accelerationDomainEnterpriseDao.deleteByIpsetId(ipsetId);

        int total = domainBeanList.size();
        int interval = 500;
        int i = 0;

        while (true) {
            List<AccelerationDomainEnterpriseBean> domainEnterpriseBeanList = new LinkedList<>();

            if (total - i < interval) {
                domainEnterpriseBeanList = domainBeanList.subList(i, total);
                if (!domainEnterpriseBeanList.isEmpty()) {
                    syncDomain(ipsetId, domainEnterpriseBeanList);
                }
                break;
            } else {
                domainEnterpriseBeanList = domainBeanList.subList(i, i + interval);
                syncDomain(ipsetId, domainEnterpriseBeanList);
                i += interval;
            }
        }

        //同步数据到sextant
        updateIpsetOfSextant(ipsetId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void syncDomain(long ipsetId, List<AccelerationDomainEnterpriseBean> domainBeanList) {
        domainBeanList.forEach(domainBean -> {
            if (accelerationDomainEnterpriseDao.findByDomainIpsetId(domainBean.getDomain(), ipsetId) != null) {
                return;
            }
            AccelerationDomainEnterpriseEntity domainEntity = domainEnterpriseAssembler.fromBean(domainBean);
            domainEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
            accelerationDomainEnterpriseDao.saveOrUpdate(domainEntity);
        });
    }

    @Override
    public List<AccelerationCidrEnterpriseVo> listCidrByIpsetId(long ipsetId) {
        List<AccelerationCidrEnterpriseEntity> cidrEnterpriseEntityList = accelerationCidrEnterpriseDao.findByIpsetId(ipsetId);
        List<AccelerationCidrEnterpriseVo> cidrEnterpriseVoList = cidrEnterpriseAssembler.toVos(cidrEnterpriseEntityList);

        return cidrEnterpriseVoList;
    }

    @Override
    public List<AccelerationDomainEnterpriseVo> listDomainByIpsetId(long ipsetId) {
        List<AccelerationDomainEnterpriseEntity> domainEnterpriseEntityList = accelerationDomainEnterpriseDao.findByIpsetId(ipsetId);
        List<AccelerationDomainEnterpriseVo> domainEnterpriseVoList = domainEnterpriseAssembler.toVos(domainEnterpriseEntityList);

        return domainEnterpriseVoList;
    }

    @Override
    public List<AccelerationCidrEnterpriseVo> listCidrByNullCountry(int limit) {
        List<AccelerationCidrEnterpriseEntity> cidrEnterpriseEntityList = accelerationCidrEnterpriseDao.findByCountryIsNull(limit);
        List<AccelerationCidrEnterpriseVo> cidrEnterpriseVoList = cidrEnterpriseAssembler.toVos(cidrEnterpriseEntityList);

        return cidrEnterpriseVoList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyCidrByCountry(long cidrId) {

        AccelerationCidrEnterpriseEntity accelerationCidrEnterpriseEntity = accelerationCidrEnterpriseDao.findOne(cidrId);

        String cidr = accelerationCidrEnterpriseEntity.getCidr().split("/")[0];

        CommonRequest request = new CommonRequest("http://freeapi.ipip.net/", cidr, "", ContentType.APPLICATION_JSON);
        request.getHeaders().put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        RestReturn restReturn = RestClient.get(request, null, null);
        if (restReturn.getStatusCode() == 200) {

            String body = restReturn.getBody();
            List infos = gson.fromJson(body, List.class);
            accelerationCidrEnterpriseEntity.setCountry((String) infos.get(0) + "-" + (String) infos.get(1) + "-" + (String) infos.get(2));

            accelerationCidrEnterpriseDao.saveOrUpdate(accelerationCidrEnterpriseEntity);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeCidr(long collectionId, long ipsetId, long cidrId) {
        AccelerationCidrEnterpriseEntity cidrEntity = accelerationCidrEnterpriseDao.findOne(cidrId);
        if (cidrEntity != null) {
            if (cidrEntity.getCollectionId() != collectionId) {
                throw BusinessException.error(CustomerErrorConstants.ACCELERATION_CIDR_ILLEGAL);
            } else {
                accelerationCidrEnterpriseDao.delete(cidrEntity);
                //同步数据到sextant
//                deleteCidrOfSextant(collectionId, ipsetId, cidrId);
                updateIpsetOfSextant(ipsetId);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createDomain(AccelerationDomainEnterpriseBean domainBean) {
        if (accelerationDomainEnterpriseDao.findByDomainIpsetId(domainBean.getDomain(), domainBean.getIpsetId()) != null) {
            throw BusinessException.error(CustomerErrorConstants.ACCELERATION_DOMAIN_REPEAT);
        }
        AccelerationDomainEnterpriseEntity domainEntity = domainEnterpriseAssembler.fromBean(domainBean);
        domainEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));

        accelerationDomainEnterpriseDao.saveOrUpdate(domainEntity);
        //同步数据到sextant
//        updateDomainOfSextant(domainEntity);
        updateIpsetOfSextant(domainBean.getIpsetId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeDomain(long collectionId, long ipsetId, long domainId) {
        AccelerationDomainEnterpriseEntity domainEntity = accelerationDomainEnterpriseDao.findOne(domainId);
        if (domainEntity != null) {
            if (domainEntity.getCollectionId() != collectionId) {
                throw BusinessException.error(CustomerErrorConstants.ACCELERATION_DOMAIN_ILLEGAL);
            } else {
                accelerationDomainEnterpriseDao.delete(domainEntity);
                //同步数据到sextant
//                deleteDomainOfSextant(collectionId, ipsetId, domainId);
                updateIpsetOfSextant(ipsetId);
            }
        }
    }

    private void updateCollectionOfSextant(AccelerationCollectionEnterpriseEntity collectionEntity) {

        CollectionReq collectionReq = new CollectionReq();
        collectionReq.setId(collectionEntity.getId());
        collectionReq.setCollectionName(collectionEntity.getCollectionName());
        collectionReq.setCollectionDescription(collectionEntity.getCollectionDescription());
        collectionReq.setType(collectionEntity.getType());
        collectionReq.setAccelerateMode(collectionEntity.getAccelerateMode());

        BoxRes boxRes = collectionIF.updateCollection(collectionReq);

        if (!boxRes.isSuccess()) {
            logger.error(String.format("Sextant-企业加速集合-更新加速集合失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.UPDATE_COLLECTION_SYNCHRONIZATION);
        }
    }

    private void deleteCollectionOfSextant(long id) {

        BoxRes boxRes = collectionIF.deleteCollection(id);

        if (!boxRes.isSuccess()) {
            logger.error(String.format("Sextant-企业加速集合-删除加速集合失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.DELETE_COLLECTION_SYNCHRONIZATION);
        }
    }

    private void updateIpsetOfSextant(long ipsetId) {
        AccelerationIpsetEnterpriseEntity ipsetEntity = accelerationIpsetEnterpriseDao.findOne(ipsetId);
        List<AccelerationCidrEnterpriseEntity> cidrEntityList = accelerationCidrEnterpriseDao.findByIpsetId(ipsetId);
        List<AccelerationDomainEnterpriseEntity> domainEntityList = accelerationDomainEnterpriseDao.findByIpsetId(ipsetId);

        BoxRes boxRes = collectionIF.updateIpset(new IpsetReq(ipsetEntity, cidrEntityList, domainEntityList));

        if (!boxRes.isSuccess()) {
            logger.error(String.format("Sextant-企业加速集合-新增IPSET失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.UPDATE_IPSET_SYNCHRONIZATION);
        }
    }

    private void deleteIpsetOfSextant(long collectionId, long ipsetId) {

        BoxRes boxRes = collectionIF.deleteIpset(collectionId, ipsetId);

        if (!boxRes.isSuccess()) {
            logger.error(String.format("Sextant-企业加速集合-删除加速IPSET失败，错误码：%s，错误信息：%s",
                    boxRes.getErrorBody().getErrorCode(),
                    boxRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.DELETE_IPSET_SYNCHRONIZATION);
        }
    }

//    private void updateCidrOfSextant(AccelerationCidrEnterpriseEntity cidrEntity) {
//        BoxRes boxRes = collectionIF.updateCidr(new CidrReq(cidrEntity));
//
//        if (!boxRes.isSuccess()) {
//            logger.error(String.format("Sextant-企业加速集合-新增加速IP地址段失败，错误码：%s，错误信息：%s",
//                    boxRes.getErrorBody().getErrorCode(),
//                    boxRes.getErrorBody().getMessage()));
//            throw new BusinessException(CustomerErrorConstants.CREATE_CIDR_SYNCHRONIZATION);
//        }
//    }
//
//    private void syncCidrOfSextant(long collectionId, long ipsetId, List<AccelerationCidrEnterpriseEntity> cidrEntitySet) {
//        List<CidrReq> cidrReqList = new LinkedList<>();
//
//        for (AccelerationCidrEnterpriseEntity cidrEntity : cidrEntitySet) {
//            cidrReqList.add(new CidrReq(cidrEntity));
//        }
//
//        BoxRes boxRes = collectionIF.syncCidr(collectionId, ipsetId, cidrReqList);
//
//        if (!boxRes.isSuccess()) {
//            logger.error(String.format("Sextant-企业加速集合-同步加速IP地址段失败，错误码：%s，错误信息：%s",
//                    boxRes.getErrorBody().getErrorCode(),
//                    boxRes.getErrorBody().getMessage()));
//            throw new BusinessException(CustomerErrorConstants.SYNC_CIDR_SYNCHRONIZATION);
//        }
//    }
//
//    private void deleteCidrOfSextant(long collectionId, long ipsetId, long cidrId) {
//
//        BoxRes boxRes = collectionIF.deleteCidr(collectionId, ipsetId, cidrId);
//
//        if (!boxRes.isSuccess()) {
//            logger.error(String.format("Sextant-企业加速集合-删除加速IP地址段失败，错误码：%s，错误信息：%s",
//                    boxRes.getErrorBody().getErrorCode(),
//                    boxRes.getErrorBody().getMessage()));
//            throw new BusinessException(CustomerErrorConstants.DELETE_CIDR_SYNCHRONIZATION);
//        }
//    }
//
//    private void syncDomainOfSextant(long collectionId, long ipsetId, List<AccelerationDomainEnterpriseEntity> domainEntityList) {
//        List<DomainReq> domainReqList = new LinkedList<>();
//
//        for (AccelerationDomainEnterpriseEntity domainEntity : domainEntityList) {
//            domainReqList.add(new DomainReq(domainEntity));
//        }
//
//        BoxRes boxRes = collectionIF.syncDomain(collectionId, ipsetId, domainReqList);
//
//        if (!boxRes.isSuccess()) {
//            logger.error(String.format("Sextant-企业加速集合-同步域名失败，错误码：%s，错误信息：%s",
//                    boxRes.getErrorBody().getErrorCode(),
//                    boxRes.getErrorBody().getMessage()));
//            throw new BusinessException(CustomerErrorConstants.SYNC_DOMAIN_SYNCHRONIZATION);
//        }
//
//    }
//
//    private void updateDomainOfSextant(AccelerationDomainEnterpriseEntity domainEntity) {
//        BoxRes boxRes = collectionIF.updateDomain(new DomainReq(domainEntity));
//
//        if (!boxRes.isSuccess()) {
//            logger.error(String.format("Sextant-企业加速集合-新增域名失败，错误码：%s，错误信息：%s",
//                    boxRes.getErrorBody().getErrorCode(),
//                    boxRes.getErrorBody().getMessage()));
//            throw new BusinessException(CustomerErrorConstants.CREATE_DOMAIN_SYNCHRONIZATION);
//        }
//    }
//
//    private void deleteDomainOfSextant(long collectionId, long ipsetId, long domainId) {
//
//        BoxRes boxRes = collectionIF.deleteDomain(collectionId, ipsetId, domainId);
//
//        if (!boxRes.isSuccess()) {
//            logger.error(String.format("Sextant-企业加速集合-删除加速集合失败，错误码：%s，错误信息：%s",
//                    boxRes.getErrorBody().getErrorCode(),
//                    boxRes.getErrorBody().getMessage()));
//            throw new BusinessException(CustomerErrorConstants.DELETE_DOMAIN_SYNCHRONIZATION);
//        }
//    }
}
