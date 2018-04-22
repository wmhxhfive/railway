package com.railway.wm.dao;


import com.railway.wm.domain.AnalyseResult;
import com.railway.wm.domain.TrainInfoDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * Description:
 * date: 2017/3/15 16:28
 */
public interface TrainInfoRepository extends JpaRepository<TrainInfoDao, Long> ,JpaSpecificationExecutor<TrainInfoDao> {

     List<TrainInfoDao> findTraininfodaoByCheckDateBetween(String begin,String
            end);

     TrainInfoDao findTrainInfoDaoByCheckDateAndRailNoAndRailStation
             (String checkDate,String railno,String railStation);

     List<TrainInfoDao> findTop5ByRailStationOrderByCheckDateDesc(String railStation);
}