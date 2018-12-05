package com.ccit.service.impl;

import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.CustomerInternetCafeBean;
import com.ccit.dao.CustomerInternetCafeDao;
import com.ccit.entity.CustomerInternetCafeEntity;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.zion.interfaces.UserIF;
import com.ccit.rest.zion.request.UserReq;
import com.ccit.rest.zion.response.UserRes;
import com.ccit.service.CustomerInternetCafeService;
import com.ccit.service.ResourceService;
import com.ccit.util.MD5Utils;
import com.ccit.util.StringUtils;
import com.ccit.vo.CustomerInternetCafeVo;
import com.ccit.web.interceptor.UserInterceptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CustomerInternetCafeServiceImpl implements CustomerInternetCafeService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private CustomerInternetCafeDao customerInternetCafeDao;

    @Autowired
    private Assembler<CustomerInternetCafeBean, CustomerInternetCafeEntity, CustomerInternetCafeVo> customerInternetCafeAssembler;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    @Qualifier("zionUserIF")
    private UserIF zionUserIF;

    public List<CustomerInternetCafeVo> searchCustomerInternetCafe(String name, String portalName, String sort) {
        List<CustomerInternetCafeEntity> customerInternetCafeEntities = customerInternetCafeDao.searchCustomerInternetCafe(name, portalName, sort);
        List<CustomerInternetCafeVo> customerInternetCafeVos = customerInternetCafeAssembler.toVos(customerInternetCafeEntities);
        return customerInternetCafeVos;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveCustomerInternetCafe(CustomerInternetCafeBean customerInternetCafeBean) {
        assertZionNameValid(customerInternetCafeBean.getZionName());
        assertNameValid(null, customerInternetCafeBean.getName());

        CustomerInternetCafeEntity customerInternetCafeEntity = customerInternetCafeAssembler.fromBean(customerInternetCafeBean);
        customerInternetCafeEntity.setZionPassword(MD5Utils.encrypt(String.format("%s{%s}", customerInternetCafeBean.getZionPassword(), customerInternetCafeEntity.getZionName())));
        customerInternetCafeEntity.setCreateUid(UserInterceptor.getLocalUser().getId());
        customerInternetCafeEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        customerInternetCafeEntity.setEnable(Boolean.TRUE);
        customerInternetCafeEntity.setRemoved(Boolean.FALSE);
        customerInternetCafeDao.saveOrUpdate(customerInternetCafeEntity);

        customerInternetCafeBean.setId(customerInternetCafeEntity.getId());
        updateUserOfZion(customerInternetCafeBean);
    }


    public CustomerInternetCafeVo getById(long id) {
        CustomerInternetCafeEntity customerInternetCafeEntity = customerInternetCafeDao.findOne(id);
        Validator.notNull(customerInternetCafeEntity, ResourceException.error(CustomerErrorConstants.CUSTOMER_INTERNET_CAFE_NOT_EXIST));

        return customerInternetCafeAssembler.toVo(customerInternetCafeEntity);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateCustomerInternetCafe(CustomerInternetCafeBean customerInternetCafeBean) {
        CustomerInternetCafeEntity customerInternetCafeEntity = customerInternetCafeDao.findOne(customerInternetCafeBean.getId());
        Validator.notNull(customerInternetCafeEntity, BusinessException.error(CustomerErrorConstants.CUSTOMER_INTERNET_CAFE_NOT_EXIST));

        // 客户名称检查
        assertNameValid(customerInternetCafeEntity.getName(), customerInternetCafeBean.getName());

        customerInternetCafeEntity.setName(customerInternetCafeBean.getName());
        customerInternetCafeEntity.setInfo(customerInternetCafeBean.getInfo());
        customerInternetCafeEntity.setProvince(customerInternetCafeBean.getProvince());
        customerInternetCafeEntity.setCity(customerInternetCafeBean.getCity());
        customerInternetCafeEntity.setContact(customerInternetCafeBean.getContact());
        customerInternetCafeEntity.setContactPhone(customerInternetCafeBean.getContactPhone());
        if (StringUtils.isNotBlank(customerInternetCafeBean.getZionPassword())) {
            customerInternetCafeEntity.setZionPassword(MD5Utils.encrypt(String.format("%s{%s}", customerInternetCafeBean.getZionPassword(), customerInternetCafeEntity.getZionName())));
        }
        customerInternetCafeEntity.setLastUid(customerInternetCafeBean.getLastUid());
        customerInternetCafeEntity.setLastTime(new Timestamp(System.currentTimeMillis()));

        customerInternetCafeDao.saveOrUpdate(customerInternetCafeEntity);

        updateUserOfZion(customerInternetCafeBean);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeCustomerInternetCafe(long id) {
        CustomerInternetCafeEntity customerInternetCafeEntity = customerInternetCafeDao.findOne(id);
        if (null != customerInternetCafeEntity) {
            // 检查客户是否可删除
            resourceService.check(customerInternetCafeEntity);

            String delTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            customerInternetCafeEntity.setZionName(customerInternetCafeEntity.getZionName() + "_" + delTime);
            customerInternetCafeEntity.setRemoved(true);

            removeUserOfZion(id);
        }
    }


    private void updateUserOfZion(CustomerInternetCafeBean customerInternetCafeBean) {
        UserReq userReq = new UserReq();
        userReq.setUserName(customerInternetCafeBean.getZionName());
        userReq.setTargetPassword(customerInternetCafeBean.getZionPassword());

        UserRes userRes = zionUserIF.updateUser(customerInternetCafeBean.getId(), userReq);
        if (!userRes.isSuccess()) {
            logger.error(String.format("Z-更新用户-同步用户失败，错误码：%s，错误信息：%s",
                    userRes.getErrorBody().getErrorCode(),
                    userRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.USER_SYNCHRONIZATION_FAIL);
        }
    }


    private void removeUserOfZion(long userId) {
        UserRes userRes = zionUserIF.deleteUser(userId);
        if (!userRes.isSuccess()) {
            logger.error(String.format("Zion-删除用户-同步用户失败，错误码：%s，错误信息：%s",
                    userRes.getErrorBody().getErrorCode(),
                    userRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.USER_SYNCHRONIZATION_FAIL);
        }
    }

    // Zion关联用户名重复抛异常
    private void assertZionNameValid(String portalName) throws BusinessException {
        CustomerInternetCafeEntity entity = customerInternetCafeDao.getByZionName(portalName);
        if (null != entity) {
            throw new BusinessException(CustomerErrorConstants.CUSTOMER_INTERNET_CAFE_ZION_NAME_USED);
        }
    }


    // 客户名称重复抛异常
    private void assertNameValid(String oldName, String newName) throws BusinessException {
        if (StringUtils.equals(oldName, newName)) {
            return;
        }
        CustomerInternetCafeEntity entity = customerInternetCafeDao.getByName(newName);
        if (null != entity) {
            throw new BusinessException(CustomerErrorConstants.CUSTOMER_INTERNET_CAFE_NAME_USED);
        }
    }

}