package com.fengying.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdSlot {

    //广告位编码
    private String adSlotCode;

    //流量类型
    private Integer positionType;

    //广告位宽和高
    private Integer heigth;
    private Integer width;

    //广告物料类型:图片，视频等等
    private List<Integer> type;

    //最低出价
    private Integer minCpm;

}
