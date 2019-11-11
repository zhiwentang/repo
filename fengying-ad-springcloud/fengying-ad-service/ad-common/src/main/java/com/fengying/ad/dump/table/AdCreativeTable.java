package com.fengying.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCreativeTable {

    private Long adId;
    private String name;
    private Integer Type;
    private Integer materialTpye;
    private Integer height;
    private Integer width;
    private Integer auditStatus;
    private String adUrl;
}
