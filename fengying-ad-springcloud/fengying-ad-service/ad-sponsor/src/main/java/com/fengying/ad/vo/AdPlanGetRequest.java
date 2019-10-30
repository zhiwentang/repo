package com.fengying.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanGetRequest {
    private Long userId;
    private List<Long> ids;

    public boolean validate() {
        return userId != null && CollectionUtils.isEmpty(ids);
    }

}
