package com.fengying.ad.handler;

import com.fengying.ad.dump.table.AdCreativeTable;
import com.fengying.ad.dump.table.AdPlanTable;
import com.fengying.ad.dump.table.AdUnitTable;
import com.fengying.ad.index.DateTable;
import com.fengying.ad.index.adplan.AdPlanIndex;
import com.fengying.ad.index.adplan.AdPlanObject;
import com.fengying.ad.index.adunit.AdUnitIndex;
import com.fengying.ad.index.adunit.AdUnitObject;
import com.fengying.ad.index.creative.CreativeIndex;
import com.fengying.ad.index.creative.CreativeObject;
import com.fengying.ad.index.indexAware;
import com.fengying.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

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
