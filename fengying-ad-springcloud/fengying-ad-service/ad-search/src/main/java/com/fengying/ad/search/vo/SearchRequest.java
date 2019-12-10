package com.fengying.ad.search.vo;

import com.fengying.ad.search.vo.feature.DistrictFeature;
import com.fengying.ad.search.vo.feature.FeatureRelation;
import com.fengying.ad.search.vo.feature.ItFeature;
import com.fengying.ad.search.vo.feature.KeywordFeature;
import com.fengying.ad.search.vo.media.AdSlot;
import com.fengying.ad.search.vo.media.App;
import com.fengying.ad.search.vo.media.Device;
import com.fengying.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {

    //媒体方的请求标识
    private String mediaId;
    //请求基本信息
    private RequestInfo requestInfo;
    //匹配信息
    private FeatureInfo featureInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo{
        private String requestId;
        private List<AdSlot> adSlots;
        private App app;
        private Device device;
        private Geo geo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo{
        private DistrictFeature districtFeature;
        private ItFeature itFeature;
        private KeywordFeature keywordFeature;
        private FeatureRelation relation=FeatureRelation.AND;
    }

}
