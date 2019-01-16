package com.sevenXnetworks.treasure.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Description
 * @Author sulongfei
 * @Date 18-11-6 上午10:52
 * @Version 1.0
 */
@Data
public class OrderBean {
    private String orderId;
    private Long activityId;
    private String openId;
    private String memberId;
    private String userName;
    private String cellPhone;
    private Timestamp createDate;
    private Timestamp tradeDate;
    private Integer tradeCount;
    private BigDecimal tradeMoney;

    public OrderBean(String orderId, Long activityId, String openId, String memberId, String userName, String cellPhone, Timestamp createDate, Timestamp tradeDate, Integer tradeCount, BigDecimal tradeMoney) {
        this.orderId = orderId;
        this.activityId = activityId;
        this.openId = openId;
        this.memberId = memberId;
        this.userName = userName;
        this.cellPhone = cellPhone;
        this.createDate = createDate;
        this.tradeDate = tradeDate;
        this.tradeCount = tradeCount;
        this.tradeMoney = tradeMoney;
    }
}
