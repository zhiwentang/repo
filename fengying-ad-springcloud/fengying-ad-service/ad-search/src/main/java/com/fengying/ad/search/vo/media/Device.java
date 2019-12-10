package com.fengying.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    //设备 id
    private String deviceCode;
    private String mac;
    private String ip;
    //机型编码
    private String model;
    //分辨率尺寸
    private String displaySize;
    //屏幕尺寸
    private String screenSize;
    //设备序列号
    private String serialName;


}
