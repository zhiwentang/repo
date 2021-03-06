package com.fengying.ad.index.adunit;

import antlr.CharQueue;
import com.fengying.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitObject {

    private Long unitId;
    private Long planId;
    private Integer positionType;
    private Integer unitStatus;
    private AdPlanObject adPlanObject;

    public void update(AdUnitObject newObject){
        if(null!=newObject.getUnitId()){
            this.unitId=newObject.getUnitId();
        }
        if(null!=newObject.getPlanId()){
            this.planId=newObject.getPlanId();
        }
        if(null!=newObject.getPositionType()){
            this.positionType=newObject.getPositionType();
        }
        if(null!=newObject.getUnitStatus()){
            this.unitStatus=newObject.getUnitStatus();
        }
        if(null!=newObject.getAdPlanObject()){
            this.adPlanObject=newObject.getAdPlanObject();
        }
    }

    private static boolean isKaiPing(int positionType){
        return (positionType&AdUnitConstants.POSITION_TYPE.KAIPING)>0;
    }

    private static boolean isTiePlan(int positionType){
        return (positionType&AdUnitConstants.POSITION_TYPE.TIEPLAN)>0;
    }

    private static boolean isTiePlan_Middle(int positionType){
        return (positionType&AdUnitConstants.POSITION_TYPE.TIEPLAN_MIDDLE)>0;
    }

    private static boolean isTiePlan_Pause(int positionType){
        return (positionType&AdUnitConstants.POSITION_TYPE.TIEPLAN_PAUSE)>0;
    }

    private static boolean isTiePlan_Post(int positionType){
        return (positionType&AdUnitConstants.POSITION_TYPE.TIEPLAN_POST)>0;
    }

    public static boolean isAdSlotTypeOK(int adSlotType,int positionType){
        switch (adSlotType){
            case AdUnitConstants.POSITION_TYPE.KAIPING:
                return isKaiPing(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPLAN:
                return isTiePlan(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPLAN_MIDDLE:
                return isTiePlan_Middle(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPLAN_PAUSE:
                return isTiePlan_Pause(positionType);
            case AdUnitConstants.POSITION_TYPE.TIEPLAN_POST:
                return isTiePlan_Post(positionType);
            default:
                return false;
        }
    }



}
