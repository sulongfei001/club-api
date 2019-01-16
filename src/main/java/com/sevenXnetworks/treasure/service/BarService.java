package com.sevenXnetworks.treasure.service;

import com.sevenXnetworks.treasure.vo.BarVo;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:18
 * @Version 1.0
 */
public interface BarService {
    List<BarVo> appPassList(long beginId, int offset);
}
