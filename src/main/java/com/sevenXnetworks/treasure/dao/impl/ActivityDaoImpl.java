package com.sevenXnetworks.treasure.dao.impl;

import com.sevenXnetworks.treasure.dao.ActivityDao;
import com.sevenXnetworks.treasure.entity.ActivityEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:41
 * @Version 1.0
 */
@Repository
public class ActivityDaoImpl extends BaseDaoImpl<ActivityEntity, Long> implements ActivityDao {
    @Override
    public List<ActivityEntity> findAll() {
        String hql = "FROM ActivityEntity ORDER BY orderNum ";
        Query query = currentSession().createQuery(hql);
        return query.list();
    }

    @Override
    public long findByBarId(long id) {
        String hql = "SELECT COUNT(0) FROM ActivityEntity WHERE barId = :id ";
        Query query = currentSession().createQuery(hql);
        query.setLong("id", id);
        return (long) query.uniqueResult();
    }

    @Override
    public List<ActivityEntity> findByPage(long beginId, int offset) {
        String hql = "FROM ActivityEntity WHERE onSale = 1 AND startTime < now() AND endTime > now() ORDER BY orderNum ";
        Query query = currentSession().createQuery(hql);
        query.setFirstResult(Math.toIntExact(beginId));
        query.setMaxResults(offset);
        return query.list();
    }

    @Override
    public List<ActivityEntity> findByPage(long barId, long beginId, int offset) {
        String hql = "FROM ActivityEntity WHERE onSale = 1 AND barId = :barId AND startTime < now() AND endTime > now() ORDER BY orderNum ";
        Query query = currentSession().createQuery(hql);
        query.setLong("barId", barId);
        query.setFirstResult(Math.toIntExact(beginId));
        query.setMaxResults(offset);
        return query.list();
    }

}
