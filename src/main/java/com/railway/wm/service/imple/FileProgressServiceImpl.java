package com.railway.wm.service.imple;

import com.mysql.jdbc.StringUtils;
import com.railway.wm.dao.AnalyseRepository;
import com.railway.wm.dao.TrainInfoRepository;
import com.railway.wm.domain.AnalyseResult;
import com.railway.wm.domain.FileAnalyseResult;
import com.railway.wm.domain.TrainInfoDao;
import com.railway.wm.service.FileProgressService;
import com.railway.wm.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

@Service("fileProgressService")
public class FileProgressServiceImpl implements FileProgressService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Value("${file.url}")
    private String fileUrl;

    @Autowired
    private AnalyseRepository analyseRepository;

    @Autowired
    private TrainInfoRepository trainInfoRepository;

    @Override
    public void scanFileDirectAndResultIntoDB() throws Exception {
        long start = System.currentTimeMillis();
        if (StringUtils.isNullOrEmpty(fileUrl)) {
            log.error("文件目录为空");
            return;
        } else {
            String filename = new StringBuffer().append(fileUrl).append(DateUtil.getCurrentDateString()).append(".txt").toString();
            File file = new File(filename);

            List<FileAnalyseResult> fileAnalyseResultList = new ArrayList<>();
            //1.检测结果格式化为dto
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
                String s;
                log.info("文件名:" + filename);
                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    if (!StringUtils.isNullOrEmpty(s)) {
                        String[] resultArray = s.split("\\|");
                        FileAnalyseResult analyseResult = new FileAnalyseResult();
                        analyseResult.setRailNo(resultArray[0]);
                        analyseResult.setPartNo(resultArray[1]);
                        analyseResult.setUrl(resultArray[2]);
                        analyseResult.setAnalyResult(Integer.parseInt(resultArray[3]));
                        analyseResult.setCheckDate(resultArray[4]);
                        analyseResult.setRailStation(resultArray[5]);
                        analyseResult.setErrorReason(resultArray[6]);
                        fileAnalyseResultList.add(analyseResult);
                    }
                }
                br.close();
            } catch (Exception e) {
                log.error("文件处理错误", e);
                return;
            }

            if (!CollectionUtils.isEmpty(fileAnalyseResultList)) {
                //机车概要信息处理
                List<TrainInfoDao> trainInfoDaoList = fileInfoChangeTrainInfo(fileAnalyseResultList);
                List<TrainInfoDao> trainInfoDaoListDb = trainInfoRepository.findTraininfodaoByCheckDateBetweenAndRailStation(DateUtil.getCurrentDateString(),
                        DateUtil.dateIncreaseByDay(DateUtil.getCurrentDateString(), 1),fileAnalyseResultList.get(0).getRailStation());
                if (CollectionUtils.isEmpty(trainInfoDaoListDb)) {
                    // 首次插入保存全部机车概要信息
                    trainInfoRepository.saveAll(trainInfoDaoList);
                    //保存所有的机车详情信息
                    for (FileAnalyseResult fileResult : fileAnalyseResultList) {
                        saveAnalyseResult(fileResult);
                    }
                } else {
                    Iterator<TrainInfoDao> iterables = trainInfoDaoList.iterator();
                    while (iterables.hasNext()) {
                        TrainInfoDao checkTrainInfo = iterables.next();
                        for (TrainInfoDao trainInfo : trainInfoDaoListDb
                                ) {
                            if (checkTrainInfo.equals(trainInfo)) {
                                iterables.remove();
                            }
                        }
                    }
                    if (CollectionUtils.isEmpty(trainInfoDaoList)) {
                        log.info("不存在新的机车信息");
                        return;
                    }

                    trainInfoRepository.saveAll(trainInfoDaoList);
                    //机车检测详情处理 --DB中
                    List<AnalyseResult> analyseResults = analyseRepository.findAnalyseResultsByCheckDateBetween(DateUtil.getCurrentDateString(),
                            DateUtil.dateIncreaseByDay(DateUtil.getCurrentDateString(), 1));

                    Iterator<FileAnalyseResult> fileAnalyseResultIterator = fileAnalyseResultList.iterator();
                    while (fileAnalyseResultIterator.hasNext()) {
                        boolean flag = false;
                        FileAnalyseResult fileAnalyseResult = fileAnalyseResultIterator.next();
                        AnalyseResult result = new AnalyseResult();
                        BeanUtils.copyProperties(fileAnalyseResult, result);
                        TrainInfoDao trainInfoDao = trainInfoRepository.findTrainInfoDaoByCheckDateAndRailNoAndRailStation(
                                fileAnalyseResult.getCheckDate(), fileAnalyseResult.getRailNo(), fileAnalyseResult.getRailStation());
                        result.setTrainInfoId(trainInfoDao.getId());
                        for (AnalyseResult analyseResult : analyseResults
                                ) {
                            if (result.equals(analyseResult)) {
                                fileAnalyseResultIterator.remove();
                                flag=true;
                            }
                        }
                        if(!flag){
                            analyseRepository.save(result);
                        }

                    }
                }
            } else {
                log.info("解析的文件为空。。");
            }

        }
    }

    private void saveAnalyseResult(FileAnalyseResult fileResult) {
        AnalyseResult result = new AnalyseResult();
        BeanUtils.copyProperties(fileResult, result);
        TrainInfoDao trainInfoDao = trainInfoRepository.findTrainInfoDaoByCheckDateAndRailNoAndRailStation(
                result.getCheckDate(), fileResult.getRailNo(), fileResult.getRailStation());
        result.setTrainInfoId(trainInfoDao.getId());
        analyseRepository.save(result);
    }


    public List<TrainInfoDao> fileInfoChangeTrainInfo(List<FileAnalyseResult> source) {
        Set<TrainInfoDao> trainInfoDaoSet = new HashSet<>();
        for (FileAnalyseResult fileAnalyse : source
                ) {
            TrainInfoDao dao = new TrainInfoDao();
            BeanUtils.copyProperties(fileAnalyse, dao);
            trainInfoDaoSet.add(dao);
        }
        return new ArrayList<>(trainInfoDaoSet);
    }
}
