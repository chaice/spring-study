package com.ccit.dao.impl;

import com.ccit.entity.SecurityUserEntity;
import com.ccit.dao.SecurityUserDao;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityUserDaoImpl extends BaseDaoImpl<SecurityUserEntity, Long> implements SecurityUserDao {

    public SecurityUserEntity findByName(String name) {
        String hql = "FROM SecurityUserEntity WHERE enable=true AND userName=:userName";
        Query query = currentSession().createQuery(hql)
                .setString("userName", name);
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        query.setCacheRegion("SecurityUserDao.findByName");

        Object entityObj =  query.uniqueResult();
        return entityObj != null ? (SecurityUserEntity) entityObj : null;
    }

    @Override
    public SecurityUserEntity findOne(Long id) {
        String hql = "FROM SecurityUserEntity WHERE enable=true AND id=:id";
        Query query = currentSession().createQuery(hql).setLong("id", id);
        return (SecurityUserEntity) query.uniqueResult();
    }

}