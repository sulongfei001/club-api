package com.sevenXnetworks.treasure.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sevenXnetworks.treasure.bean.OrderBean;
import com.sevenXnetworks.treasure.config.ConfigProperties;
import com.sevenXnetworks.treasure.dao.ActivityDao;
import com.sevenXnetworks.treasure.dao.ActivitySnapshotDao;
import com.sevenXnetworks.treasure.dao.BarDao;
import com.sevenXnetworks.treasure.dao.OrderDao;
import com.sevenXnetworks.treasure.entity.ActivityEntity;
import com.sevenXnetworks.treasure.entity.ActivitySnapshotEntity;
import com.sevenXnetworks.treasure.entity.BarEntity;
import com.sevenXnetworks.treasure.entity.OrderEntity;
import com.sevenXnetworks.treasure.model.Const;
import com.sevenXnetworks.treasure.service.OrderService;
import com.sevenXnetworks.treasure.utils.StringUtils;
import com.sevenXnetworks.treasure.vo.ActivitySnapshotVo;
import com.sevenXnetworks.treasure.vo.OrderVo;
import com.sevenXnetworks.treasure.vo.UserDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * @Description
 * @Author sulongfei
 * @Date 18-11-6 上午11:20
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ConfigProperties properties;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private BarDao barDao;
    @Autowired
    private ActivitySnapshotDao snapshotDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void appCreate(OrderBean orderBean) {
        OrderEntity orderEntity = toEntity(orderBean);
        orderDao.saveOrUpdate(orderEntity);
    }

    @Override
    public List<OrderVo> appList(String memberId, long beginId, int offset) {
        List<OrderEntity> orderEntities = orderDao.list(memberId, beginId, offset);
        List<OrderVo> orderVos = new ArrayList<>();
        orderEntities.forEach(entity -> {
            ActivityEntity activityEntity = activityDao.findOne(entity.getActivityId());
            List<ActivitySnapshotEntity> snapshotEntities = snapshotDao.findByActivityIdAndType(entity.getActivityId(), Const.SNAPSHOT_TYPE_2);
            List<ActivitySnapshotVo> snapshotVos = toVo(snapshotEntities);
            BarEntity barEntity = null;
            if (StringUtils.isNotBlank(activityEntity.getBarId())) {
                barEntity = barDao.findOne(activityEntity.getBarId());
            }
            OrderVo vo = new OrderVo();
            vo.setId(entity.getId());
            vo.setOrderId(entity.getOrderId());
            vo.setGoodsName(activityEntity.getGoodsName());
            vo.setTradeCount(entity.getTradeCount());
            vo.setCreateDate(entity.getCreateDate());
            vo.setTradeDate(entity.getTradeDate());
            vo.setTradeMoney(entity.getTradeMoney());
            vo.setIsWin(entity.getIsWin());
            vo.setIsRun(verifyTime(activityEntity.getStartTime(), activityEntity.getEndTime()));
            vo.setIsPromotion((byte) (barEntity == null ? 0 : 1));
            vo.setSnapshotVos(snapshotVos);
            orderVos.add(vo);
        });
        return orderVos;
    }

    @Override
    public OrderVo appGet(long id) {
        OrderEntity orderEntity = orderDao.findOne(id);
        ActivityEntity activityEntity = activityDao.findOne(orderEntity.getActivityId());
        List<OrderEntity> orderEntities = orderDao.findIsWin(activityEntity.getId());
        BarEntity barEntity = null;
        if (StringUtils.isNotBlank(activityEntity.getBarId())) {
            barEntity = barDao.findOne(activityEntity.getBarId());
        }
        OrderVo vo = new OrderVo();
        vo.setId(orderEntity.getId());
        vo.setOrderId(orderEntity.getOrderId());
        vo.setGoodsName(activityEntity.getGoodsName());
        vo.setTradeCount(orderEntity.getTradeCount());
        vo.setCreateDate(orderEntity.getCreateDate());
        vo.setTradeDate(orderEntity.getTradeDate());
        vo.setTradeMoney(orderEntity.getTradeMoney());
        vo.setIsWin(orderEntity.getIsWin());
        vo.setWinName(orderEntities != null && orderEntities.size() > 0 ? orderEntities.get(0).getUserName() : "");
        vo.setWinCode(orderEntity.getWinCode());
        vo.setIsRun(verifyTime(activityEntity.getStartTime(), activityEntity.getEndTime()));
        vo.setIsPromotion((byte) (barEntity == null ? 0 : 1));
        return vo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void random(long id) {
        List<OrderEntity> orderEntities = orderDao.findIsWin(id);
        if (orderEntities != null && orderEntities.size() > 0) return;
        List<OrderEntity> orderEntityList = orderDao.findByActivityId(id);
        if (orderEntityList != null && orderEntityList.size() > 0) {
            OrderEntity orderEntity = orderEntityList.get(new Random().nextInt(orderEntityList.size()));
            orderEntity.setIsWin((byte) 1);
            orderEntity.setWinCode("you win");
            orderDao.saveOrUpdate(orderEntity);
        }
    }

    private OrderEntity toEntity(OrderBean orderBean) {
        OrderEntity entity = new OrderEntity();
        entity.setOrderId(orderBean.getOrderId());
        entity.setActivityId(orderBean.getActivityId());
        entity.setOpenId(orderBean.getOpenId());
        entity.setMemberId(orderBean.getMemberId());
        entity.setUserName(orderBean.getUserName());
        entity.setCellPhone(orderBean.getCellPhone());
        entity.setCreateDate(orderBean.getCreateDate());
        entity.setTradeDate(orderBean.getTradeDate());
        entity.setTradeCount(orderBean.getTradeCount());
        entity.setTradeMoney(orderBean.getTradeMoney());
        entity.setIsWin((byte) 0);
        entity.setWinCode("");
        return entity;
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

    private Byte verifyTime(Timestamp startTime, Timestamp endTime) {
        Calendar now = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        now.setTime(new Date());
        start.setTime(startTime);
        end.setTime(endTime);
        return (byte) (now.after(start) && now.before(end) ? 1 : 0);
    }


}
