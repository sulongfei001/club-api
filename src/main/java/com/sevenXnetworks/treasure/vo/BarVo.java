package com.sevenXnetworks.treasure.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author sulongfei
 * @Date 2018/10/31 15:20
 * @Version 1.0
 */
@Data
public class BarVo {
    private long id;
    private String name;
    private Byte havePromotion;
    private List<BarSnapshotVo> snapshotVos;
}
