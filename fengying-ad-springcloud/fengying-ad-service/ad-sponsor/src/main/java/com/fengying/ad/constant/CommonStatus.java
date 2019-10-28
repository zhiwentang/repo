package com.fengying.ad.constant;

import lombok.Getter;

@Getter
public enum CommonStatus {
    VAlID(1,"有效状态"),
    INVALID(2,"无效状态");

    Integer status;
    String desc;

    CommonStatus(Integer status,String desc){
        this.status=status;
        this.desc=desc;
    }

}
