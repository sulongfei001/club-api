package com.sevenXnetworks.treasure.service;

import com.sevenXnetworks.treasure.bean.OrderBean;
import com.sevenXnetworks.treasure.vo.OrderVo;
import com.sevenXnetworks.treasure.vo.UserDataVo;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 18-11-6 上午11:19
 * @Version 1.0
 */
public interface OrderService {
    void appCreate(OrderBean orderBean);

    List<OrderVo> appList(String memberId, long beginId, int offset);

    OrderVo appGet(long id);

    void random(long id);
}
