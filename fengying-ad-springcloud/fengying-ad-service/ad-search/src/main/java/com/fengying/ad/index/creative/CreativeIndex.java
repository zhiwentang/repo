package com.fengying.ad.index.creative;

import com.fengying.ad.index.indexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CreativeIndex implements indexAware<Long,CreativeObject> {

    private static Map<Long,CreativeObject> objectMap;

    static {
        objectMap=new ConcurrentHashMap<>();
    }

    public List<CreativeObject> fetch(Collection<Long> adIds){
        if(CollectionUtils.isEmpty(adIds)){
            return Collections.emptyList();
        }
        List<CreativeObject> result=new ArrayList<>();
        adIds.forEach(u->{
            CreativeObject creativeObject=get(u);
            if(null==creativeObject){
                log.error("creativeObject not found",u);
                return;
            }
            result.add(creativeObject);
        });
        return result;
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("before add: {}",objectMap);
        objectMap.put(key,value);
        log.info("after add: {}",objectMap);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("before update: {}",objectMap);
        CreativeObject oldObject=objectMap.get(key);
        if(oldObject==null){
            objectMap.put(key,value);
        }else {
            oldObject.update(value);
        }
        log.info("before update: {}",objectMap);
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("before delete: {}",objectMap);
        objectMap.remove(key);
        log.info("after delete: {}",objectMap);
    }
}
