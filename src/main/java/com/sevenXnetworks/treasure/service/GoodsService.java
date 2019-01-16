package com.sevenXnetworks.treasure.service;

import com.sevenXnetworks.treasure.vo.GoodsVo;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/11/21 16:51
 * @Version 1.0
 */
public interface GoodsService {
    List<GoodsVo> list();

    List<GoodsVo> passList();
}
