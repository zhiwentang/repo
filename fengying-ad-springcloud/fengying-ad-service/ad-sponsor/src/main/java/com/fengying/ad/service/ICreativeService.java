package com.fengying.ad.service;

import com.fengying.ad.entity.Creative;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.vo.CreativeRequest;
import com.fengying.ad.vo.CreativeResponse;

public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
