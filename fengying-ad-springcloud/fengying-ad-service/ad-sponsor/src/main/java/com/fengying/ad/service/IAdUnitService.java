package com.fengying.ad.service;

import com.fengying.ad.exception.AdException;
import com.fengying.ad.vo.*;

public interface IAdUnitService {
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;
}
