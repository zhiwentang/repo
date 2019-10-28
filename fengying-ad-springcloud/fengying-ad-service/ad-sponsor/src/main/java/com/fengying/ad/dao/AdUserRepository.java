package com.fengying.ad.dao;

import com.fengying.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdUserRepository extends JpaRepository<AdUser,Long> {

    AdUser findByUsername(String name);

}
