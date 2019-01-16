package com.sevenXnetworks.treasure.service;

import com.sevenXnetworks.treasure.entity.ActivityEntity;
import com.sevenXnetworks.treasure.vo.ActivityVo;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:39
 * @Version 1.0
 */
public interface ActivityService {

    List<ActivityEntity> list();

    List<ActivityVo> appList(long barId, long beginId, int offset);

    ActivityVo appGet(long barId, long activityId, long beginId, int offset);

}
