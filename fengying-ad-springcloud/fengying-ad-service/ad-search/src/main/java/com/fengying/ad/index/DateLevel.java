package com.fengying.ad.index;

import lombok.Getter;

@Getter
public enum DateLevel {

    LEVEL2("2","level 2"),
    LEVEL3("3","level 3"),
    LEVEL4("4","LEVEL4");

    private String level;
    private String desc;

    DateLevel(String level,String desc){
        this.level=level;
        this.desc=desc;
    }
}