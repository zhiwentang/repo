package com.fengying.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanRequest {
    private Long Id;
    private Long userId;
    private String planName;
    private String startDate;
    private String endDate;

    public boolean createValidate() {
        return userId != null &&
                !StringUtils.isEmpty(planName) &&
                !StringUtils.isEmpty(startDate) &&
                !StringUtils.isEmpty(endDate);
    }

    public boolean updateValidate() {
        return Id != null && userId != null;
    }

    public boolean deleteValidate() {
        return Id != null && userId != null;
    }
}
