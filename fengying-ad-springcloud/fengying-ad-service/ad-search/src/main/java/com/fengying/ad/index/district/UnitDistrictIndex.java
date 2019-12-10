package com.fengying.ad.index.district;

import com.fengying.ad.index.indexAware;
import com.fengying.ad.search.vo.feature.DistrictFeature;
import com.fengying.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UnitDistrictIndex implements indexAware<String, Set<Long>> {

    //此时的string为province-city连接在一起
    private static Map<String,Set<Long>> districtUnitMap;
    private static Map<Long,Set<String>> unitDistrictMap;

    static {
        unitDistrictMap=new ConcurrentHashMap<>();
        districtUnitMap=new ConcurrentHashMap<>();
    }
    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("UnitDistrictIndex,before add: {}",unitDistrictMap);
        Set<Long> unitIdSet = CommonUtils.getorCreate(key,districtUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);
        for(Long unitId:value){
            Set<String> its= CommonUtils.getorCreate(unitId,
                    unitDistrictMap,ConcurrentSkipListSet::new);
            its.add(key);
        }
        log.info("UnitDistrictIndex,after add: {}",unitDistrictMap);

    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("district index can not support update");

    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitDistrictIndex,before delete: {}",unitDistrictMap);
        Set<Long> unitIds=CommonUtils.getorCreate(key,districtUnitMap,ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for (Long unitId:value){
            Set<String> its=CommonUtils.
                    getorCreate(unitId,unitDistrictMap,ConcurrentSkipListSet::new);
            its.remove(key);
        }
        log.info("UnitDistrictIndex,after delete: {}",unitDistrictMap);

    }

    public boolean match(Long unitId, List<DistrictFeature.ProvinceAndCity> districts){
        if(unitDistrictMap.containsKey(unitId)&&CollectionUtils.isNotEmpty(unitDistrictMap.get(unitId))){
            Set<String> unitDistricts=unitDistrictMap.get(unitId);
            List<String> targetDistricts=districts.stream().map(
                    d->CommonUtils.stringConcat(d.getProvince(),d.getCity())
            ).collect(Collectors.toList());
            return CollectionUtils.isSubCollection(targetDistricts,unitDistricts);
        }
        return false;
    }

}
