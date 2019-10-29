package com.fengying.ad.service;

import com.fengying.ad.exception.AdException;
import com.fengying.ad.vo.CreateUserRequest;
import com.fengying.ad.vo.CreateUserResponse;

public interface IUserService {
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
