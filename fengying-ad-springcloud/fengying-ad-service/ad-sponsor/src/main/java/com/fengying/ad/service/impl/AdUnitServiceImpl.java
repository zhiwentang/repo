package com.fengying.ad.service.impl;

import com.fengying.ad.constant.Constants;
import com.fengying.ad.dao.AdPlanRepository;
import com.fengying.ad.dao.AdUnitRepository;
import com.fengying.ad.dao.CreativeRepository;
import com.fengying.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.fengying.ad.dao.unit_condition.AdUnitItRepository;
import com.fengying.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.fengying.ad.dao.unit_condition.CreativeUnitRepository;
import com.fengying.ad.entity.AdPlan;
import com.fengying.ad.entity.AdUnit;
import com.fengying.ad.entity.unit_condition.AdUnitDistrict;
import com.fengying.ad.entity.unit_condition.AdUnitIt;
import com.fengying.ad.entity.unit_condition.AdUnitKeyword;
import com.fengying.ad.entity.unit_condition.CreativeUnit;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.service.IAdUnitService;
import com.fengying.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdUnitServiceImpl implements IAdUnitService {
    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;

    @Autowired
    private AdUnitKeywordRepository adUnitKeywordRepository;
    @Autowired
    private AdUnitItRepository adUnitItRepository;
    @Autowired
    private AdUnitDistrictRepository adUnitDistrictRepository;
    @Autowired
    private AdUnitRepository adUnitRepository;

    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
        if(!request.createValidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdPlan> adPlan=adPlanRepository.findById(request.getPlanId());
        if(!adPlan.isPresent()){
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        AdUnit oldUnit=adUnitRepository.findByPlanIdAndUnitName(request.getPlanId(),request.getUnitName());
        if(oldUnit!=null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }
        AdUnit newAdUnit=adUnitRepository.save(new AdUnit(request.getPlanId(),request.getUnitName(),request.getPositionType(),request.getBudget()));

        return new AdUnitResponse(newAdUnit.getId(),newAdUnit.getUnitName());
    }

    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        List<Long> unitIds=request.getUnitKeywords().stream().
                map(AdUnitKeywordRequest.UnitKeyword::getUnitId).collect(Collectors.toList());
        if(!isRelatedUnitExist(unitIds)){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<Long> ids= Collections.emptyList();

        List<AdUnitKeyword> unitKeywords=new ArrayList<>();
        if(!CollectionUtils.isEmpty(request.getUnitKeywords())){
            request.getUnitKeywords().forEach(i->unitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(),i.getKeyword())));
            ids=adUnitKeywordRepository.saveAll(unitKeywords).stream().
                    map(AdUnitKeyword::getId).collect(Collectors.toList());
        }

        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(
                new AdUnitIt(i.getUnitId(), i.getItTag())
        ));
        List<Long> ids = adUnitItRepository.saveAll(unitIts).stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());

        return new AdUnitItResponse(ids);

    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(d -> unitDistricts.add(
                new AdUnitDistrict(d.getUnitId(), d.getProvince(),
                        d.getCity())
        ));
        List<Long> ids = adUnitDistrictRepository.saveAll(unitDistricts)
                .stream().map(AdUnitDistrict::getId)
                .collect(Collectors.toList());

        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        List<Long> unitIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());
        List<Long> creativeIds = request.getUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());
        if(!(isRelatedUnitExist(unitIds)&&isRelatedCreativeExist(creativeIds))){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<CreativeUnit> creativeUnits=new ArrayList<>();
        request.getUnitItems().forEach(i->creativeUnits.add(new CreativeUnit(i.getCreativeId(),i.getUnitId())));
        List<Long> ids=creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId).collect(Collectors.toList());

        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> ids){
        if(CollectionUtils.isEmpty(ids)){
            return false;
        }
        return adUnitRepository.findAllById(ids).size()==new HashSet<>(ids).size();
    }

    private boolean isRelatedCreativeExist(List<Long> ids){
        if(CollectionUtils.isEmpty(ids)){
            return false;
        }
        return creativeRepository.findAllById(ids).size()==new HashSet<>(ids).size();
    }


}
