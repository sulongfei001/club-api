package com.sevenXnetworks.treasure.dao.impl;

import com.sevenXnetworks.treasure.dao.ActivitySnapshotDao;
import com.sevenXnetworks.treasure.entity.ActivitySnapshotEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/11/1 11:32
 * @Version 1.0
 */
@Repository
public class ActivitySnapshotDaoImpl extends BaseDaoImpl<ActivitySnapshotEntity, Long> implements ActivitySnapshotDao {

    @Override
    public List<ActivitySnapshotEntity> findByActivityIdAndType(long id, Byte type) {
        String hql = "FROM ActivitySnapshotEntity WHERE activityId = :id AND type = :type ";
        Query query = currentSession().createQuery(hql);
        query.setLong("id", id);
        query.setByte("type", type);
        return query.list();
    }
}
