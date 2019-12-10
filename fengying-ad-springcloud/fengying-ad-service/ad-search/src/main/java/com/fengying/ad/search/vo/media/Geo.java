package com.fengying.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geo {

    //经度
    private Float longitude;
    //纬度
    private Float latitude;

    //省份
    private String province;
    //市
    private String city;
}
