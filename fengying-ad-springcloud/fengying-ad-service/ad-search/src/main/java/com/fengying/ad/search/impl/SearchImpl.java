package com.fengying.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.index.CommonStatus;
import com.fengying.ad.index.DateTable;
import com.fengying.ad.index.adunit.AdUnitIndex;
import com.fengying.ad.index.adunit.AdUnitObject;
import com.fengying.ad.index.creative.CreativeIndex;
import com.fengying.ad.index.creative.CreativeObject;
import com.fengying.ad.index.creativeunit.CreativeUnitIndex;
import com.fengying.ad.index.district.UnitDistrictIndex;
import com.fengying.ad.index.interest.UnitItIndex;
import com.fengying.ad.index.keyword.UnitKeywordIndex;
import com.fengying.ad.search.ISearch;
import com.fengying.ad.search.vo.SearchRequest;
import com.fengying.ad.search.vo.SearchResponse;
import com.fengying.ad.search.vo.feature.DistrictFeature;
import com.fengying.ad.search.vo.feature.FeatureRelation;
import com.fengying.ad.search.vo.feature.ItFeature;
import com.fengying.ad.search.vo.feature.KeywordFeature;
import com.fengying.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SearchImpl implements ISearch {
    @Override
    public SearchResponse fetchAds(SearchRequest request) {
        //请求的广告位信息
        List<AdSlot> adSlots=request.getRequestInfo().getAdSlots();
        //三个feature
        KeywordFeature keywordFeature=request.getFeatureInfo().getKeywordFeature();
        ItFeature itFeature=request.getFeatureInfo().getItFeature();
        DistrictFeature districtFeature=request.getFeatureInfo().getDistrictFeature();
        FeatureRelation featureRelation=request.getFeatureInfo().getRelation();

        SearchResponse searchResponse=new SearchResponse();
        Map<String,List<SearchResponse.Creative>> adSlotAds= searchResponse.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            Set<Long> adUnitIdSet= DateTable.of(AdUnitIndex.class).match(adSlot.getPositionType());
            if(featureRelation==FeatureRelation.AND){
                filterKeywordFeature(adUnitIdSet,keywordFeature);
                filterDistrictFeature(adUnitIdSet,districtFeature);
                filterItFeature(adUnitIdSet,itFeature);
                targetUnitIdSet=adUnitIdSet;
            }else {
                targetUnitIdSet=getORRelationUnitIds(adUnitIdSet,keywordFeature,districtFeature,itFeature);
            }
            List<AdUnitObject> unitObjects=DateTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            filterAdUnitAndPlanstatus(unitObjects,CommonStatus.VAILD);

            List<Long> adIds=DateTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creativeObjects=DateTable.of(CreativeIndex.class).fetch(adIds);

            filterCreativeByAdSlot(creativeObjects,adSlot.getWidth(),adSlot.getHeigth(),adSlot.getType());

            adSlotAds.put(adSlot.getAdSlotCode(),buildCreativeResponse(creativeObjects));


        }
        log.info("fetchAds {}-{}", JSON.toJSONString(request),JSON.toJSONString(searchResponse));
        return searchResponse;
    }

    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature,ItFeature itFeature){
        if(CollectionUtils.isEmpty(adUnitIdSet)){
            return Collections.emptySet();
        }
        Set<Long> keywordUnitIdSet=new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet=new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet=new HashSet<>(adUnitIdSet);
        filterItFeature(itUnitIdSet,itFeature);
        filterDistrictFeature(districtUnitIdSet,districtFeature);
        filterKeywordFeature(keywordUnitIdSet,keywordFeature);
        return new HashSet<>(CollectionUtils.union(
                CollectionUtils.union(
                        keywordUnitIdSet,districtUnitIdSet
                ),itUnitIdSet
        ));
    }

    private void filterKeywordFeature(Collection<Long> adUnitIds,KeywordFeature keywordFeature){
        if(CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if(CollectionUtils.isNotEmpty(keywordFeature.getKeywords())){
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId->DateTable.of(UnitKeywordIndex.class).match(adUnitId,keywordFeature.getKeywords())
            );
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds,DistrictFeature districtFeature){
        if(CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())){
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId->DateTable.of(UnitDistrictIndex.class).match(adUnitId,districtFeature.getDistricts())
            );
        }
    }

    private void filterItFeature(Collection<Long> adUnitIds,ItFeature itFeature){
        if(CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if(CollectionUtils.isNotEmpty(itFeature.getIts())){
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId->DateTable.of(UnitItIndex.class).match(adUnitId,itFeature.getIts())
            );
        }
    }

    private void filterAdUnitAndPlanstatus(List<AdUnitObject> unitObjects, CommonStatus status){
        if(CollectionUtils.isEmpty(unitObjects)){
            return;
        }
        CollectionUtils.filter(
                unitObjects,object->object.getUnitStatus().equals(status)&&
                        object.getAdPlanObject().getPlanStatus().equals(status)
        );
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creatives,
                                        Integer width,
                                        Integer height,
                                        List<Integer> type){
        if(CollectionUtils.isEmpty(creatives)){
            return;
        }
        CollectionUtils.filter(
                creatives,
                creative->creative.getAuditStatus().equals(CommonStatus.VAILD.getStatus())
                &&creative.getWidth().equals(width)
                &&creative.getHeight().equals(height)
                &&type.contains(creative.getType())
        );
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creatives){
        if(CollectionUtils.isEmpty(creatives)){
            return Collections.emptyList();
        }
        CreativeObject randomObject=creatives.get(
                Math.abs(new Random().nextInt())%creatives.size()
        );

        return Collections.singletonList(SearchResponse.convert(randomObject));
    }
}
