package com.fengying.ad.service;

import com.fengying.ad.entity.AdPlan;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.vo.AdPlanGetRequest;
import com.fengying.ad.vo.AdPlanRequest;
import com.fengying.ad.vo.AdPlanResponse;

import java.util.List;

public interface IAdPlanService {
    //创建推广计划
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    //获取推广计划
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    //更新推广计划
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    //删除推广计划
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
