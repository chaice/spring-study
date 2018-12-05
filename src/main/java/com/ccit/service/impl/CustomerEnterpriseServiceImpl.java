package com.ccit.service.impl;

import com.ccit.dao.*;
import com.ccit.entity.AdnCustomerZoneEntity;
import com.ccit.entity.AdnServiceZoneEntity;
import com.ccit.entity.CustomerEnterpriseEntity;
import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.CustomerEnterpriseBean;
import com.ccit.dao.*;
import com.ccit.entity.*;
import com.ccit.enums.AdnServiceTypeEnum;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.axis.interfaces.AxisUserIF;
import com.ccit.rest.axis.request.AxisUserReq;
import com.ccit.rest.axis.response.AxisUserRes;
import com.ccit.rest.rainbow.interfaces.RainbowUserIF;
import com.ccit.rest.rainbow.request.RainbowUserReq;
import com.ccit.rest.rainbow.response.RainbowUserRes;
import com.ccit.rest.sextant.interfaces.UserIF;
import com.ccit.rest.sextant.request.UserReq;
import com.ccit.rest.sextant.response.UserRes;
import com.ccit.service.AdnCustomerDomainService;
import com.ccit.service.AdnZoneService;
import com.ccit.service.CustomerEnterpriseService;
import com.ccit.service.ResourceService;
import com.ccit.util.MD5Utils;
import com.ccit.util.StringUtils;
import com.ccit.vo.AdnCustomerDomainVo;
import com.ccit.vo.CustomerEnterpriseVo;
import com.ccit.web.interceptor.UserInterceptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CustomerEnterpriseServiceImpl implements CustomerEnterpriseService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private CustomerEnterpriseDao customerEnterpriseDao;

    @Autowired
    private Assembler<CustomerEnterpriseBean, CustomerEnterpriseEntity, CustomerEnterpriseVo> customerEnterpriseAssembler;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    @Qualifier("sextantUserIF")
    private UserIF sextantUserIF;

    @Autowired
    @Qualifier("axisUserIF")
    private AxisUserIF axisUserIF;

    @Autowired
    @Qualifier("rainbowUserIF")
    private RainbowUserIF rainbowUserIF;

    @Autowired
    private AdnZoneService adnZoneService;

    @Autowired
    private AdnCustomerDomainService adnCustomerDomainService;

    @Autowired
    private AdnL4ServiceDao adnL4ServiceDao;

    @Autowired
    private AdnHttpServiceDao adnHttpServiceDao;

    @Autowired
    private AdnServiceZoneDao adnServiceZoneDao;

    @Autowired
    private AdnServiceEntryDao adnServiceEntryDao;

    @Autowired
    private AdnCustomerZoneDao adnCustomerZoneDao;

    public List<CustomerEnterpriseVo> searchCustomerEnterprise(String name, String portalName, String sort) {
        List<CustomerEnterpriseEntity> customerEnterpriseEntities = customerEnterpriseDao.searchCustomerEnterprise(name, portalName, sort);
        List<CustomerEnterpriseVo> customerEnterpriseVos = new ArrayList<>();

        customerEnterpriseEntities.forEach(customerEnterpriseEntity -> {

            CustomerEnterpriseVo vo = customerEnterpriseAssembler.toVo(customerEnterpriseEntity);
            List<AdnCustomerZoneEntity> customerZoneEntities = adnZoneService.getCustomerZone(customerEnterpriseEntity.getId());
            customerZoneEntities.forEach(customerZone -> {
                vo.getZoneList().add(adnZoneService.getZone(customerZone.getZoneId()));
            });

            List<AdnCustomerDomainVo> domainVos = adnCustomerDomainService.listCustomerDomain(customerEnterpriseEntity.getId());
            vo.getDomainList().addAll(domainVos);

            customerEnterpriseVos.add(vo);
        });

        return customerEnterpriseVos;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveCustomerEnterprise(CustomerEnterpriseBean customerEnterpriseBean) {
        assertSextantNameValid(customerEnterpriseBean.getSextantName());
        assertNameValid(null, customerEnterpriseBean.getName());

        CustomerEnterpriseEntity customerEnterpriseEntity = customerEnterpriseAssembler.fromBean(customerEnterpriseBean);
        customerEnterpriseEntity.setSextantPassword(MD5Utils.encrypt(String.format("%s{%s}", customerEnterpriseBean.getSextantPassword(), customerEnterpriseEntity.getSextantName())));
        customerEnterpriseEntity.setCreateUid(UserInterceptor.getLocalUser().getId());
        customerEnterpriseEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        customerEnterpriseEntity.setEnable(Boolean.TRUE);
        customerEnterpriseEntity.setRemoved(Boolean.FALSE);
        customerEnterpriseDao.saveOrUpdate(customerEnterpriseEntity);

        customerEnterpriseBean.setId(customerEnterpriseEntity.getId());
        updateUserOfSextant(customerEnterpriseBean);
        //同步企业客户到axis
        updateUserOfAxis(customerEnterpriseBean);

        updateUserOfRainbow(customerEnterpriseBean);
    }

    public CustomerEnterpriseVo getById(long id) {
        CustomerEnterpriseEntity customerEnterpriseEntity = customerEnterpriseDao.findOne(id);
        Validator.notNull(customerEnterpriseEntity, ResourceException.error(CustomerErrorConstants.CUSTOMER_ENTERPRISE_NOT_EXIST));

        return customerEnterpriseAssembler.toVo(customerEnterpriseEntity);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateCustomerEnterprise(CustomerEnterpriseBean customerEnterpriseBean) {
        CustomerEnterpriseEntity customerEnterpriseEntity = customerEnterpriseDao.findOne(customerEnterpriseBean.getId());
        Validator.notNull(customerEnterpriseEntity, BusinessException.error(CustomerErrorConstants.CUSTOMER_ENTERPRISE_NOT_EXIST));

        // 客户名称检查
        assertNameValid(customerEnterpriseEntity.getName(), customerEnterpriseBean.getName());

        customerEnterpriseEntity.setName(customerEnterpriseBean.getName());
        customerEnterpriseEntity.setInfo(customerEnterpriseBean.getInfo());
        customerEnterpriseEntity.setProvince(customerEnterpriseBean.getProvince());
        customerEnterpriseEntity.setCity(customerEnterpriseBean.getCity());
        customerEnterpriseEntity.setContact(customerEnterpriseBean.getContact());
        customerEnterpriseEntity.setContactPhone(customerEnterpriseBean.getContactPhone());
        if (StringUtils.isNotBlank(customerEnterpriseBean.getSextantPassword())) {
            customerEnterpriseEntity.setSextantPassword(MD5Utils.encrypt(String.format("%s{%s}", customerEnterpriseBean.getSextantPassword(), customerEnterpriseEntity.getSextantName())));
        }
        customerEnterpriseEntity.setLastUid(customerEnterpriseBean.getLastUid());
        customerEnterpriseEntity.setLastTime(new Timestamp(System.currentTimeMillis()));

        customerEnterpriseDao.saveOrUpdate(customerEnterpriseEntity);

        updateUserOfSextant(customerEnterpriseBean);
        //同步企业客户到axis
        updateUserOfAxis(customerEnterpriseBean);

        updateUserOfRainbow(customerEnterpriseBean);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeCustomerEnterprise(long id) {
        CustomerEnterpriseEntity customerEnterpriseEntity = customerEnterpriseDao.findOne(id);
        if (null != customerEnterpriseEntity) {
            // 检查客户是否可删除
            resourceService.check(customerEnterpriseEntity);

            String delTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            customerEnterpriseEntity.setSextantName(customerEnterpriseEntity.getSextantName() + "_" + delTime);
            customerEnterpriseEntity.setRemoved(true);

            //删除服务
            adnHttpServiceDao.deleteByCustomerId(id);
            adnL4ServiceDao.deleteByCustomerId(id);
            //删除用户区域关联关系
            adnCustomerZoneDao.deleteByCustomerId(id);

            //删除服务入口关联关系
            List<AdnServiceZoneEntity> adnServiceZoneEntityList = adnServiceZoneDao.findByCustomerId(id);
            adnServiceZoneEntityList.forEach(serviceZoneEntity -> {
                adnServiceEntryDao.deleteByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.FOUR_LAYER.getType(), serviceZoneEntity.getServiceId(), serviceZoneEntity.getZoneId());
                adnServiceEntryDao.deleteByServiceTypeServiceIdZoneId(AdnServiceTypeEnum.HTTP.getType(), serviceZoneEntity.getServiceId(), serviceZoneEntity.getZoneId());
            });
            //删除服务区域关联记录
            adnServiceZoneDao.deleteByCustomerId(id);

            removeUserOfSextant(id);
            //同步企业客户到axis
            removeUserOfAxis(id);

            removeUserOfRainbow(id);
        }
    }


    private void updateUserOfSextant(CustomerEnterpriseBean customerEnterpriseBean) {
        UserReq userReq = new UserReq();
        userReq.setUserName(customerEnterpriseBean.getSextantName());
        userReq.setTargetPassword(customerEnterpriseBean.getSextantPassword());

        UserRes userRes = sextantUserIF.updateUser(customerEnterpriseBean.getId(), userReq);
        if (!userRes.isSuccess()) {
            logger.error(String.format("Sextant-更新用户-同步用户失败，错误码：%s，错误信息：%s",
                    userRes.getErrorBody().getErrorCode(),
                    userRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.USER_SYNCHRONIZATION_FAIL);
        }
    }


    private void removeUserOfSextant(long userId) {
        UserRes userRes = sextantUserIF.deleteUser(userId);
        if (!userRes.isSuccess()) {
            logger.error(String.format("Sextant-删除用户-同步用户失败，错误码：%s，错误信息：%s",
                    userRes.getErrorBody().getErrorCode(),
                    userRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.USER_SYNCHRONIZATION_FAIL);
        }
    }

    private void updateUserOfAxis(CustomerEnterpriseBean customerEnterpriseBean) {
        AxisUserReq userReq = new AxisUserReq();
        userReq.setUserName(customerEnterpriseBean.getSextantName());
        userReq.setTargetPassword(customerEnterpriseBean.getSextantPassword());

        AxisUserRes userRes = axisUserIF.updateUser(customerEnterpriseBean.getId(), userReq);
        if (!userRes.isSuccess()) {
            logger.error(String.format("Axis-更新用户-同步用户失败，错误码：%s，错误信息：%s",
                    userRes.getErrorBody().getErrorCode(),
                    userRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.USER_SYNCHRONIZATION_FAIL);
        }
    }


    private void removeUserOfAxis(long userId) {
        AxisUserRes userRes = axisUserIF.deleteUser(userId);
        if (!userRes.isSuccess()) {
            logger.error(String.format("Axis-删除用户-同步用户失败，错误码：%s，错误信息：%s",
                    userRes.getErrorBody().getErrorCode(),
                    userRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.USER_SYNCHRONIZATION_FAIL);
        }
    }

    // Sextant关联用户名重复抛异常
    private void assertSextantNameValid(String portalName) throws BusinessException {
        CustomerEnterpriseEntity entity = customerEnterpriseDao.getBySextantName(portalName);
        if (null != entity) {
            throw new BusinessException(CustomerErrorConstants.CUSTOMER_ENTERPRISE_SEXTANT_NAME_USED);
        }
    }

    private void updateUserOfRainbow(CustomerEnterpriseBean customerEnterpriseBean) {
        RainbowUserReq userReq = new RainbowUserReq();
        userReq.setUserName(customerEnterpriseBean.getSextantName());
        userReq.setTargetPassword(customerEnterpriseBean.getSextantPassword());
        //userReq.setName(customerEnterpriseBean.getName());
        //userReq.setContact(customerEnterpriseBean.getContact());
        //userReq.setContactPhone(customerEnterpriseBean.getContactPhone());

        RainbowUserRes userRes = rainbowUserIF.updateUser(customerEnterpriseBean.getId(), userReq);
        if (!userRes.isSuccess()) {
            logger.error(String.format("Rainbow-更新用户-同步用户失败，错误码：%s，错误信息：%s",
                    userRes.getErrorBody().getErrorCode(),
                    userRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.USER_SYNCHRONIZATION_FAIL);
        }
    }

    private void removeUserOfRainbow(long userId) {
        RainbowUserRes userRes = rainbowUserIF.deleteUser(userId);
        if (!userRes.isSuccess()) {
            logger.error(String.format("Rainbow-删除用户-同步用户失败，错误码：%s，错误信息：%s",
                    userRes.getErrorBody().getErrorCode(),
                    userRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.USER_SYNCHRONIZATION_FAIL);
        }
    }

    // 客户名称重复抛异常
    private void assertNameValid(String oldName, String newName) throws BusinessException {
        if (StringUtils.equals(oldName, newName)) {
            return;
        }
        CustomerEnterpriseEntity entity = customerEnterpriseDao.getByName(newName);
        if (null != entity) {
            throw new BusinessException(CustomerErrorConstants.CUSTOMER_ENTERPRISE_NAME_USED);
        }
    }

}