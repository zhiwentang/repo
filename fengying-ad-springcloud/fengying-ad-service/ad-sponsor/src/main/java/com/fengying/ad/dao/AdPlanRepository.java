package com.fengying.ad.dao;

import com.fengying.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdPlanRepository extends JpaRepository<AdPlan,Long> {

    AdPlan findByIdAndUserId(Long id,Long userId);

    AdPlan findByPlanName(String planName);

    List<AdPlan> findAllByIdInAndUserId(List<Long> ids,Long userId);

    AdPlan findByUserIDAndPlanName(Long userId,String planName);

    List<AdPlan> findAllByPlanstatus(Integer status);
}
