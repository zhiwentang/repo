package com.fengying.ad.index.interest;

import com.fengying.ad.index.indexAware;
import com.fengying.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class UnitItIndex implements indexAware<String, Set<Long>> {

    private static Map<String,Set<Long>> itUnitMap;

    private static Map<Long,Set<String>> unitItMap;

    static {
        itUnitMap=new ConcurrentHashMap<>();
        unitItMap=new ConcurrentHashMap<>();
    }


    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitItIndex,before add: {}",unitItMap);
        Set<Long> unitIdSet = CommonUtils.getorCreate(key,itUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);
        for(Long unitId:value){
            Set<String> its= CommonUtils.getorCreate(unitId,
                    unitItMap,ConcurrentSkipListSet::new);
            its.add(key);
        }
        log.info("UnitItIndex,after add: {}",unitItMap);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitItIndex,before delete: {}",unitItMap);
        Set<Long> unitIds=CommonUtils.getorCreate(key,itUnitMap,ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for (Long unitId:value){
            Set<String> its=CommonUtils.
                    getorCreate(unitId,unitItMap,ConcurrentSkipListSet::new);
            its.remove(key);
        }
        log.info("UnitItIndex,after delete: {}",unitItMap);

    }

    public boolean match(Long unitId, List<String> its){

        if(unitItMap.containsKey(unitId)&& CollectionUtils.isNotEmpty(unitItMap.get(unitId))){
            Set<String> itTags=unitItMap.get(unitId);
            return CollectionUtils.isSubCollection(its,itTags);//its是itTags的字集
        }
        return false;
    }
}
