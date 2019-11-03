package com.fengying.ad.vo;

import com.fengying.ad.constant.CommonStatus;
import com.fengying.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeRequest {

    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Long size;
    private Integer duration;
    private Long usrId;
    private String url;

    public Creative convertToEntity(){
        Creative creative=new Creative();
        creative.setName(name);
        creative.setType(type);
        creative.setMaterialType(materialType);
        creative.setHeight(height);
        creative.setWidth(width);
        creative.setSize(size);
        creative.setDuration(duration);
        creative.setUserId(usrId);
        creative.setUrl(url);
        creative.setAuditStatus(CommonStatus.VAlID.getStatus());
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());

        return creative;
    }
}
