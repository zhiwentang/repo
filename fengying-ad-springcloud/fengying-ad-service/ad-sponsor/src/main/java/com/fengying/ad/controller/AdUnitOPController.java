package com.fengying.ad.controller;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.service.IAdUnitService;
import com.fengying.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AdUnitOPController {

    @Autowired
    private IAdUnitService iAdUnitService;

    @PostMapping("/create/unit")
    public AdUnitResponse createUnit(@RequestBody AdUnitRequest request) throws AdException{
        log.info("ad-sponsor:createUnit->{}", JSON.toJSONString(request));
        return iAdUnitService.createUnit(request);
    }

    @PostMapping("/create/unitKeyword")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest request) throws AdException{
        log.info("ad-sponsor:createUnitKeyword->{}",JSON.toJSONString(request));
        return iAdUnitService.createUnitKeyword(request);
    }

    @PostMapping("/create/unitIt")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request) throws AdException{
        log.info("ad-sponsor:createUnitIt->{}",JSON.toJSONString(request));
        return iAdUnitService.createUnitIt(request);
    }

    @PostMapping("/create/unitDistrict")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException{
        log.info("ad-sponsor:createUnitDistrict->{}",JSON.toJSONString(request));
        return iAdUnitService.createUnitDistrict(request);
    }

    @PostMapping("/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException{
        log.info("ad-sponsor:createCreativeUnit->{}",JSON.toJSONString(request));
        return iAdUnitService.createCreativeUnit(request);
    }

}
