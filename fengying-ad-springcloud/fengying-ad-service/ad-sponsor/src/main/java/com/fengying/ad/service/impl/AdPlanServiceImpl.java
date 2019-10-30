package com.fengying.ad.service.impl;

import com.fengying.ad.constant.CommonStatus;
import com.fengying.ad.constant.Constants;
import com.fengying.ad.dao.AdPlanRepository;
import com.fengying.ad.dao.AdUserRepository;
import com.fengying.ad.entity.AdPlan;
import com.fengying.ad.entity.AdUser;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.service.IAdPlanService;
import com.fengying.ad.utils.CommonUtils;
import com.fengying.ad.vo.AdPlanGetRequest;
import com.fengying.ad.vo.AdPlanRequest;
import com.fengying.ad.vo.AdPlanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdPlanServiceImpl implements IAdPlanService {

    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private AdUserRepository adUserRepository;
    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if(!request.createValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        //确定user存在
        Optional<AdUser> adUser=adUserRepository.findById(request.getUserId());
        if(!adUser.isPresent()){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdPlan oldPlan=adPlanRepository.findByUserIDAndPlanName(request.getUserId(),request.getPlanName());
        if(oldPlan!=null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newPlan=adPlanRepository.save(new AdPlan(request.getUserId(),request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),
                CommonUtils.parseStringDate(request.getEndDate())));
        return new AdPlanResponse(newPlan.getId(),newPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {

        if(!request.validate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return adPlanRepository.findAllByIdInAndUserId(request.getIds(),request.getUserId());
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if(!request.updateValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan=adPlanRepository.findByIdAndUserId(request.getId(),request.getUserId());
        if(plan==null){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        if(request.getPlanName()!=null){
            AdPlan oldPlan=adPlanRepository.findByPlanName(request.getPlanName());
            if(oldPlan!=null){
                throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
            }
            plan.setPlanName(request.getPlanName());
        }
        if(request.getStartDate()!=null){
            plan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
        }
        if(request.getEndDate()!=null){
            plan.setEndDate(CommonUtils.parseStringDate((request.getEndDate())));
        }
        plan.setUpdateTime(new Date());
        plan=adPlanRepository.save(plan);
        return new AdPlanResponse(plan.getId(),plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if(!request.deleteValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan=adPlanRepository.findByIdAndUserId(request.getId(),request.getUserId());
        if(plan==null){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        adPlanRepository.save(plan);
    }
}
