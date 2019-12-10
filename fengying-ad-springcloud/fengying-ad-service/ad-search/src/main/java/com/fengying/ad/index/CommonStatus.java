package com.fengying.ad.index;

import lombok.Getter;

@Getter
public enum CommonStatus {

    VAILD(1,"有效状态"),
    INVAILD(2,"无效状态");

    private Integer status;
    private String desc;
    CommonStatus(Integer status,String desc){
        this.status=status;
        this.desc=desc;
    }
}
