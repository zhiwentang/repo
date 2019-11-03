package com.fengying.ad.controller;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.service.IUserService;
import com.fengying.ad.vo.CreateUserRequest;
import com.fengying.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserOPController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor:createUser->{}", JSON.toJSONString(request));
        return iUserService.createUser(request);
    }

}
