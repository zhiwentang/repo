package com.fengying.ad.controller;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.annotation.IgnoreResponseAdvice;
import com.fengying.ad.client.SponsorClient;
import com.fengying.ad.client.vo.AdPlan;
import com.fengying.ad.client.vo.AdPlanGetRequest;
import com.fengying.ad.search.ISearch;
import com.fengying.ad.search.impl.SearchImpl;
import com.fengying.ad.search.vo.SearchRequest;
import com.fengying.ad.search.vo.SearchResponse;
import com.fengying.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class SearchController {

    @Autowired
    private ISearch search;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SponsorClient sponsorClient;

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request){
        log.info("ad-search:fetchAds->{}",JSON.toJSONString(request));
        return search.fetchAds(request);
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request){
        log.info("ad-search:getAdPlans->{}",JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(@RequestBody AdPlanGetRequest request){
        log.info("ad-search:getAdPlansByRibbon->{}", JSON.toJSONString(request));
        return restTemplate.postForEntity("http://ad-sponsor/ad-sponsor/get/adPlan",request,CommonResponse.class).getBody();
    }




}
