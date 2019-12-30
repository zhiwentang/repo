package com.fengying.ad.index.adunit;

import com.fengying.ad.index.indexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class AdUnitIndex implements indexAware<Long,AdUnitObject> {

    private static Map<Long,AdUnitObject> objectMap;

    static {
        objectMap=new ConcurrentHashMap<>();
    }

    public Set<Long> match(Integer positionType){

        Set<Long> adUnitIds=new HashSet<>();
        objectMap.forEach((K,V)->{
            if(AdUnitObject.isAdSlotTypeOK(positionType,V.getPositionType())) {
                adUnitIds.add(K);
            }
        });
        return adUnitIds;
    }

    public List<AdUnitObject> fetch(Collection<Long> adUnitIds){
        if(CollectionUtils.isEmpty(adUnitIds)){
            return Collections.emptyList();
        }
        List<AdUnitObject> unitObjects=new ArrayList<>();
        adUnitIds.forEach(p->{
            AdUnitObject adUnitObject=get(p);
            if(adUnitObject==null){
                log.error("adUnitObject not found:{}",p);
                return;
            }
            unitObjects.add(adUnitObject);
        });
        return unitObjects;
    }

    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdUnitObject value) {
        log.info("before add: {}",objectMap);
        objectMap.put(key,value);
        log.info("after add: {}",objectMap);
    }

    @Override
    public void update(Long key, AdUnitObject value) {
        log.info("before update: {}",objectMap);
        AdUnitObject oldObject=objectMap.get(key);
        if(null==oldObject){
            objectMap.put(key,value);
        }else {
            oldObject.update(value);
        }
        log.info("after update: {}",objectMap);
    }

    @Override
    public void delete(Long key, AdUnitObject value) {
        log.info("before delete: {}",objectMap);
        objectMap.remove(key);
        log.info("before delete: {}",objectMap);
    }

}
