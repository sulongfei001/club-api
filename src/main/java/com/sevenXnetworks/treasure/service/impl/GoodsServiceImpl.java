package com.sevenXnetworks.treasure.service.impl;

import com.sevenXnetworks.treasure.dao.GoodsDao;
import com.sevenXnetworks.treasure.entity.GoodsEntity;
import com.sevenXnetworks.treasure.service.GoodsService;
import com.sevenXnetworks.treasure.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/11/21 16:51
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<GoodsVo> list() {
        List<GoodsEntity> goodsEntities = goodsDao.findAll();
        List<GoodsVo> goodsVos = new ArrayList<>();
        goodsEntities.forEach(entity -> {
            goodsVos.add(toVo(entity));
        });
        return goodsVos;
    }

    @Override
    public List<GoodsVo> passList() {
        List<GoodsEntity> goodsEntities = goodsDao.findPass();
        List<GoodsVo> goodsVos = new ArrayList<>();
        goodsEntities.forEach(entity -> {
            goodsVos.add(toVo(entity));
        });
        return goodsVos;
    }

    private GoodsVo toVo(GoodsEntity entity) {
        GoodsVo vo = new GoodsVo();
        vo.setGoodsId(entity.getGoodsId());
        vo.setGoodsName(entity.getGoodsName());
        vo.setGoodsType(entity.getGoodsType());
        vo.setGoodsPrdValueMon(entity.getGoodsPrdValueMon());
        return vo;
    }
}
