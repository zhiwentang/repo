package com.fengying.ad.index.adunit;

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

}
