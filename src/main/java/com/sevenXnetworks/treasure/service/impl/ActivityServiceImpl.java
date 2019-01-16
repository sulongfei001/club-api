package com.sevenXnetworks.treasure.service.impl;

import com.sevenXnetworks.treasure.config.ConfigProperties;
import com.sevenXnetworks.treasure.dao.ActivityDao;
import com.sevenXnetworks.treasure.dao.ActivitySnapshotDao;
import com.sevenXnetworks.treasure.dao.BarDao;
import com.sevenXnetworks.treasure.dao.OrderDao;
import com.sevenXnetworks.treasure.entity.ActivityEntity;
import com.sevenXnetworks.treasure.entity.ActivitySnapshotEntity;
import com.sevenXnetworks.treasure.entity.BarEntity;
import com.sevenXnetworks.treasure.entity.OrderEntity;
import com.sevenXnetworks.treasure.exception.ResourceException;
import com.sevenXnetworks.treasure.model.Const;
import com.sevenXnetworks.treasure.model.CustomerErrorConstants;
import com.sevenXnetworks.treasure.model.Validator;
import com.sevenXnetworks.treasure.service.ActivityService;
import com.sevenXnetworks.treasure.utils.StringUtils;
import com.sevenXnetworks.treasure.vo.ActivitySnapshotVo;
import com.sevenXnetworks.treasure.vo.ActivityVo;
import com.sevenXnetworks.treasure.vo.HistoryOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:39
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private BarDao barDao;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ActivitySnapshotDao snapshotDao;
    @Autowired
    private ConfigProperties properties;

    @Override
    public List<ActivityEntity> list() {
        List<ActivityEntity> activityEntities = activityDao.findAll();
        return activityEntities;
    }

    @Override
    public List<ActivityVo> appList(long barId, long beginId, int offset) {
        List<ActivityEntity> activityEntities;
        if (barId == -1) {
            activityEntities = activityDao.findByPage(beginId, offset);
        } else {
            activityEntities = activityDao.findByPage(barId, beginId, offset);
        }
        List<ActivityVo> activityVos = new ArrayList<>();
        List<ActivityVo> activityEndVos = new ArrayList<>();
        activityEntities.forEach(entity -> {
            BarEntity barEntity = null;
            if (StringUtils.isNotBlank(entity.getBarId())) {
                barEntity = barDao.findOne(entity.getBarId());
            }
            long saleCount = orderDao.countByActivityId(entity.getId());
            List<ActivitySnapshotEntity> snapshotEntities = snapshotDao.findByActivityIdAndType(entity.getId(), Const.SNAPSHOT_TYPE_0);
            List<ActivitySnapshotVo> snapshotVos = toVo(snapshotEntities);
            ActivityVo vo = toVoApp(entity);
            vo.setIsPromotion((byte) (barEntity == null ? 0 : 1));
            vo.setSnapshotVos(snapshotVos);
            vo.setBarId(barId);
            vo.setSaleCount(saleCount);
            if (vo.getSaleCount() >= vo.getCount()) {
                activityEndVos.add(vo);
            } else {
                activityVos.add(vo);
            }
        });
        activityVos.addAll(activityEndVos);
        return activityVos;
    }

    @Override
    public ActivityVo appGet(long barId, long activityId, long beginId, int offset) {
        ActivityEntity activityEntity = activityDao.findOne(activityId);
        Validator.notNull(activityEntity, ResourceException.error(CustomerErrorConstants.ACTIVITY_NOT_EXIST));
        List<ActivitySnapshotEntity> snapshotEntities = snapshotDao.findByActivityIdAndType(activityId, Const.SNAPSHOT_TYPE_1);
        List<OrderEntity> orderEntities = orderDao.findByActivityId(activityId);
        long saleCount = orderDao.countByActivityId(activityId);
        BarEntity barEntity = null;
        if (StringUtils.isNotBlank(activityEntity.getBarId())) {
            barEntity = barDao.findOne(activityEntity.getBarId());
        }
        List<ActivitySnapshotVo> snapshotVos = toVo(snapshotEntities);
        List<HistoryOrderVo> orderVos = toOrderVo(orderEntities, activityEntity.getName(), activityEntity.getGoodsName());
        ActivityVo vo = toVoApp(activityEntity);
        vo.setGoodsId(activityEntity.getGoodsId());
        vo.setGoodsName(activityEntity.getGoodsName());
        vo.setGoodsType(activityEntity.getGoodsType());
        vo.setGoodsPrdValueMon(activityEntity.getGoodsPrdValueMon());
        vo.setIsPromotion((byte) (barEntity == null ? 0 : 1));
        vo.setSnapshotVos(snapshotVos);
        vo.setBarId(barId);
        vo.setSaleCount(saleCount);
        vo.setOrderVos(orderVos);
        return vo;
    }


    private List<HistoryOrderVo> toOrderVo(List<OrderEntity> orderEntities, String name, String goodsName) {
        List<HistoryOrderVo> orderVos = new ArrayList<>();
        orderEntities.forEach(entity -> {
            HistoryOrderVo vo = new HistoryOrderVo();
            vo.setUserName(entity.getUserName());
            vo.setTradeDate(entity.getTradeDate());
            vo.setTradeCount(entity.getTradeCount());
            vo.setActivityName(name);
            vo.setGoodsName(goodsName);
            orderVos.add(vo);
        });
        return orderVos;
    }

    private ActivityVo toVoApp(ActivityEntity entity) {
        ActivityVo vo = new ActivityVo();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setContent(entity.getContent());
        vo.setCount(entity.getCount());
        vo.setStartTime(entity.getStartTime());
        vo.setEndTime(entity.getEndTime());
        return vo;
    }


    private List<ActivitySnapshotVo> toVo(List<ActivitySnapshotEntity> entities) {
        List<ActivitySnapshotVo> snapshotVos = new ArrayList<>();
        entities.forEach(entity -> {
            ActivitySnapshotVo vo = new ActivitySnapshotVo();
            vo.setUrl(properties.getUploadAddr() + entity.getUrl());
            snapshotVos.add(vo);
        });
        return snapshotVos;
    }

}
