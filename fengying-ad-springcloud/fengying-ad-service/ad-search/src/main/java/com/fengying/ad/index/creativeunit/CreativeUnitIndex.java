package com.fengying.ad.index.creativeunit;

import com.fengying.ad.index.adunit.AdUnitObject;
import com.fengying.ad.index.indexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class CreativeUnitIndex implements indexAware<String,CreativeUnitObject> {
    //这里的string为adId-unitId

    //<adId-unitId,CreativeUnitObject>
    private static Map<String,CreativeUnitObject> objectMap;
    //<adId,unitId set>
    private static Map<Long,Set<Long>> creativeUnitMap;
    //<unitId,adId set>
    private static Map<Long,Set<Long>> unitCreativeMap;

    static {
        objectMap=new ConcurrentHashMap<>();
        creativeUnitMap=new ConcurrentHashMap<>();
        unitCreativeMap=new ConcurrentHashMap<>();
    }
    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("before add:{}",objectMap);
        objectMap.put(key,value);
        Set<Long> unitSet=creativeUnitMap.get(value.getAdId());
        if(CollectionUtils.isEmpty(unitSet)){
            unitSet=new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(),unitSet);
        }
        unitSet.add(value.getUnitId());

        Set<Long> creativeSet=unitCreativeMap.get(value.getUnitId());
        if(CollectionUtils.isEmpty(creativeSet)){
            creativeSet=new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(),creativeSet);
        }
        creativeSet.add((value.getAdId()));
        log.info("after add:{}",objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex index can not support update");

    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("before delete:{}",objectMap);
        objectMap.remove(key);

        Set<Long> unitSet=creativeUnitMap.get(value.getAdId());
        if(CollectionUtils.isNotEmpty(unitSet)){
            unitSet.remove(value.getUnitId());
        }

        Set<Long> creativeSet=unitCreativeMap.get(value.getUnitId());
        if(CollectionUtils.isNotEmpty(creativeSet)){
            creativeSet.remove(value.getAdId());
        }
        log.info("after delete:{}",objectMap);

    }

    public List<Long> selectAds(List<AdUnitObject> unitObjects){
        if(CollectionUtils.isEmpty(unitObjects)){
            return Collections.emptyList();
        }
        List<Long> result=new ArrayList<>();
        for (AdUnitObject unitObject : unitObjects) {
            Set<Long> adIds=unitCreativeMap.get(unitObject);
            if(CollectionUtils.isNotEmpty(adIds)){
                result.addAll(adIds);
            }
        }
        return result;
    }
}
