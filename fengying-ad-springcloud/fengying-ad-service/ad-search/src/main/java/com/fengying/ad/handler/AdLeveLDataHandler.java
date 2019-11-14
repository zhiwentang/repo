package com.fengying.ad.handler;

import com.alibaba.fastjson.JSON;
import com.fengying.ad.dump.table.*;
import com.fengying.ad.index.DateTable;
import com.fengying.ad.index.adplan.AdPlanIndex;
import com.fengying.ad.index.adplan.AdPlanObject;
import com.fengying.ad.index.adunit.AdUnitIndex;
import com.fengying.ad.index.adunit.AdUnitObject;
import com.fengying.ad.index.creative.CreativeIndex;
import com.fengying.ad.index.creative.CreativeObject;
import com.fengying.ad.index.creativeunit.CreativeUnitIndex;
import com.fengying.ad.index.creativeunit.CreativeUnitObject;
import com.fengying.ad.index.district.UnitDistrictIndex;
import com.fengying.ad.index.indexAware;
import com.fengying.ad.index.interest.UnitItIndex;
import com.fengying.ad.index.keyword.UnitKeywordIndex;
import com.fengying.ad.mysql.constant.OpType;
import com.fengying.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class AdLeveLDataHandler {

    public static void handlerLevel2(AdPlanTable planTable,OpType type){

        AdPlanObject adPlanObject=new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        handleBinlogEvent(
                DateTable.of(AdPlanIndex.class),adPlanObject.getPlanId(),adPlanObject,type
        );
    }

    public static void handlerLevel2(AdCreativeTable adCreativeTable,OpType type){

        CreativeObject creativeObject=new CreativeObject(
                adCreativeTable.getAdId(),
                adCreativeTable.getName(),
                adCreativeTable.getType(),
                adCreativeTable.getMaterialTpye(),
                adCreativeTable.getHeight(),
                adCreativeTable.getWidth(),
                adCreativeTable.getAuditStatus(),
                adCreativeTable.getAdUrl()
        );
        handleBinlogEvent(
                DateTable.of(CreativeIndex.class),creativeObject.getAdId(),creativeObject,type
        );
    }

    public static void handlerLevel3(AdUnitTable adUnitTable,OpType type){

        AdPlanObject adPlanObject= DateTable.of(AdPlanIndex.class).get(adUnitTable.getPlanId());
        if (null==adPlanObject){
            log.error("handlerLevel3 found AdPlanObject error:{}",adUnitTable.getPlanId());
            return;
        }

        AdUnitObject adUnitObject=new AdUnitObject(
                adUnitTable.getUnitId(),
                adUnitTable.getPlanId(),
                adUnitTable.getPositionType(),
                adUnitTable.getUnitStatus(),
                adPlanObject
        );

        handleBinlogEvent(
                DateTable.of(AdUnitIndex.class),
                adUnitTable.getUnitId(),
                adUnitObject,
                type
        );
    }

    public static void handlerLevel3(AdCreativeUnitTable adCreativeUnitTable,OpType type){
        if(type==OpType.UPDATE){
            log.error("creativeUnitIndex not support update");
            return;
        }
        AdUnitObject unitObject=DateTable.of(AdUnitIndex.class).get(adCreativeUnitTable.getUnitId());
        CreativeObject creativeObject=DateTable.of(CreativeIndex.class).get(adCreativeUnitTable.getAdId());

        if(unitObject==null||creativeObject==null){
            log.error("adCreativeUnitindex error: {}", JSON.toJSONString(adCreativeUnitTable));
            return;
        }
        CreativeUnitObject creativeUnitObject=new CreativeUnitObject
                (adCreativeUnitTable.getAdId(),adCreativeUnitTable.getUnitId());

        handleBinlogEvent(
                DateTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(creativeUnitObject.getAdId().toString(),creativeUnitObject.getUnitId().toString()),
                creativeUnitObject,
                type
        );
    }

    public static void handlerLevel4(AdUnitDistrictTable unitDistrictTable,OpType type){
        if(type==OpType.UPDATE){
            log.error("AdUnitDistrictTable index not support update");
            return;
        }
        AdUnitObject unitObject=DateTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if(unitObject==null){
            log.error("AdUnitDistrictTable index error:{}",unitDistrictTable.getUnitId());
            return;
        }
        String key=CommonUtils.stringConcat(unitDistrictTable.getProvince(),unitDistrictTable.getCity());
        Set<Long> value=new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())
        );
        handleBinlogEvent(
                DateTable.of(UnitDistrictIndex.class),
                key,value,
                type
        );
    }

    public static void handlerLevel4(AdUnitKeywordTable unitKeywordTable,OpType type){
        if(type==OpType.UPDATE){
            log.error("AdUnitKeywordTable index not support update");
            return;
        }
        AdUnitObject unitObject=DateTable.of(AdUnitIndex.class).get(unitKeywordTable.getUnitId());
        if(unitObject==null){
            log.error("AdUnitKeywordTable index error:{}",unitKeywordTable.getUnitId());
            return;
        }
        Set<Long> value=new HashSet<>(
                Collections.singleton(unitKeywordTable.getUnitId())
        );
        handleBinlogEvent(
                DateTable.of(UnitKeywordIndex.class),
                unitKeywordTable.getKeyword(),
                value,
                type
        );
    }

    public static void handlerLevel4(AdUnitItTable unitItTable,OpType type){
        if (type== null){
            log.error("AdUnitItTable index not support update");
        }
        AdUnitObject unitObject=DateTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());
        if (unitObject==null){
            log.error("AdUnitItTable index error:{}",unitItTable.getUnitId());
            return;
        }
        Set<Long> value=new HashSet<>(
                Collections.singleton(unitItTable.getUnitId())
        );
        handleBinlogEvent(
                DateTable.of(UnitItIndex.class),
                unitItTable.getItTag(),
                value,
                type
        );
    }




    private static <K, V> void handleBinlogEvent(indexAware<K,V> index, K key, V value, OpType type){
        switch (type){
            case ADD:
                index.add(key,value);
                break;
            case DELETE:
                index.delete(key,value);
                break;
            case UPDATE:
                index.update(key,value);
                break;
            default:
                break;
        }
    }
}
