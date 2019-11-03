package com.fengying.ad.controller;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.service.ICreativeService;
import com.fengying.ad.vo.CreativeRequest;
import com.fengying.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreativeOPController {

    @Autowired
    private ICreativeService iCreativeService;

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException{
        log.info("ad-sponsor:createCreative->{}", JSON.toJSONString(request));
        return iCreativeService.createCreative(request);
    }


}
