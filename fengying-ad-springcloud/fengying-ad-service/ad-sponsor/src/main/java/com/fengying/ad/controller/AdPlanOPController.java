package com.fengying.ad.controller;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.entity.AdPlan;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.service.IAdPlanService;
import com.fengying.ad.vo.AdPlanGetRequest;
import com.fengying.ad.vo.AdPlanRequest;
import com.fengying.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class AdPlanOPController {

    @Autowired
    private IAdPlanService iAdPlanService;

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor:createAdPlan->{}", JSON.toJSONString(request));
        return iAdPlanService.createAdPlan(request);
    }

    //获取推广计划

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException{
        log.info("ad-sponsor:getAdPlanByIds->{}",JSON.toJSONString(request));
        return iAdPlanService.getAdPlanByIds(request);
    }

    //更新推广计划
    @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor:updateAdPlan->{}",JSON.toJSONString(request));
        return iAdPlanService.updateAdPlan(request);
    }

    //删除推广计划
    @DeleteMapping
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor:deleteAdPlan->{}",JSON.toJSONString(request));
        iAdPlanService.deleteAdPlan(request);
    }

}
