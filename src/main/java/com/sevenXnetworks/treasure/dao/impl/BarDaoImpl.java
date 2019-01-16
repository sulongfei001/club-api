package com.sevenXnetworks.treasure.dao.impl;

import com.sevenXnetworks.treasure.dao.BarDao;
import com.sevenXnetworks.treasure.entity.BarEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:22
 * @Version 1.0
 */
@Repository
public class BarDaoImpl extends BaseDaoImpl<BarEntity, Long> implements BarDao {
    @Override
    public List<BarEntity> findPass(long beginId, int offset) {
        String hql = "FROM BarEntity WHERE state = 1 ORDER BY orderNum ";
        Query query = currentSession().createQuery(hql);
        query.setFirstResult(Math.toIntExact(beginId));
        query.setMaxResults(offset);
        return query.list();
    }
}
