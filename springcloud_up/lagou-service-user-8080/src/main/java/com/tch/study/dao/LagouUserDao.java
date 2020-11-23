package com.tch.study.dao;

import com.tch.study.pojo.LagouUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LagouUserDao extends JpaRepository<LagouUser,Long> {


}
