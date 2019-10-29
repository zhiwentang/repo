package com.fengying.ad.service.impl;

import com.fengying.ad.constant.Constants;
import com.fengying.ad.dao.AdUserRepository;
import com.fengying.ad.entity.AdUser;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.service.IUserService;
import com.fengying.ad.utils.CommonUtils;
import com.fengying.ad.vo.CreateUserRequest;
import com.fengying.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private final AdUserRepository userRepository;

    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if(!request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser=userRepository.findByUsername(request.getUsername());
        if(oldUser!=null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }
        AdUser newUser=userRepository.save(new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())));
        return new CreateUserResponse(newUser.getId(),newUser.getUsername(),newUser.getToken(),newUser.getCreateTime(),newUser.getCreateTime());
    }
}
