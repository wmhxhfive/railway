package com.railway.wm.dao;


import com.railway.wm.domain.AnalyseResult;
import com.railway.wm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description:
 * date: 2017/3/15 16:28
 */
public interface AnalyseRepository extends JpaRepository<AnalyseResult, Long> {


}