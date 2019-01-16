package com.sevenXnetworks.treasure.dao;

import com.sevenXnetworks.treasure.entity.ActivitySnapshotEntity;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/11/1 11:31
 * @Version 1.0
 */
public interface ActivitySnapshotDao extends BaseDao<ActivitySnapshotEntity, Long> {
    List<ActivitySnapshotEntity> findByActivityIdAndType(long id, Byte type);
}
