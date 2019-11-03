package com.fengying.ad.service.impl;

import com.fengying.ad.dao.CreativeRepository;
import com.fengying.ad.entity.Creative;
import com.fengying.ad.exception.AdException;
import com.fengying.ad.service.ICreativeService;
import com.fengying.ad.vo.CreativeRequest;
import com.fengying.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateServiceImpl implements ICreativeService {

    @Autowired
    private CreativeRepository creativeRepository;

    @Override
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {

        Creative creative=creativeRepository.save(request.convertToEntity());
        return new CreativeResponse(creative.getId(),creative.getName());
    }
}
