package com.sevenXnetworks.treasure.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 18-11-6 上午11:33
 * @Version 1.0
 */
@Data
public class OrderVo {
    private long id;
    private String orderId;
    private String goodsName;
    private String winName;
    private String winCode;
    private Timestamp createDate;
    private Timestamp tradeDate;
    private Integer tradeCount;
    private BigDecimal tradeMoney;
    private Byte isPromotion;
    private Byte isWin;
    private Byte isRun;
    private List<ActivitySnapshotVo> snapshotVos;
}
