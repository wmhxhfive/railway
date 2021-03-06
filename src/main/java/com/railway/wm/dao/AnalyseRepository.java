package com.railway.wm.dao;


import com.railway.wm.domain.AnalyseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * date: 2017/3/15 16:28
 */
public interface AnalyseRepository extends JpaRepository<AnalyseResult, Long> {
    /**
     * 获取最近机车的检测结果
     * @return
     */
    @Query(value = " select id, rail_no,part_no,url,create_date,analy_result,check_date,rail_station ,analy_result " +
            " from  analyse_result  where check_date=(select check_date from  analyse_result order by check_date limit 1)",
            nativeQuery = true)
     List<AnalyseResult> findLatelyCheckResult();

    List<AnalyseResult> findAnalyseResultsByCheckDateBetween(String begin,String
                                                             end);

    /**
     * 按照机车摘要id以及检测时间获取机车详情信息
     * @param trainId
     * @param checkDate
     * @return
     */
    List<AnalyseResult> findAnalyseResultByTrainInfoIdAndCheckDate(Long trainId,String checkDate);

    List<AnalyseResult> findAnalyseResultByTrainInfoIdEquals(Long id);
}