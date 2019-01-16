package com.sevenXnetworks.treasure.service.impl;

import com.sevenXnetworks.treasure.config.ConfigProperties;
import com.sevenXnetworks.treasure.dao.ActivityDao;
import com.sevenXnetworks.treasure.dao.BarDao;
import com.sevenXnetworks.treasure.dao.BarSnapshotDao;
import com.sevenXnetworks.treasure.entity.BarEntity;
import com.sevenXnetworks.treasure.entity.BarSnapshotEntity;
import com.sevenXnetworks.treasure.service.BarService;
import com.sevenXnetworks.treasure.vo.BarSnapshotVo;
import com.sevenXnetworks.treasure.vo.BarVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:18
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class BarServiceImpl implements BarService {
    @Autowired
    private BarDao barDao;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ConfigProperties properties;
    @Autowired
    private BarSnapshotDao snapshotDao;

    @Override
    public List<BarVo> appPassList(long beginId, int offset) {
        List<BarEntity> barEntities = barDao.findPass(beginId, offset);
        List<BarVo> barVos = new ArrayList<>();
        barEntities.forEach(barEntity -> {
            long count = activityDao.findByBarId(barEntity.getId());
            List<BarSnapshotEntity> snapshotEntities = snapshotDao.findByBarId(barEntity.getId());
            List<BarSnapshotVo> snapshotVos = new ArrayList<>();
            snapshotEntities.forEach(entity -> {
                snapshotVos.add(toVo(entity));
            });
            BarVo vo = toVo(barEntity);
            vo.setSnapshotVos(snapshotVos);
            vo.setHavePromotion((byte) (count > 0 ? 1 : 0));
            barVos.add(vo);
        });
        return barVos;
    }

    private BarSnapshotVo toVo(BarSnapshotEntity entity) {
        BarSnapshotVo vo = new BarSnapshotVo();
        vo.setUrl(properties.getUploadAddr() + entity.getUrl());
        return vo;
    }

    private BarVo toVo(BarEntity barEntity) {
        BarVo vo = new BarVo();
        vo.setId(barEntity.getBarId());
        vo.setName(barEntity.getBarName());
        return vo;
    }
}
