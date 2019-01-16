package com.sevenXnetworks.treasure.dao;

import com.sevenXnetworks.treasure.entity.ActivityEntity;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:41
 * @Version 1.0
 */
public interface ActivityDao extends BaseDao<ActivityEntity, Long> {

    long findByBarId(long id);

    List<ActivityEntity> findByPage(long beginId, int offset);

    List<ActivityEntity> findByPage(long barId, long beginId, int offset);

}
