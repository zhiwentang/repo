package com.fengying.ad.client;

import com.fengying.ad.client.vo.AdPlan;
import com.fengying.ad.client.vo.AdPlanGetRequest;
import com.fengying.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1,"ad-sponsor error");
    }
}
