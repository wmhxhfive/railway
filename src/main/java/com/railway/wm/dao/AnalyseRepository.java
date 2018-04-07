package com.railway.wm.dao;


import com.railway.wm.domain.AnalyseResult;
import com.railway.wm.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Column;
import java.sql.Date;
import java.util.List;

/**
 * Description:
 * date: 2017/3/15 16:28
 */
public interface AnalyseRepository extends JpaRepository<AnalyseResult, Long> {
    @Query(value = "select id, rail_no,part_no,url,create_date,analy_result,check_date,rail_station from analyse_result ", nativeQuery = true)
     List<AnalyseResult> findLastResult();
}