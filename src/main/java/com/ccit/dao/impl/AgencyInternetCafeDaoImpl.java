package com.ccit.dao.impl;

import com.ccit.entity.AgencyInternetCafeEntity;
import com.ccit.dao.AgencyInternetCafeDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AgencyInternetCafeDaoImpl extends BaseDaoImpl<AgencyInternetCafeEntity, Long> implements AgencyInternetCafeDao {

    public AgencyInternetCafeEntity getByAccount(String account) {
        String hql = "FROM AgencyInternetCafeEntity WHERE account=:account";
        Query query = currentSession().createQuery(hql).setString("account", account);
        return (AgencyInternetCafeEntity) query.uniqueResult();
    }

}
