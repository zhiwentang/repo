package com.fengying.ad.vo;

import com.fengying.ad.entity.unit_condition.AdUnitKeyword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitKeywordRequest {

    private List<UnitKeyword> unitKeywords;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitKeyword{
        private Long unitId;
        private String keyword;
    }

}
