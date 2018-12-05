package com.ccit.dao.impl;

import com.ccit.entity.AgencyEnterpriseEntity;
import com.ccit.dao.AgencyEnterpriseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AgencyEnterpriseDaoImpl extends BaseDaoImpl<AgencyEnterpriseEntity, Long> implements AgencyEnterpriseDao {

    public AgencyEnterpriseEntity getByAccount(String account) {
        String hql = "FROM AgencyEnterpriseEntity WHERE account=:account";
        Query query = currentSession().createQuery(hql).setString("account", account);
        return (AgencyEnterpriseEntity) query.uniqueResult();
    }

}
